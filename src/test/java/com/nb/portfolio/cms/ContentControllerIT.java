package com.nb.portfolio.cms;

import com.nb.portfolio.cms.controllers.ContentController;
import com.nb.portfolio.cms.services.ContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ContentController.class)
@Import(ContentControllerIT.TestSecurityConfig.class)
class ContentControllerIT {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ContentService contentService;

    @Test
    void getContent_returns200() throws Exception {
        when(contentService.findAll()).thenReturn(List.of());
        mvc.perform(get("/api/content"))
                .andExpect(status().isOk());
    }

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        @Order(0)
        SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .securityMatcher(AnyRequestMatcher.INSTANCE)
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                    .build();
        }
    }
}