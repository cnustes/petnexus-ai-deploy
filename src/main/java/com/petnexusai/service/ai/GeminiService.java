package com.petnexusai.service.ai;

import com.petnexusai.dto.ai.gemini.GeminiContent;
import com.petnexusai.dto.ai.gemini.GeminiPart;
import com.petnexusai.dto.ai.gemini.GeminiRequest;
import com.petnexusai.dto.ai.gemini.GeminiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service("geminiService")
public class GeminiService implements GenerativeAiService {

    private static final Logger log = LoggerFactory.getLogger(GeminiService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.model}")
    private String geminiModel;

    @Value("${ai.max.tokens}")
    private Integer maxTokens;

    @Override
    public String generateResponse(String prompt) {
        log.info("Sending prompt to Gemini API using model '{}'...", geminiModel);

        GeminiPart part = new GeminiPart(prompt);
        GeminiContent content = new GeminiContent(Collections.singletonList(part));
        GeminiRequest.GenerationConfig config = new GeminiRequest.GenerationConfig(maxTokens);
        GeminiRequest requestBody = new GeminiRequest(Collections.singletonList(content), config);

        // Build the final URL dynamically with the base URL, model, and API key
        String finalUrl = String.format("%s/%s:generateContent?key=%s", geminiApiUrl, geminiModel, geminiApiKey);

        try {
            GeminiResponse response = restTemplate.postForObject(finalUrl, requestBody, GeminiResponse.class);

            if (response != null && !response.getCandidates().isEmpty()) {
                String reply = response.getCandidates().get(0).getContent().getParts().get(0).getText();
                log.info("✅ Successfully received response from Gemini.");
                return reply;
            } else {
                log.warn("Received a null or empty response from Gemini.");
                return null;
            }
        } catch (Exception e) {
            log.error("❌ Error calling Gemini API: {}", e.getMessage());
            return null;
        }
    }
}