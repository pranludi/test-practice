package io.pranludi.testpractice.student;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import io.pranludi.testpractice.config.TestSecurityConfig;
import io.pranludi.testpractice.config.security.common.JwtUtil;
import io.pranludi.testpractice.config.security.filter.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Import(TestSecurityConfig.class)
@WebAppConfiguration
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private Gson gson;

    @Autowired
    private WebApplicationContext context;

    MockMvc mvc;

    @MockitoBean
    StudentService studentService;
    @MockitoBean
    JwtUtil jwtUtil;
    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockitoBean
    JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    void hello_단순_GET_Method_Controller_테스트() throws Exception {
        // given
        // when
        mvc.perform(
                get("/student/hello")
            )
            .andExpect(status().isOk()) // then
            .andDo(print())
            .andExpect(content().string("Hello World")); // then
    }

    @Test
    void helloDto_Param_Dto_응답_테스트() throws Exception {
        // given
        String name = "hello";
        int amount = 1000;

        // when
        mvc.perform(
                get("/student/hello/dto")
                    .param("name", name)
                    .param("amount", String.valueOf(amount))
            ).andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.name").value(name)) // then
            .andExpect(jsonPath("$.amount").value(amount)); // then
    }

    @Test
    void save_테스트_사용자_등록() throws Exception {
        // given
        Student student = new Student();
        student.setName("test");
        student.setEmail("test@email.com");
        String content = gson.toJson(student);

        // when
        mvc.perform(
                post("/student")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andExpect(status().is2xxSuccessful()) // then
            .andDo(print());
    }

}
