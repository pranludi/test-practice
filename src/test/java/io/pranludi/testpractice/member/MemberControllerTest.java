package io.pranludi.testpractice.member;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.pranludi.testpractice.config.security.common.JwtUtil;
import io.pranludi.testpractice.config.security.filter.JwtAuthenticationFilter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(MemberController.class)
@WebAppConfiguration
class MemberControllerTest {

    @Autowired
    private WebApplicationContext context;

    MockMvc mvc;

    @MockitoBean
    MemberService memberService;
    @MockitoBean
    MemberRepository memberRepository;
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
    void 사용자_조회() throws Exception {
        // given
        Member member = new Member("test01", "test01_name", "test01@email.com");
        given(memberService.find(any())).willReturn(member);
        // when
        mvc.perform(
                get("/member/test01")
                    .with(SecurityMockMvcRequestPostProcessors.user("test01").roles("USER"))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(member.getId()))
            .andDo(print());
    }

    @Test
    void 모든_사용자_조회() throws Exception {
        // given
        Member member = new Member("test01", "test01_name", "test01@email.com");
        var memberList = List.of(member);
        given(memberService.findAll()).willReturn(memberList);

        // when
        mvc.perform(
                get("/member/all")
                    .with(SecurityMockMvcRequestPostProcessors.user("test01").roles("USER"))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andDo(print());
    }
}
