package com.dat250.FeedApp;

import com.dat250.FeedApp.controller.PersonController;
import com.dat250.FeedApp.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Person person;

    @Test
    public void greetingShouldReturnMessageFromService() throws Exception {
        when(person.getName()).thenReturn("Gunnar");
        this.mockMvc.perform(get("/people")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Gunnar")));
    }
}
