package com.petnexusai.controller.chat;

import com.petnexusai.service.chat.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @Test
    @WithMockUser(username = "testuser@example.com")
    void handleChatMessage_Success() throws Exception {
        // --- Arrange ---
        String userMessage = "{\"message\":\"This is a test\"}";
        String expectedReply = "This is a mock reply from the service.";

        when(chatService.getResponse(anyString(), anyString())).thenReturn(expectedReply);

        // --- Act & Assert ---
        mockMvc.perform(post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userMessage))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reply").value(expectedReply));
    }
}