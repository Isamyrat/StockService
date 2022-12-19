package com.example.Task.controller;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.example.Task.controller.StockController.STOCK_URL;
import static com.example.Task.controller.UserController.USER_URL;
import static com.example.Task.jwt.JwtUtils.BEARER_PREFIX;
import static com.example.Task.service.impl.StockServiceImpl.INVALID_DATE_FORMAT;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

public class StockControllerTest extends AbstractWebTests {
    private final String startDate = "2022.06.02T02:00:00";
    private final String endDate = "2022.06.02T02:00:00";

    @Test
    void testFilterFindAll() throws Exception {
        this.mockMvc.perform(get(STOCK_URL + "/findAll")
                                 .param("name", "STOCK_NAME")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("//*[@id='stock-list']/tr[@data-id='1']").exists())
            .andExpect(xpath("//*[@id='stock-list']/tr[@data-name='STOCK_NAME']").exists());

    }

    @Test
    void testAddStock() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/add")
                                 .param("name", "STOCK_NAME_3")
                                 .param("startDate", startDate)
                                 .param("endDate", endDate)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }

    @Test
    void testAddStockWithInvalidDate() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/add")
                                 .param("name", "STOCK_NAME_3")
                                 .param("startDate", "2022.06.02")
                                 .param("endDate", endDate)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().string(containsString(INVALID_DATE_FORMAT)));
    }
    @Test
    void testEnableStockById() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/enable/" + 2)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }
    @Test
    void testDisableStockById() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/disable/" + 1)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }
    @Test
    void testFindAll() throws Exception {
        this.mockMvc.perform(get(STOCK_URL + "/findAll")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("//*[@id='stock-list']/tr").nodeCount(2))
            .andExpect(xpath("//*[@id='stock-list']/tr[@data-id='1']").exists())
            .andExpect(xpath("//*[@id='stock-list']/tr[@data-id='2']").exists())
            .andExpect(xpath("//*[@id='stock-list']/tr[@data-name='STOCK_NAME']").exists())
            .andExpect(xpath("//*[@id='stock-list']/tr[@data-name='STOCK_NAME_2']").exists());
    }
}
