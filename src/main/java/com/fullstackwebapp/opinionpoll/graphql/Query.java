package com.fullstackwebapp.opinionpoll.graphql;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fullstackwebapp.opinionpoll.model.Poll;
import com.fullstackwebapp.opinionpoll.repository.PollRepository;
import graphql.schema.GraphQLFieldDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    @Autowired
    PollRepository pollRepository;

    public Page<Poll> findAllPolls(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit > 0 ? limit : 10);
        return pollRepository.findAll(pageable);
    }


}
