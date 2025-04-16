package com.chandu.demo.jobportal.repository;

import com.chandu.demo.jobportal.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
