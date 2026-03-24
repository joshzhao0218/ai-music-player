package com.josh.music.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AI Command Request Model
 */
@Data
@NoArgsConstructor
public class AiCommandRequest {
    private String command;
    private String mood;
    private String genre;
}
