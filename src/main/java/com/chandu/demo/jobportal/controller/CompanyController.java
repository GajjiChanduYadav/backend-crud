package com.chandu.demo.jobportal.controller;


import com.chandu.demo.jobportal.model.Company;
import com.chandu.demo.jobportal.model.Job;
import com.chandu.demo.jobportal.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company){
        companyService.createCompany(company);
        return new ResponseEntity<>("Company added successfully", HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity< List<Company>> getAllCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public String updateJob(@PathVariable Long id, @RequestBody Company updatedCompany) {
        companyService.updateCompany(id, updatedCompany);
        return "Job with ID " + id + " has been updated successfully.";
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompanyById(id);
        return ResponseEntity.ok("Company deleted successfully with id: " + id);
    }
    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable Long id){
        return companyService.getCompanyById(id);
    }
}
