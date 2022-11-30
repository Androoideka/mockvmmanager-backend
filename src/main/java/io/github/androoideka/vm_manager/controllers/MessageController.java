package io.github.androoideka.vm_manager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import io.github.androoideka.vm_manager.responses.StateChangeMessage;

@Component
public class MessageController {
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void sendNewState(StateChangeMessage message) {
        this.simpMessagingTemplate.convertAndSend("/topic/" + message.getUserId(), message);
    }
}
