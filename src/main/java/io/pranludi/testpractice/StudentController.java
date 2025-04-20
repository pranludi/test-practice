package io.pranludi.testpractice;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

    final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(
        @RequestParam("name") String name,
        @RequestParam("amount") int amount
    ) {
        return new HelloResponseDto(name, amount);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(
        @RequestBody Student student
    ) {
        service.saveStudent(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents() {
        return ResponseEntity.ok(service.findAllStudents());
    }

}
