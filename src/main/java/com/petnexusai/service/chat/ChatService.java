package com.petnexusai.service.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    // Initialize the logger for this class
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    /**
     * Processes a chat message from an authenticated user.
     * <p>
     * For now, this implementation is a simple simulation. It logs the incoming
     * message and returns a predefined response incorporating the user's details.
     * This logic will be replaced in Part 5 with the RAG implementation.
     *
     * @param message The text message sent by the user.
     * @param userEmail The email of the authenticated user, extracted from the JWT.
     * @return A simple, simulated text reply.
     */
    public String getResponse(String message, String userEmail) {
        // Use the logger instead of System.out.println
        log.info("Processing message from user '{}': '{}'", userEmail, message);

        // Simple simulation logic for Part 4
        return "Hello " + userEmail + ", I received your message: '" + message + "'. I'll be able to help you better soon.";
    }
}