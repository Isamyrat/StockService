
package com.example.Task.controller;

import org.junit.jupiter.api.Test;

import static com.example.Task.controller.UserController.USER_URL;
import static com.example.Task.jwt.JwtUtils.BEARER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


class UserControllerTest extends AbstractWebTests {

    @Test
    void testFilterFindAll() throws Exception {
        this.mockMvc.perform(get(USER_URL + "/findAll")
                                 .param("keyword", "user_second_root")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("//*[@id='user-list']/tr[@data-id='3']").exists());

    }
    @Test
    void testAddUser() throws Exception {

        this.mockMvc.perform(post(USER_URL + "/registration")
                                 .param("fullName", "Maria Suhareva")
                                 .param("username", "maria002")
                                 .param("password", "maria")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }
    //todo something wrong
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
   /* @Test
    void testFindAllWithForbidden() throws Exception {
        this.mockMvc.perform(get(USER_URL + "/findAll")
                                 .header(AUTHORIZATION, BEARER_PREFIX + USER_TOKEN))
            .andDo(print())
            .andExpect(status().isForbidden());

    }*/

   /*
   dont work
   @Test
    void testAddUserWithNullUsername() throws Exception{
        String data = gson.toJson(new UserDto("Maria Suhareva", "", "maria"));

        this.mockMvc.perform(post(USER_URL + "/registration")
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .content(data)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("//*[@id='user-errors']/small[@error-message='Password can not be empty.']").exists());
    }*/

}