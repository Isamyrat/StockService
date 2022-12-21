package com.example.Task.controller;

import com.example.Task.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.Task.util.ConstantsHolder.SECRET;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class AbstractWebTests {

    @Autowired
    protected MockMvc mockMvc;

    protected final String ADMIN_TOKEN = JwtUtils.generateToken("admin_root", SECRET, Integer.MAX_VALUE);
    protected final String USER_TOKEN = JwtUtils.generateToken("user_root", SECRET, Integer.MAX_VALUE);

    protected final String USER_PASSWORD = "ac8fd58as6dgf584";
}
