package com.zpmc.ztos.infra.business.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zpmc.ztos.infra.business.DummyApp;
import com.zpmc.ztos.infra.business.model.Request;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = DummyApp.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void testGeo() throws Exception {
        mockMvc.perform(get("/api/geo").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testNothing() throws Exception {
        String encoded = passwordEncoder.encode("Abcd1234");
        System.out.println(encoded);
        Request request = new Request();
        request.setUsername("admin");
        request.setPassword("Abcd1234");
        String content = new ObjectMapper().writeValueAsString(request);
        System.out.println(content);
        mockMvc.perform(
                post("/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }
}