package io.pranludi.testpractice.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pranludi.testpractice.config.TestSecurityConfig;
import io.pranludi.testpractice.config.security.common.JwtUtil;
import io.pranludi.testpractice.config.security.filter.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(TestSecurityConfig.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;

    @MockitoBean
    MemberService memberService;
    @MockitoBean
    JwtUtil jwtUtil;
    @MockitoBean
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockitoBean
    JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Autowired
    ObjectMapper mapper;

    @Test
    void 사용자_등록() throws Exception {
        // given
        Member member = new Member("test01", "test01_name", "test01@email.com");
        String content = mapper.writeValueAsString(member);
        // when
        mvc.perform(
                post("/auth/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
            .andExpect(status().is2xxSuccessful()) // then
            .andDo(print());
    }

}
