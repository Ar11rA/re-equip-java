package self.study.example.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import self.study.example.entities.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {

}
