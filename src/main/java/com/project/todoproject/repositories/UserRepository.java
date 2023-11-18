package com.project.todoproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.todoproject.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
