package com.fullstackwebapp.opinionpoll;

import com.fullstackwebapp.opinionpoll.model.Poll;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoolingApplicationTests {

	@Autowired
	RestTemplate restTemplate;


	@Test
	public void contextLoads() {
		Iterable<Poll> polls = restTemplate.getForObject("http://127.0.0.1:5000/polls", Iterable.class);
		System.out.println(polls);
	}

}
