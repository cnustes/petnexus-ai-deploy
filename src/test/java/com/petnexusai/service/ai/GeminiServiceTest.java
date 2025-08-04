package com.petnexusai.service.ai;

import com.petnexusai.dto.ai.gemini.GeminiContent;
import com.petnexusai.dto.ai.gemini.GeminiPart;
import com.petnexusai.dto.ai.gemini.GeminiRequest;
import com.petnexusai.dto.ai.gemini.GeminiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the GeminiService.
 */
@ExtendWith(MockitoExtension.class)
class GeminiServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GeminiService geminiService;

    /**
     * Set up the @Value fields before each test.
     */
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(geminiService, "geminiApiKey", "test-api-key");
        ReflectionTestUtils.setField(geminiService, "geminiApiUrl", "https://generativelanguage.googleapis.com/v1beta/models");
        ReflectionTestUtils.setField(geminiService, "geminiModel", "gemini-test");
        ReflectionTestUtils.setField(geminiService, "maxTokens", 150);
    }

    /**
     * Test case for a successful response from the generateResponse method.
     */
    @Test
    void generateResponse_Success() {
        // --- 1. Arrange (Given) ---
        String prompt = "This is a test prompt for Gemini.";
        String expectedReply = "This is a test reply from Gemini.";

        // Create a fake Gemini response object
        GeminiResponse mockApiResponse = new GeminiResponse();
        GeminiPart mockPart = new GeminiPart(expectedReply);
        GeminiContent mockContent = new GeminiContent(Collections.singletonList(mockPart));
        GeminiResponse.GeminiCandidate mockCandidate = new GeminiResponse.GeminiCandidate();
        mockCandidate.setContent(mockContent);
        mockApiResponse.setCandidates(Collections.singletonList(mockCandidate));

        // Configure the mock RestTemplate's behavior for Gemini's DTOs
        when(restTemplate.postForObject(anyString(), any(GeminiRequest.class), eq(GeminiResponse.class)))
                .thenReturn(mockApiResponse);

        // --- 2. Act (When) ---
        String actualReply = geminiService.generateResponse(prompt);

        // --- 3. Assert (Then) ---
        assertNotNull(actualReply, "The response should not be null.");
        assertEquals(expectedReply, actualReply, "The response should match the expected Gemini reply.");
    }
}