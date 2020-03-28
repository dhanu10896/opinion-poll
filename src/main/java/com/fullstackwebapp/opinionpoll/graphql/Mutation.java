package com.fullstackwebapp.opinionpoll.graphql;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.fullstackwebapp.opinionpoll.exception.GraphQlGenericException;
import com.fullstackwebapp.opinionpoll.model.Poll;
import com.fullstackwebapp.opinionpoll.repository.PollRepository;
import com.fullstackwebapp.opinionpoll.utils.ObjectUtils;
import graphql.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {

    @Autowired
    PollRepository pollRepository;

    public Poll updatePollName(Long id, String name) {
        Optional<Poll> poll = pollRepository.findById(id);
        if (!ObjectUtils.isUsable(name)){
            GraphQlGenericException graphQlGenericExecption = new GraphQlGenericException(ErrorType.ValidationError);
            graphQlGenericExecption.addInfo("Error", "name can not be blank.");
            throw graphQlGenericExecption;
        }
        if (!poll.isPresent()){
            GraphQlGenericException graphQlGenericExecption = new GraphQlGenericException(ErrorType.ValidationError);
            graphQlGenericExecption.addInfo("Error", "Invalid poll id provided.");
            throw graphQlGenericExecption;
        }
        poll.get().setName(name);
        pollRepository.save(poll.get());
        return poll.get();
    }

}
