
package com.example.Task.controller;

import org.junit.jupiter.api.Test;

import static com.example.Task.controller.UserController.USER_URL;
import static com.example.Task.jwt.JwtUtils.BEARER_PREFIX;
import static com.example.Task.service.impl.UserServiceImpl.USERNAME_IS_OCCUPIED_PLEASE_CHANGE_IT;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


class UserControllerTest extends AbstractWebTests {

    @Test
    void testAddUser() throws Exception {

        this.mockMvc.perform(post(USER_URL + "/registration")
                                 .param("fullName", "Maria Suhareva")
                                 .param("username", "maria002")
                                 .param("password", "maria")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }

    @Test
    void testUpdateUser() throws Exception {
        this.mockMvc.perform(post(USER_URL + "/edit/" + 4)
                                 .param("fullName", "Maria Suhareva")
                                 .param("username", "maria0023")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }

    @Test
    void testFindAll() throws Exception {
        this.mockMvc.perform(get(USER_URL + "/findAll")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("//*[@id='user-list']/tr").nodeCount(5))
            .andExpect(xpath("//*[@id='user-list']/tr[@data-id='1']").exists())
            .andExpect(xpath("//*[@id='user-list']/tr[@data-id='2']").exists())
            .andExpect(xpath("//*[@id='user-list']/tr[@data-username='maria002']").exists());

    }

    @Test
    void testFilterFindAll() throws Exception {
        this.mockMvc.perform(get(USER_URL + "/findAll")
                                 .param("keyword", "maria0023")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("//*[@id='user-list']/tr[@data-id='4']").exists())
            .andExpect(xpath("//*[@id='user-list']/tr[@data-username='maria0023']").exists());
    }

    @Test
    void testFindAllWithInvalidRole() throws Exception {
        this.mockMvc.perform(get(USER_URL + "/findAll")
                                 .header(AUTHORIZATION, BEARER_PREFIX + USER_TOKEN))
            .andDo(print())
            .andExpect(status().isForbidden());
    }

    @Test
    void testForNonUNAUTHORIZED() throws Exception {
        this.mockMvc.perform(get(USER_URL + "/findAll"))
            .andDo(print())
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testAddUserWithNullFullName() throws Exception {

        this.mockMvc.perform(post(USER_URL + "/registration")
                                 .param("fullName", "")
                                 .param("username", "maria002")
                                 .param("password", "maria")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-name='fullNameError']").exists())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-message='Full name can not be empty.']").exists());
    }

    @Test
    void testAddUserWithNullUserName() throws Exception {

        this.mockMvc.perform(post(USER_URL + "/registration")
                                 .param("fullName", "Maria Suhareva")
                                 .param("username", "")
                                 .param("password", "maria")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-name='usernameError']").exists())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-message='Username can not be empty.']").exists());
    }

    @Test
    void testAddUserWithNullPassword() throws Exception {

        this.mockMvc.perform(post(USER_URL + "/registration")
                                 .param("fullName", "Maria Suhareva")
                                 .param("username", "maria002")
                                 .param("password", "")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-name='passwordError']").exists())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-message='Password can not be empty.']").exists());
    }

    @Test
    void testEditUserWithNullFullName() throws Exception {

        this.mockMvc.perform(post(USER_URL + "/edit/" + 4)
                                 .param("fullName", "")
                                 .param("username", "maria002")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-name='fullNameError']").exists())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-message='Full name can not be empty.']").exists());
    }

    @Test
    void testEditUserWithNullUserName() throws Exception {

        this.mockMvc.perform(post(USER_URL + "/edit/" + 4)
                                 .param("fullName", "Maria Suhareva")
                                 .param("username", "")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-name='usernameError']").exists())
            .andExpect(xpath("//*[@id='user-edit']/small[@error-message='Username can not be empty.']").exists());
    }

    @Test
    void testAddUserWithExistUsername() throws Exception {

        this.mockMvc.perform(post(USER_URL + "/registration")
                                 .param("fullName", "Maria Suhareva")
                                 .param("username", "user_second_root")
                                 .param("password", "maria")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString(USERNAME_IS_OCCUPIED_PLEASE_CHANGE_IT)));
    }

    @Test
    void testUpdateUserWithExistUsername() throws Exception {
        this.mockMvc.perform(post(USER_URL + "/edit/" + 1)
                                 .param("fullName", "Maria Suhareva")
                                 .param("username", "user_second_root")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString(USERNAME_IS_OCCUPIED_PLEASE_CHANGE_IT)));
    }
}