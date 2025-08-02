package com.petnexusai.controller.chat;

import com.petnexusai.dto.chat.ChatRequest;
import com.petnexusai.dto.chat.ChatResponse;
import com.petnexusai.service.chat.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@SecurityRequirement(name = "bearerAuth")
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Handles incoming chat messages from authenticated users.
     * <p>
     * This endpoint is secured and requires a valid JWT. It extracts the user's
     * identity from the security context, validates the request, and passes
     * the message to the ChatService for processing.
     *
     * @param chatRequest The request body containing the user's message.
     * @param authentication The authentication object, automatically populated by Spring Security.
     * @return A ResponseEntity containing the chat reply.
     */
    @Operation(summary = "Submit a chat message", description = "Sends a message to the chat service and receives a reply. Requires authentication.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully received and processed the message",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChatResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body, e.g., empty message",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, invalid or missing JWT token",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<ChatResponse> handleChatMessage(
            @Valid @RequestBody ChatRequest chatRequest,
            Authentication authentication) {

        String userEmail = authentication.getName();
        log.info("Received chat request from authenticated user: {}", userEmail);

        String reply = chatService.getResponse(chatRequest.getMessage(), userEmail);

        return ResponseEntity.ok(new ChatResponse(reply));
    }
}