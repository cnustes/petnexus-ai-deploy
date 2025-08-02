package com.petnexusai.dto.ai.gemini;

import lombok.Data;
import java.util.List;

@Data
public class GeminiResponse {
    private List<GeminiCandidate> candidates;

    @Data
    public static class GeminiCandidate {
        private GeminiContent content;
    }
}