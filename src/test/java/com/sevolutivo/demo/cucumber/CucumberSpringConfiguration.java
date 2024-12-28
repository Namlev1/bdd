package com.sevolutivo.demo.cucumber;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberSpringConfiguration {

    protected TestRestTemplate testRestTemplate;

    public CucumberSpringConfiguration(TestRestTemplate testRestTemplate) {
        testRestTemplate.getRestTemplate()
                .setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        this.testRestTemplate = testRestTemplate;
    }

}
