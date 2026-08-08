package com.example.jobms.job.impl;


import com.example.jobms.job.Job;
import com.example.jobms.job.JobRepository;
import com.example.jobms.job.JobService;
import com.example.jobms.job.clients.CompanyClient;
import com.example.jobms.job.clients.Reviewclient;
import com.example.jobms.job.dto.JobDTO;
import com.example.jobms.job.external.Company;
import com.example.jobms.job.external.Review;
import com.example.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
//    private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;
private CompanyClient companyClient;
    private Reviewclient reviewClient;

    int attempt=0;
    @Autowired
    RestTemplate restTemplate;
    public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,Reviewclient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient=companyClient;
        this.reviewClient=reviewClient;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {

        Job job= jobRepository.findById(id).orElse(null);
        return convertToDTO(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional=jobRepository.findById(id);
            if(jobOptional.isPresent()){
                Job job=jobOptional.get();
                job.setTitle(updatedJob.getTitle());
                job.setDescription(updatedJob.getDescription());
                job.setMinSalary(updatedJob.getMinSalary());
                job.setMaxSalary(updatedJob.getMaxSalary());
                job.setLocation(updatedJob.getLocation());
                jobRepository.save(job);
                return true;
        }
        return false;
    }

    @Override
//    @CircuitBreaker(name= "companyBreaker",fallbackMethod = "companyBreakerFallback")
//  @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")

    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt :"+ ++attempt);
List<Job> jobs=jobRepository.findAll();
List<JobDTO> jobDTOS =new ArrayList<>();
        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<String> companyBreakerFallback(Exception e){
        List<String> list=new ArrayList<>();
        list.add("Dummy");
        return list;
    }
private JobDTO convertToDTO(Job job){
//    RestTemplate  restTemplate=new RestTemplate();
//        JobWithCompanyDTO  jobWithCompanyDTO =new JobWithCompanyDTO();
//        jobWithCompanyDTO.setJob(job);
//    Company company=restTemplate.getForObject("http://COMPANYMS/companies/"+job.getCompanyId(), Company.class);
   Company company=companyClient.getCompany(job.getCompanyId());
//    ResponseEntity<List<Review>> reviewResponse= restTemplate.exchange("http://REVIEWMS/reviews?companyId="+job.getCompanyId(), HttpMethod.GET,null
//            ,new ParameterizedTypeReference<List<Review>>() {
//            });
    List<Review> reviews=reviewClient.getReviews(job.getCompanyId());
    JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job,company,reviews);
//        jobDTO.setCompany(company);
        return jobDTO;
}
}
