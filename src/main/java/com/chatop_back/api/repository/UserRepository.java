package com.chatop_back.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chatop_back.api.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {}

