package com.chandu.demo.jobportal.repository;

import com.chandu.demo.jobportal.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Long> {
}
