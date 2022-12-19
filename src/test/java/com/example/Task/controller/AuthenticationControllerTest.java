package com.example.Task.controller;

import org.junit.jupiter.api.Test;

import static com.example.Task.controller.AuthenticationController.AUTH_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthenticationControllerTest extends AbstractWebTests {

    @Test
    void testAuthenticate() throws Exception {
        this.mockMvc.perform(post(AUTH_URL + "/authenticate")
                                 .param("username", "admin_root")
                                 .param("password", "ac8fd58as6dgf584"))
            .andDo(print())
            .andExpect(status().is3xxRedirection());
    }

    @Test
    void testAuthenticateWithNullUsername() throws Exception {
        this.mockMvc.perform(post(AUTH_URL + "/authenticate")
                                 .param("username", "")
                                 .param("password", "ac8fd58as6dgf584"))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void testAuthenticateWithNullPassword() throws Exception {
        this.mockMvc.perform(post(AUTH_URL + "/authenticate")
                                 .param("username", "admin_root")
                                 .param("password", ""))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}
