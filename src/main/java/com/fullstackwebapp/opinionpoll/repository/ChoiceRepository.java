package com.fullstackwebapp.opinionpoll.repository;

import com.fullstackwebapp.opinionpoll.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
