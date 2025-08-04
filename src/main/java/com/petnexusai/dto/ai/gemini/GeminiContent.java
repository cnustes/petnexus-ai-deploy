package com.petnexusai.dto.ai.gemini;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class GeminiContent {
    private List<GeminiPart> parts;
}