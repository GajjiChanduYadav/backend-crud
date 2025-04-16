package com.chandu.demo.jobportal.controller;

import com.chandu.demo.jobportal.model.Job;
import com.chandu.demo.jobportal.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

  private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/jobs")
    public List<Job> findAll(){
      return   jobService.findAll();
    }

    @PostMapping ("/job")
    public String createJob(@RequestBody Job job){
       jobService.createJob(job);
        return "Job has been created Successfully";
    }

    @GetMapping("/job/{id}")
    public Job getById(@PathVariable Long id){
        return jobService.getById(id);
    }

    @DeleteMapping("/job/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobService.deleteById(id);
        return "Job with ID " + id + " has been deleted successfully.";
    }

    @PutMapping("/job/{id}")
    public String updateJob(@PathVariable Long id, @RequestBody Job updatedJob) {
        jobService.updateJob(id, updatedJob);
        return "Job with ID " + id + " has been updated successfully.";
    }


}
