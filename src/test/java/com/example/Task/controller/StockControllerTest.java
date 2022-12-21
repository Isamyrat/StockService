package com.example.Task.controller;

import org.junit.jupiter.api.Test;

import static com.example.Task.controller.StockController.STOCK_URL;
import static com.example.Task.jwt.JwtUtils.BEARER_PREFIX;
import static com.example.Task.service.ProductServiceTest.FEED_LINK_OF_FIRST_STOCK;
import static com.example.Task.service.impl.StockServiceImpl.INVALID_DATE_FORMAT;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

public class StockControllerTest extends AbstractWebTests {
    private final String startDate = "2022-12-30T01:32";
    private final String endDate = "2022-12-30T01:32";

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
                                 .param("feedLink", FEED_LINK_OF_FIRST_STOCK)
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
    void testAddStockWithNullName() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/add")
                                 .param("name", "")
                                 .param("startDate", startDate)
                                 .param("endDate", endDate)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='stock-edit']/small[@error-name='nameError']").exists())
            .andExpect(xpath("//*[@id='stock-edit']/small[@error-message='Stock name can not be empty.']").exists());
    }

    @Test
    void testAddStockWithNullStartDate() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/add")
                                 .param("name", "STOCK_NAME_3")
                                 .param("startDate", "")
                                 .param("endDate", endDate)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='stock-edit']/small[@error-name='startDateError']").exists())
            .andExpect(
                xpath("//*[@id='stock-edit']/small[@error-message='Date parameters should be not null.']").exists());
    }

    @Test
    void testAddStockWithNullEndDate() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/add")
                                 .param("name", "STOCK_NAME_3")
                                 .param("startDate", startDate)
                                 .param("endDate", "")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='stock-edit']/small[@error-name='endDateError']").exists())
            .andExpect(
                xpath("//*[@id='stock-edit']/small[@error-message='Date parameters should be not null.']").exists());
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

    @Test
    void testEditStock() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/edit/" + 2)
                                 .param("name", "STOCK_NAME_Fourth")
                                 .param("startDate", startDate)
                                 .param("endDate", endDate)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }

    @Test
    void testEditStockWithNullName() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/edit/" + 2)
                                 .param("name", "")
                                 .param("startDate", startDate)
                                 .param("endDate", endDate)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='stock-edit']/small[@error-name='nameError']").exists())
            .andExpect(xpath("//*[@id='stock-edit']/small[@error-message='Stock name can not be empty.']").exists());

    }

    @Test
    void testEditStockWithNullStartDate() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/edit/" + 2)
                                 .param("name", "STOCK_NAME_Fourth")
                                 .param("startDate", "")
                                 .param("endDate", endDate)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='stock-edit']/small[@error-name='startDateError']").exists())
            .andExpect(
                xpath("//*[@id='stock-edit']/small[@error-message='Date parameters should be not null.']").exists());
    }

    @Test
    void testEditStockWithNullEndDate() throws Exception {

        this.mockMvc.perform(post(STOCK_URL + "/edit/" + 2)
                                 .param("name", "STOCK_NAME_Fourth")
                                 .param("startDate", startDate)
                                 .param("endDate", "")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='stock-edit']/small[@error-name='endDateError']").exists())
            .andExpect(
                xpath("//*[@id='stock-edit']/small[@error-message='Date parameters should be not null.']").exists());

    }
}
