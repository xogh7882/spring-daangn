package com.ssafy.Daangn;

import com.ssafy.Daangn.Controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = TestController.class)
public class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser // Security 우회하기
    void CICDTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test/CICD"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("CI/CD 배포 성공"));
    }
}
