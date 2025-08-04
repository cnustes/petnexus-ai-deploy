package com.petnexusai.controller.chat;

import com.petnexusai.security.JwtAuthFilter;
import com.petnexusai.security.JwtService;
import com.petnexusai.service.chat.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService chatService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser
    void shouldReturnChatResponseSuccessfully() throws Exception {
        when(chatService.getResponse(anyString(), anyString())).thenReturn("Hello, how can I help you?");

        mockMvc.perform(post("/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"Hi\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Hello, how can I help you?"));
    }

    @Test
    @WithMockUser
    void shouldReturnBadRequestWhenMessageIsEmpty() throws Exception {
        mockMvc.perform(post("/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUnauthorizedWhenNoAuthProvided() throws Exception {
        mockMvc.perform(post("/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"Hi\"}"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void shouldReturnServerErrorWhenServiceFails() throws Exception {
        when(chatService.getResponse(anyString(), anyString())).thenThrow(new RuntimeException("Simulated failure"));

        mockMvc.perform(post("/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\": \"Hi\"}"))
                .andExpect(status().isInternalServerError());
    }
}
