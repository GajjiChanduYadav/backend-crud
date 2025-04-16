package com.chandu.demo.jobportal.service;

import com.chandu.demo.jobportal.customException.CompanyNotFoundException;
import com.chandu.demo.jobportal.customException.JobNotFoundException;
import com.chandu.demo.jobportal.model.Company;
import com.chandu.demo.jobportal.model.Job;
import com.chandu.demo.jobportal.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void createCompany(Company company){
        companyRepository.save(company);
    }


    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }

    public void updateCompany(Long id, Company updatedCompany) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("company not found with id: " + id));

        existingCompany.setName(updatedCompany.getName());
        existingCompany.setDescription(updatedCompany.getDescription());
        existingCompany.setJobs(updatedCompany.getJobs());


        companyRepository.save(existingCompany); // Save the updated job to the DB
    }

    public void deleteCompanyById(Long id){
          Company company= companyRepository.findById(id)
                  .orElseThrow(() ->  new CompanyNotFoundException("company not found with id: " + id));
          companyRepository.delete(company);
    }
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() ->  new CompanyNotFoundException("company not found with id: " + id));
    }


}
