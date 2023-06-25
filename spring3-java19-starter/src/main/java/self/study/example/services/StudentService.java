package self.study.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import self.study.example.dto.CreateStudentDTO;
import self.study.example.entities.Student;
import self.study.example.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student createStudent(CreateStudentDTO createStudentDto){
        Student student = dtoToEntity(createStudentDto);
        Student savedStudent = studentRepository.save(student);
        return savedStudent;
    }

    public Student getStudentById(String id){
        Student student = studentRepository.findById(id).orElse(null);
        return student;
    }

    public List<Student> getAllStudents(){
        List<Student> allStudents = new ArrayList<>();
        studentRepository.findAll().forEach(student -> allStudents.add(student));
        return allStudents;
    }

    private Student dtoToEntity(CreateStudentDTO createStudentDto) {
        Student student = new Student();
        student.setName(createStudentDto.getName());
        student.setGrade(createStudentDto.getGrade());
        student.setContactNo(createStudentDto.getContactNo());
        return student;
    }
}
