package com.fullstackwebapp.opinionpoll;

import com.fullstackwebapp.opinionpoll.model.Poll;
import com.fullstackwebapp.opinionpoll.repository.PollRepository;
import com.fullstackwebapp.opinionpoll.repository.QuestionRepository;
import com.fullstackwebapp.opinionpoll.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PollService  {
    @Autowired
    QuestionRepository questionRepository;


    @Autowired
    PollRepository pollRepository;



    public void savePoll(Poll poll) {
//        questionRepository.saveAll(poll.getQuestions());
        pollRepository.save(poll);
    }
}
