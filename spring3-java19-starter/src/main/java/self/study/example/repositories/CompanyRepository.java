package self.study.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import self.study.example.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, String> {
}
