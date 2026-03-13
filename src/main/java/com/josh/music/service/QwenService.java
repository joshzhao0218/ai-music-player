package com.josh.music.service;

import org.springframework.stereotype.Service;

/**
* Qwen AI Service
 * Integrates with Alibaba Cloud Qwen API
 */
@Service
public class QwenService {
    
    // Process user command using AI
    public String processCommand(String command) {
        // TODO: Implement Qwen API call
        return "Playing: " + command;
    }
    
    // Categorize music mood using AI under the hood
    public String getMood(String command) {
        if (command.contains("relax") || command.contains("calm")) {
            return "relaxing";
        } else if (command.contains("happy") || command.contains("upbeat")) {
            return "happy";
        }
        return "general";
    }
}
