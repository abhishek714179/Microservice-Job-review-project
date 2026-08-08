package com.example.jobms.job;

import com.example.jobms.job.dto.JobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/jobs")
public class JobController {
@Autowired
 JobService jobService;


    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll(){
        return ResponseEntity.ok(jobService.findAll());
    }


    @PostMapping
    public ResponseEntity<String> createJobs(@RequestBody Job job){
       jobService.createJob(job);
       return new ResponseEntity<>("Job created successfully",HttpStatus.OK);

    }

//    @GetMapping("/jobs/{id}")
//    public Job getJobById(@PathVariable  Long id){
//    Job job=jobService.getJobById(id);
//    if(job!=null)
//        return job;
//        return new Job(1L,"Test Job","Test Job","2000","4000","loc");
//    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> findJobById(@PathVariable Long id){
        JobDTO jobDTO =jobService.getJobById(id);
        if(jobDTO !=null)
            return new ResponseEntity<>(jobDTO, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable  Long id){
        boolean deleted=jobService.deleteJobById(id);
        if(deleted)
            return new ResponseEntity<>("Job deleted successfully",HttpStatus.OK);
        return new ResponseEntity<>("JOb not found",HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
//    @RequestMapping(value="/jobs/{id}" ,method=RequestMethod.PUT)
    public ResponseEntity<String> updateJob(@PathVariable Long id,@RequestBody Job updatedJob){
        boolean updated=jobService.updateJob(id,updatedJob);
        if(updated)
            return new ResponseEntity<>("Updated Job Successfully",HttpStatus.OK);
        return new ResponseEntity<>("Job not found",HttpStatus.NOT_FOUND);
    }
}
