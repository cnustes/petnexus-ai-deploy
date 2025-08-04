package com.petnexusai.service.chat;

import com.petnexusai.service.ai.GenerativeAiService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    // --- Dependencies ---
    private final Map<String, String> knowledgeBase = new HashMap<>();
    private final GenerativeAiService openAiService;
    private final GenerativeAiService geminiService; // <-- NEW DEPENDENCY
    private final RestTemplate restTemplate;

    @Value("${knowledge.base.url}")
    private String knowledgeBaseUrl;

    // --- Constructor Injection for all services ---
    @Autowired
    public ChatService(
            @Qualifier("openAiService") GenerativeAiService openAiService,
            @Qualifier("geminiService") GenerativeAiService geminiService, // <-- NEW DEPENDENCY
            RestTemplate restTemplate) {
        this.openAiService = openAiService;
        this.geminiService = geminiService; // <-- NEW DEPENDENCY
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    public void loadKnowledgeBase() {
        log.info("Attempting to load knowledge base from URL: {}", knowledgeBaseUrl);
        try {
            String content = restTemplate.getForObject(knowledgeBaseUrl, String.class);
            if (content != null) {
                for (String line : content.split("\\r?\\n")) {
                    String[] parts = line.split("::", 2);
                    if (parts.length == 2) {
                        knowledgeBase.put(parts[0].toLowerCase().trim(), parts[1].trim());
                    }
                }
            }
            log.info("✅ Knowledge base loaded successfully with {} entries.", knowledgeBase.size());
        } catch (Exception e) {
            log.error("❌ Failed to load knowledge base from URL: {}", knowledgeBaseUrl, e);
        }
    }

    public String getResponse(String message, String userEmail) {
        log.info("Orchestrating response for user '{}'", userEmail);

        String context = findContextInKnowledgeBase(message);
        String finalPrompt = buildPromptWithContext(context, message);

        // --- UPDATED GENERATION LOGIC WITH FALLBACK ---
        // 1. Try primary service (OpenAI)
        String response = openAiService.generateResponse(finalPrompt);

        // 2. If primary fails, try fallback service (Gemini)
        if (response == null) {
            log.warn("OpenAI service failed. Attempting fallback to Gemini service...");
            response = geminiService.generateResponse(finalPrompt);
        }

        // 3. If both services fail, return an error message
        if (response == null) {
            log.error("All AI services failed. Unable to generate a response.");
            return "I am currently unable to process your request. Please try again later.";
        }

        return response;
    }

    private String findContextInKnowledgeBase(String userMessage) {
        String userMessageLower = userMessage.toLowerCase();
        for (Map.Entry<String, String> entry : knowledgeBase.entrySet()) {
            if (userMessageLower.contains(entry.getKey())) {
                log.info("Found relevant context for key: '{}'", entry.getKey());
                return entry.getValue();
            }
        }
        log.info("No relevant context found in the knowledge base.");
        return null;
    }

    /**
     * Builds the final prompt to be sent to the AI, combining a system message,
     * context, and the user's question.
     * @param context The context found in the knowledge base (can be null).
     * @param question The original question from the user.
     * @return A formatted prompt string.
     */
    private String buildPromptWithContext(String context, String question) {
        // This is our new, more powerful system prompt.
        String systemPrompt = "You are PetNexus AI, a friendly and helpful AI assistant specializing in pet care. " +
                "Your primary goal is to answer user questions about the health, nutrition, and general well-being of pets. " +
                "If a user asks a question that is not related to pets or animal care, you must politely decline to answer " +
                "and state that your purpose is to help with pet-related topics. Do not answer off-topic questions. " +
                "Use Markdown for formatting, for example **bold** for important terms.";

        if (context == null) {
            log.warn("No context found. Sending original question to AI with system prompt.");
            // If there's no context from our knowledge base, we still send the system prompt.
            return String.format("%s\n\nQuestion:\n\"%s\"", systemPrompt, question);
        }

        // When we have context, we combine all three parts.
        return String.format(
                "%s\n\nUse the following context to answer the question.\n\nContext:\n\"%s\"\n\nQuestion:\n\"%s\"",
                systemPrompt,
                context,
                question
        );
    }
}