package com.petnexusai.service.ai;

import com.petnexusai.dto.ai.openai.OpenAiMessage;
import com.petnexusai.dto.ai.openai.OpenAiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the OpenAiService.
 */
@ExtendWith(MockitoExtension.class)
class OpenAiServiceTest {

    // Create a mock of RestTemplate. We will control its behavior.
    @Mock
    private RestTemplate restTemplate;

    // Create an instance of the service we want to test and inject the mocks into it.
    @InjectMocks
    private OpenAiService openAiService;

    /**
     * This method runs before each test. It's used to set up the environment.
     * We need to manually set the @Value fields because Mockito doesn't inject them.
     */
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(openAiService, "openaiApiKey", "test-api-key");
        ReflectionTestUtils.setField(openAiService, "openaiApiUrl", "https://api.openai.com/v1/chat/completions");
        ReflectionTestUtils.setField(openAiService, "openaiModel", "gpt-test");
        ReflectionTestUtils.setField(openAiService, "maxTokens", 150);
    }

    /**
     * Test case for a successful response from the generateResponse method.
     */
    @Test
    void generateResponse_Success() {
        // --- 1. Arrange (Given) ---
        // Define the input for our method
        String prompt = "This is a test prompt.";
        String expectedReply = "This is a test reply.";

        // Create a fake response object that we want our mock RestTemplate to return
        OpenAiResponse mockApiResponse = new OpenAiResponse();
        OpenAiResponse.Choice mockChoice = new OpenAiResponse.Choice();
        mockChoice.setMessage(new OpenAiMessage("assistant", expectedReply));
        mockApiResponse.setChoices(Collections.singletonList(mockChoice));

        // Configure the mock RestTemplate's behavior:
        // When postForObject is called with any URL, any request body, and for the OpenAiResponse class,
        // then return our fake response object.
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), eq(OpenAiResponse.class)))
                .thenReturn(mockApiResponse);

        // --- 2. Act (When) ---
        // Call the method we are testing
        String actualReply = openAiService.generateResponse(prompt);

        // --- 3. Assert (Then) ---
        // Verify that the result is what we expect
        assertNotNull(actualReply, "The response should not be null.");
        assertEquals(expectedReply, actualReply, "The response should match the expected reply.");
    }
}