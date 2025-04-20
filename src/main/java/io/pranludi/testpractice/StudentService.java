package io.pranludi.testpractice;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public Student saveStudent(Student student) {
        return repository.save(student);
    }

    public List<Student> findAllStudents() {
        return repository.findAll();
    }

}
