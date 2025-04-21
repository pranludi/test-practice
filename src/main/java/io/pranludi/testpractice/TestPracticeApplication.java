package io.pranludi.testpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TestPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestPracticeApplication.class, args);
    }

}
