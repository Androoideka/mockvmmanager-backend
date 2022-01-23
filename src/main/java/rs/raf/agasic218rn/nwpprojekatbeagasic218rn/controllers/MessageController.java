package rs.raf.agasic218rn.nwpprojekatbeagasic218rn.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import rs.raf.agasic218rn.nwpprojekatbeagasic218rn.responses.StateChangeMessage;

@Component
public class MessageController {
    private SimpMessagingTemplate simpMessagingTemplate;
    private SimpUserRegistry simpUserRegistry;

    @Autowired
    public MessageController(SimpMessagingTemplate simpMessagingTemplate, SimpUserRegistry simpUserRegistry) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.simpUserRegistry = simpUserRegistry;
    }

    public void sendNewState(StateChangeMessage message) {
        this.simpMessagingTemplate.convertAndSend("/topic/" + message.getUserId(), message);
    }
}
