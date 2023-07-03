package com.davinci.moneytransferservice;

import com.davinci.moneytransferservice.model.Amount;
import com.davinci.moneytransferservice.model.Confirmation;
import com.davinci.moneytransferservice.model.Transfer;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.testcontainers.containers.GenericContainer;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestContainers {

    @Autowired
    private TestRestTemplate testRestTemplate;
    private GenericContainer<?> moneyCont = new GenericContainer<>("money:latest").withExposedPorts(5500);

    @BeforeEach
    void setUp() {
        moneyCont.start();
    }

    @Test
    void simpleTransfer() {
        int port = moneyCont.getMappedPort(5500);

        String json = testRestTemplate.exchange(
                new RequestEntity<>(
                        new Transfer("1111111111111111",
                                "1223",
                                "123",
                                "2222222222222222",
                                new Amount(1234, "rubbles"),
                                "0"),
                        HttpMethod.POST,
                        URI.create("http://localhost:" + port + "/transfer")),
                String.class).getBody();
        String operationId = new JsonParser().parse(json).getAsJsonObject().get("operationId").getAsString();
        Assertions.assertEquals("0", operationId);
    }

    @Test
    void transferAndConfirm(){
        int port = moneyCont.getMappedPort(5500);

        String json = testRestTemplate.exchange(
                new RequestEntity<>(
                        new Transfer("1111111111111111",
                                "1223",
                                "123",
                                "2222222222222222",
                                new Amount(1234, "rubbles"),
                                "0"),
                        HttpMethod.POST,
                        URI.create("http://localhost:" + port + "/transfer")),
                String.class).getBody();
        String operationId = new JsonParser().parse(json).getAsJsonObject().get("operationId").getAsString();

        json = testRestTemplate.exchange(
                new RequestEntity<>(
                        new Confirmation(operationId, "0000"),
                        HttpMethod.POST,
                        URI.create("http://localhost:" + port + "/transfer")),
                String.class).getBody();
        String newOperationId = new JsonParser().parse(json).getAsJsonObject().get("operationId").getAsString();

        Assertions.assertEquals(operationId, newOperationId);
    }
}
