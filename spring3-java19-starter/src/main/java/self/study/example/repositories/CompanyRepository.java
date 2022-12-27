package self.study.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import self.study.example.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
