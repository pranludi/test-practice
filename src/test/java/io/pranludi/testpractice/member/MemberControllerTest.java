package io.pranludi.testpractice.member;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.pranludi.testpractice.config.TestSecurityConfig;
import io.pranludi.testpractice.config.security.common.JwtUtil;
import io.pranludi.testpractice.config.security.filter.JwtAuthenticationFilter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberController.class)
@Import(TestSecurityConfig.class)
class MemberControllerTest {

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
    void 사용자_조회() throws Exception {
        // given
        Member member = new Member("test01", "test01_name", "test01@email.com");
        given(memberService.find(anyString())).willReturn(member);
        // when
        mvc.perform(
                get("/member/test01")
//                     .with(SecurityMockMvcRequestPostProcessors.user("test01"))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
        // @WithMockUser(username = "test01")
    void 모든_사용자_조회() throws Exception {
        // given
        Member member = new Member("test01", "test01_name", "test01@email.com");
        var memberList = List.of(member);
        given(memberService.findAll()).willReturn(memberList);

        // when
        mvc.perform(
                get("/member/all")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }
}