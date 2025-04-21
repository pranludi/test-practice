package io.pranludi.testpractice.student;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    @Transactional
    void save() {
        // given
        Student student = new Student(null, "test name", "test email");

        // when
        Student saved = studentRepository.save(student);
        var findSavedId = studentRepository.findById(saved.getId()).get().getId();

        // then
        assertEquals(1, findSavedId);
    }
}