package co.edu.ff.orders.user.controllers;

import org.apache.http.client.methods.RequestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerSystemTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void createAndFindUser() throws Exception {
        String username = "Username-1234";
        String password = "Password-1234";
        String json = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);
        String createdJson = String.format("{\"value\":{\"username\":\"%s\",\"password\":\"%s\",\"id\":1}}", username, password);
        String findJson = String.format("{\"username\":\"%s\",\"password\":\"%s\",\"id\":1}", username, password);
        mockMvc.perform(
                post("/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(createdJson));
        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(findJson));
    }
}
