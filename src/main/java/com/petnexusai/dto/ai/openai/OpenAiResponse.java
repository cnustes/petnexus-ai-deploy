package com.petnexusai.dto.ai.openai;

import lombok.Data;
import java.util.List;

@Data
public class OpenAiResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private OpenAiMessage message;
    }
}