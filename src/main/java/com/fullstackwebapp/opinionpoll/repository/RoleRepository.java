package com.fullstackwebapp.opinionpoll.repository;


import com.fullstackwebapp.opinionpoll.model.Role;
import com.fullstackwebapp.opinionpoll.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}