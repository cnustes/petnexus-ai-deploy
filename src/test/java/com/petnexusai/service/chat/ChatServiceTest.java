package com.petnexusai.service.chat;

import com.petnexusai.service.ai.GenerativeAiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    @Qualifier("openAiService")
    private GenerativeAiService openAiService;

    @Mock
    @Qualifier("geminiService")
    private GenerativeAiService geminiService;

    @Mock
    private RestTemplate restTemplate;

    // --- CHANGE 1: REMOVE @InjectMocks HERE ---
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        // --- CHANGE 2: MANUALLY INSTANTIATE THE SERVICE ---
        // This ensures the correct mocks are passed to the constructor.
        chatService = new ChatService(openAiService, geminiService, restTemplate);

        // Now, set the private fields on this new instance
        Map<String, String> fakeKnowledgeBase = new HashMap<>();
        fakeKnowledgeBase.put("senior dog diet", "Context about senior dog diet.");
        ReflectionTestUtils.setField(chatService, "knowledgeBase", fakeKnowledgeBase);
    }

    // The rest of the test methods remain exactly the same...

    @Test
    void getResponse_OpenAiSucceeds() {
        // Arrange
        String expectedResponse = "Response from OpenAI";
        when(openAiService.generateResponse(anyString())).thenReturn(expectedResponse);

        // Act
        String actualResponse = chatService.getResponse("some message", "user@test.com");

        // Assert
        assertEquals(expectedResponse, actualResponse, "Should return the response from OpenAI.");
        verify(geminiService, never()).generateResponse(anyString());
    }

    @Test
    void getResponse_OpenAiFails_GeminiSucceeds() {
        // Arrange
        String expectedResponse = "Response from Gemini";
        when(openAiService.generateResponse(anyString())).thenReturn(null);
        when(geminiService.generateResponse(anyString())).thenReturn(expectedResponse);

        // Act
        String actualResponse = chatService.getResponse("some message", "user@test.com");

        // Assert
        assertEquals(expectedResponse, actualResponse, "Should return the response from Gemini.");
        verify(openAiService, times(1)).generateResponse(anyString());
        verify(geminiService, times(1)).generateResponse(anyString());
    }

    @Test
    void getResponse_ContextIsAddedToPrompt() {
        // Arrange
        ArgumentCaptor<String> promptCaptor = ArgumentCaptor.forClass(String.class);
        when(openAiService.generateResponse(promptCaptor.capture())).thenReturn("some response");

        // Act
        chatService.getResponse("Tell me about senior dog diet", "user@test.com");

        // Assert
        String capturedPrompt = promptCaptor.getValue();
        assertTrue(capturedPrompt.contains("Context about senior dog diet."), "Prompt should contain the context.");
        assertTrue(capturedPrompt.contains("Tell me about senior dog diet"), "Prompt should contain the original question.");
    }
}