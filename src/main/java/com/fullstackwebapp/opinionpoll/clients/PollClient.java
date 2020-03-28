package com.fullstackwebapp.opinionpoll.clients;


import com.fullstackwebapp.opinionpoll.model.Poll;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("polling-service")
public interface PollClient {
    @GetMapping(value = "/polls")
    public ResponseEntity<Poll> getPolls();
}


