package self.study.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import self.study.example.entities.Company;
import self.study.example.repositories.CompanyRepository;

import java.util.List;

@RestController()
@RequestMapping("company")
public class OverviewController {

    @Autowired
    private CompanyRepository _companyRepository;

    @GetMapping("/overview")
    public List<Company> GetOverview() {
        return _companyRepository.findAll();
    }

    @PostMapping("")
    public void GetOverview(@RequestBody() Company company) {
        _companyRepository.save(company);
    }
}
