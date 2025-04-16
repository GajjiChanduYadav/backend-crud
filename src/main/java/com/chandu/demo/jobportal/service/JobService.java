package com.chandu.demo.jobportal.service;


import com.chandu.demo.jobportal.customException.JobNotFoundException;
import com.chandu.demo.jobportal.model.Job;
import com.chandu.demo.jobportal.repository.JobRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {


   // private List<Job> jobs = new ArrayList<>();
  JobRepository jobRepository;


    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> findAll(){
        return jobRepository.findAll();
    }

    public void createJob(Job job){
        jobRepository.save(job);
    }


    public Job getById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));
    }


    public void deleteById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));

        jobRepository.delete(job);
    }


    public void updateJob(Long id, Job updatedJob) {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found with id: " + id));

        existingJob.setTitle(updatedJob.getTitle());
        existingJob.setDescription(updatedJob.getDescription());
        existingJob.setMinSalary(updatedJob.getMinSalary());
        existingJob.setMaxSalary(updatedJob.getMaxSalary());
        existingJob.setLocation(updatedJob.getLocation());

        jobRepository.save(existingJob); // Save the updated job to the DB
    }


}
