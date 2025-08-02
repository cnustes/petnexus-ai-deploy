package com.petnexusai.service.ai;

import com.petnexusai.dto.ai.openai.OpenAiMessage;
import com.petnexusai.dto.ai.openai.OpenAiRequest;
import com.petnexusai.dto.ai.openai.OpenAiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service("openAiService")
public class OpenAiService implements GenerativeAiService {

    private static final Logger log = LoggerFactory.getLogger(OpenAiService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${openai.api.url}")
    private String openaiApiUrl;

    @Value("${openai.model}")
    private String openaiModel;

    @Value("${ai.max.tokens}")
    private Integer maxTokens;

    @Override
    public String generateResponse(String prompt) {
        log.info("Sending prompt to OpenAI API using model '{}'...", openaiModel);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        OpenAiMessage message = new OpenAiMessage("user", prompt);
        OpenAiRequest requestBody = new OpenAiRequest(openaiModel, Collections.singletonList(message), maxTokens);

        HttpEntity<OpenAiRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            OpenAiResponse response = restTemplate.postForObject(openaiApiUrl, requestEntity, OpenAiResponse.class);

            if (response != null && !response.getChoices().isEmpty()) {
                String reply = response.getChoices().get(0).getMessage().getContent();
                log.info("✅ Successfully received response from OpenAI.");
                return reply;
            } else {
                log.warn("Received a null or empty response from OpenAI.");
                return null;
            }
        } catch (Exception e) {
            log.error("❌ Error calling OpenAI API: {}", e.getMessage());
            return null;
        }
    }
}