package co.edu.ff.orders.user.controllers;

import co.edu.ff.orders.user.domain.*;
import co.edu.ff.orders.user.services.UserServices;
import com.google.gson.Gson;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;


    @MockBean
    private UserServices services;

    @Test
    void createUser() throws Exception {
        UserCreated user = UserCreated.of(
                Username.of("Username1234"),
                Password.of("Password1234"),
                1L
        );
        CreateUserRequest userRequest = CreateUserRequest.of(
                Username.of("Username1234"),
                Password.of("Password1234")
        );
        UserOperation userOperation = UserOperationSuccess.of(user);
        String userExpected = this.gson.toJson(userOperation);
        String content = this.gson.toJson(userRequest);
        when(services.createUser(any(Username.class), any(Password.class)))
                .thenReturn(userOperation);
        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders
                .post("/api/v1/user").contentType("application/json").content(content);
        this.mockMvc.perform(servletRequestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(userExpected));

    }

    @Test
    void getUserByIdEmpty() throws Exception {
        //Organizar
        when(services.findById(anyLong()))
                .thenReturn(Optional.empty());

        //act n assert
        MockHttpServletRequestBuilder servletRequestBuilder = MockMvcRequestBuilders
                .get("/api/v1/user/1");
        this.mockMvc.perform(servletRequestBuilder)
                .andDo(print())
                .andExpect(status().is4xxClientError());

    }
    
    @Test
    void getUserById() throws Exception {
        UserCreated user = UserCreated.of(
                Username.of("Username1234"),
                Password.of("Password1234"),
                1L);
        String userExpected = this.gson.toJson(user);
        when(services.findById(anyLong()))
                .thenReturn(Optional.of(user));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/user/1");
        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(userExpected));
    }
}