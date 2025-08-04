package com.petnexusai.dto.ai.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class GeminiRequest {
    private List<GeminiContent> contents;

    private GenerationConfig generationConfig;

    @Data
    @AllArgsConstructor
    public static class GenerationConfig {
        private Integer maxOutputTokens;
    }
}