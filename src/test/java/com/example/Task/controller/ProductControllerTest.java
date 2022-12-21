package com.example.Task.controller;

import org.junit.jupiter.api.Test;

import static com.example.Task.controller.ProductController.PRODUCT_URL;
import static com.example.Task.jwt.JwtUtils.BEARER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

public class ProductControllerTest extends AbstractWebTests {

    @Test
    void testFilterFindAll() throws Exception {
        this.mockMvc.perform(get(PRODUCT_URL + "/findAll/" + 1)
                                 .param("productData", "PRODUCT_NAME")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("//*[@id='product-list']/tr[@data-name='PRODUCT_NAME']").exists())
            .andExpect(xpath("//*[@id='product-list']/tr[@data-price='15.2']").exists());

    }

    @Test
    void testAddProduct() throws Exception {

        this.mockMvc.perform(post(PRODUCT_URL + "/add/" + 1)
                                 .param("name", "Phone")
                                 .param("price", "15.6")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }

    @Test
    void testAddProductWithNullName() throws Exception {

        this.mockMvc.perform(post(PRODUCT_URL + "/add/" + 1)
                                 .param("name", "")
                                 .param("price", "15.6")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='product-edit']/small[@error-name='nameError']").exists())
            .andExpect(
                xpath("//*[@id='product-edit']/small[@error-message='Product name can not be empty.']").exists());

    }

    @Test
    void testAddProductWithNullPrice() throws Exception {

        this.mockMvc.perform(post(PRODUCT_URL + "/add/" + 1)
                                 .param("name", "Phone")
                                 .param("price", "")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='product-edit']/small[@error-name='priceError']").exists())
            .andExpect(
                xpath("//*[@id='product-edit']/small[@error-message='Product price can not be empty.']").exists());

    }

    @Test
    void testDeleteProductById() throws Exception {

        this.mockMvc.perform(post(PRODUCT_URL + "/delete/0a8dc8ec-4507-4a35-abfc-cc5a8063914f" + "/" + 1)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }

    @Test
    void testAddPictureToProduct() throws Exception {

        String pictureLink =
            "https://img.gkbcdn.com/p/2021-03-18/Fence-Gate-Steel-100x101-cm-Green-456546-1._w500_.jpg";
        this.mockMvc.perform(post(PRODUCT_URL + "/addPicture/0a8dc8ec-4507-4a35-abfc-cc5a8063914f")
                                 .param("pictureLink", pictureLink)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }

    @Test
    void testGetProductByInvalidId() throws Exception {

        this.mockMvc.perform(get(PRODUCT_URL + "/getById/0a8")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isNotFound());
    }

    @Test
    void testGetProductById() throws Exception {

        this.mockMvc.perform(get(PRODUCT_URL + "/getById/172fb0cd-aa4f-4a05-8e07-b9f7e58fe52c")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        this.mockMvc.perform(get(PRODUCT_URL + "/findAll/" + 2)
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(xpath("//*[@id='product-list']/tr").nodeCount(2))
            .andExpect(xpath("//*[@id='product-list']/tr[@data-name='PRODUCT_NAME_SECOND']").exists())
            .andExpect(xpath("//*[@id='product-list']/tr[@data-price='22.2']").exists());
    }

    @Test
    void testEditProduct() throws Exception {

        this.mockMvc.perform(post(PRODUCT_URL + "/edit/172fb0cd-aa4f-4a05-8e07-b9f7e58fe52c")
                                 .param("name", "Product_name")
                                 .param("price", "16.5")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print());
    }

    @Test
    void testEditProductWithNullName() throws Exception {

        this.mockMvc.perform(post(PRODUCT_URL + "/edit/172fb0cd-aa4f-4a05-8e07-b9f7e58fe52c")
                                 .param("name", "")
                                 .param("price", "16.5")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='product-edit']/small[@error-name='nameError']").exists())
            .andExpect(
                xpath("//*[@id='product-edit']/small[@error-message='Product name can not be empty.']").exists());
    }

    @Test
    void testEditProductWithNullPrice() throws Exception {

        this.mockMvc.perform(post(PRODUCT_URL + "/edit/172fb0cd-aa4f-4a05-8e07-b9f7e58fe52c")
                                 .param("name", "Product_name")
                                 .param("price", "")
                                 .header(AUTHORIZATION, BEARER_PREFIX + ADMIN_TOKEN))
            .andDo(print())
            .andExpect(xpath("//*[@id='product-edit']/small[@error-name='priceError']").exists())
            .andExpect(
                xpath("//*[@id='product-edit']/small[@error-message='Product price can not be empty.']").exists());
    }
}
