package io.pranludi.testpractice.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentService studentService;

    @Test
    void saveStudent() {
        // given
        Student student = new Student(null, "test name", "test email");
        given(studentRepository.save(student)).willAnswer(invocation -> {
            Student arg = invocation.getArgument(0, Student.class);
            arg.setId(1);
            return arg;
        });

        // when
        Student savedStudent = studentService.saveStudent(student);

        // then
        assertEquals(1, savedStudent.getId());
    }
}
