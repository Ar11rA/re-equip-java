package self.study.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import self.study.example.entities.Company;
import self.study.example.services.CompanyService;

import java.util.List;

@RestController()
@RequestMapping("company")
public class CompanyController {

    @Autowired
    private CompanyService _companyService;

    @GetMapping("/overview")
    public List<Company> GetOverview() {
        return _companyService.getOverview();
    }

    @PostMapping("")
    public Company SaveCompany(@RequestBody() Company company) {
        return _companyService.saveCompany(company);
    }
}
