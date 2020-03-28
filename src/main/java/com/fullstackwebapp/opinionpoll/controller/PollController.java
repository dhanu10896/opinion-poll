package com.fullstackwebapp.opinionpoll.controller;

import com.fullstackwebapp.opinionpoll.PollService;
import com.fullstackwebapp.opinionpoll.model.Choice;
import com.fullstackwebapp.opinionpoll.model.Poll;
import com.fullstackwebapp.opinionpoll.model.Question;
import com.fullstackwebapp.opinionpoll.model.Vote;
import com.fullstackwebapp.opinionpoll.payloads.VoteRequest;
import com.fullstackwebapp.opinionpoll.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RestController
public class PollController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PollRepository pollRepository;

    @Autowired
    PollService pollService;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    ChoiceRepository choiceRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @GetMapping("/polls")
    public ResponseEntity<Poll> getPolls() {
        return new ResponseEntity(pollRepository.findAll(), HttpStatus.OK);
    }
    @GetMapping("/polls/{pollId}")
    public ResponseEntity<Poll> getPoll(@PathVariable("pollId") Long pollId) {
        return new ResponseEntity(pollRepository.getOne(pollId), HttpStatus.OK);
    }

    @PostMapping("/polls")
    public ResponseEntity<Poll> createPolls(@Valid @RequestBody Poll poll) {
        pollService.savePoll(poll);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity deletePoll(@PathVariable("pollId") Long pollId) {
        pollRepository.deleteById(pollId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/polls/{pollId}")
    public ResponseEntity<Poll> createPolls(@PathVariable("pollId") Long pollId, @Valid @RequestBody Poll newPool) {

        Poll poll = pollRepository.getOne(pollId);
        poll.setName(newPool.getName());
        poll.setExpirationDateTime(newPool.getExpirationDateTime());
        poll.setPublic(newPool.isPublic());
        questionRepository.deleteAll(poll.getQuestions());
        poll.getQuestions().clear();
        for (Question question : newPool.getQuestions()) {
            if (question.getId()!=null) {
                Question  question1 = questionRepository.getOne(question.getId());
                question1.setValue(question.getValue());

                for (Choice choice : question.getChoices()) {
                    if (choice.getId()!=null) {
                        Choice existing = choiceRepository.getOne(choice.getId());
                        existing.setValue(choice.getValue());
                        question1.getChoices().add(existing);
                    } else {
                        question1.getChoices().add(choice);
                    }
                }
                poll.getQuestions().add(question1);
            } else {
              poll.getQuestions().add(question);
            }
        }

        pollRepository.save(poll);
       return new ResponseEntity(HttpStatus.OK);
    }





    @PostMapping("/polls/{pollId}/questions/{questionId}/votes")
    public ResponseEntity vote(@Valid @RequestBody VoteRequest voteRequest ,
                                     @PathVariable("pollId") Long pollId,
                                     @PathVariable("questionId") Long questionId) {

        Vote vote = new Vote();
        vote.setChoice(choiceRepository.getOne(voteRequest.getChoiceId()));
        vote.setQuestion(questionRepository.getOne(questionId));
        vote.setUser(userRepository.getOne(3L));
        voteRepository.save(vote);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
