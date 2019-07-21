package com.nexters.wiw.api.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TestController.class)
@RunWith(SpringRunner.class)
public class TestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void apitest() throws Exception {
        this.mvc.perform(get("/api/v1/test"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("content").exists());
    }
}