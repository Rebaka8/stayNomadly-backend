package com.staynomadly.api.controller;

import com.staynomadly.api.entity.Message;
import com.staynomadly.api.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class MessageController {

    private final MessageRepository messageRepository;

    @GetMapping("/thread")
    public ResponseEntity<List<Message>> getMessageThread(@RequestParam Long user1, @RequestParam Long user2) {
        return ResponseEntity.ok(messageRepository.findBySenderIdAndReceiverIdOrSenderIdAndReceiverIdOrderByTimestampAsc(user1, user2, user2, user1));
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        return ResponseEntity.ok(messageRepository.save(message));
    }
}
