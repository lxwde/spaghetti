package com.mycompany.myapp.cucumber;

import com.mycompany.myapp.DummyredisApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = DummyredisApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
