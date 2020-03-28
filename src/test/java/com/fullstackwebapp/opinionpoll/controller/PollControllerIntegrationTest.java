package com.fullstackwebapp.opinionpoll.controller;

import com.fullstackwebapp.opinionpoll.model.Poll;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PollControllerIntegrationTest {

    @LocalServerPort
    private int PORT;

    private  String HTTP = "http://";
    private  String HOST = "localhost:";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void test_getAllPolls() {
        ResponseEntity<List> polls = testRestTemplate.getForEntity(HTTP+HOST+PORT+"/polls", List.class);
        assertThat(polls.getStatusCode(),equalTo(HttpStatus.OK));
    }

    @Test
    public void test_getOnePoll() {
        ResponseEntity<Poll> polls = testRestTemplate.getForEntity(HTTP+HOST+PORT+"/polls/9", Poll.class);
        assertThat(polls.getStatusCode(),equalTo(HttpStatus.OK));
    }
}
