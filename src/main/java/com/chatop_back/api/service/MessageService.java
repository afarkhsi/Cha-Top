package com.chatop_back.api.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.chatop_back.api.model.Message;
import com.chatop_back.api.model.Rental;
import com.chatop_back.api.model.Users;
import com.chatop_back.api.payload.request.MessageRequest;
import com.chatop_back.api.repository.MessageRepository;
import com.chatop_back.api.repository.RentalRepository;
import com.chatop_back.api.repository.UserRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, RentalRepository rentalRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public void saveMessage(MessageRequest messageRequest) {
        Users user = userRepository.findById(messageRequest.getUser_id())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Rental rental = rentalRepository.findById(messageRequest.getRental_id())
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Message message = new Message();
        message.setMessage(messageRequest.getMessage());
        message.setUser(user);
        message.setRental(rental);
        message.setCreated_at(LocalDateTime.now());

        messageRepository.save(message);
    }
}
