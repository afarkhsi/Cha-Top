package com.chatop_back.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chatop_back.api.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}