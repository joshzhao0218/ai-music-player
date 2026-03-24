package com.josh.music.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.http.*;
import java.net.URI;
import java.time.Duration;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

/**
 * Qwen AI Service
 * Integrates with Alibaba Cloud Qwen API for natural language understanding
 */
@Service
public class QwenService {
    
    private static final Logger logger = LoggerFactory.getLogger(QwenService.class);
    
    @Value("${qwen.api-key:demo-key}")
    private String apiKey;
    
    @Value("${qwen.model:qwen-plus}")
    private String model;
    
    @Value("${qwen.base-url:https://dashscope.aliyuncs.com/api/v1}")
    private String baseUrl;
    
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    
    public QwenService() {
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * Process user command using Qwen AI
     * Returns interpreted action and parameters
     */
    public AiCommandResult processCommand(String command) {
        try {
            // For demo purposes, use local parsing
            // In production, this would call the actual Qwen API
            if ("demo-key".equals(apiKey) || apiKey == null || apiKey.isEmpty()) {
                return parseCommandLocally(command);
            }
            
            // Call Qwen API
            return callQwenApi(command);
        } catch (Exception e) {
            logger.error("Error processing command: {}", e.getMessage());
            return parseCommandLocally(command);
        }
    }
    
    /**
     * Call actual Qwen API
     */
    private AiCommandResult callQwenApi(String command) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(ObjectMapperMapper.createMessage(model, command));
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(baseUrl + "/services/aigc/text-generation/generation"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
            .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() == 200) {
            JsonNode root = objectMapper.readTree(response.body());
            String content = root.path("output").path("text").asText();
            return parseAiResponse(content);
        }
        
        return parseCommandLocally(command);
    }
    
    /**
     * Local command parsing (fallback when API not available)
     */
    public AiCommandResult parseCommandLocally(String command) {
        String lowerCommand = command.toLowerCase();
        AiCommandResult result = new AiCommandResult();
        result.originalCommand = command;
        
        // Detect action type
        if (lowerCommand.contains("play")) {
            result.action = "play";
            
            // Extract mood
            if (lowerCommand.contains("relax") || lowerCommand.contains("calm") || 
                lowerCommand.contains("peace") || lowerCommand.contains("sleep")) {
                result.mood = "relaxing";
            } else if (lowerCommand.contains("happy") || lowerCommand.contains("cheer") || 
                       lowerCommand.contains("upbeat") || lowerCommand.contains("fun")) {
                result.mood = "happy";
            } else if (lowerCommand.contains("energy") || lowerCommand.contains("workout") || 
                       lowerCommand.contains("gym") || lowerCommand.contains("run")) {
                result.mood = "energetic";
            } else if (lowerCommand.contains("sad") || lowerCommand.contains("melancholy")) {
                result.mood = "sad";
            }
            
            // Extract genre
            if (lowerCommand.contains("jazz")) {
                result.genre = "Jazz";
            } else if (lowerCommand.contains("rock")) {
                result.genre = "Rock";
            } else if (lowerCommand.contains("pop")) {
                result.genre = "Pop";
            } else if (lowerCommand.contains("classical")) {
                result.genre = "Classical";
            } else if (lowerCommand.contains("electronic") || lowerCommand.contains("edm")) {
                result.genre = "Electronic";
            } else if (lowerCommand.contains("lofi") || lowerCommand.contains("lo-fi")) {
                result.genre = "Lofi";
            }
            
            // Generate response message
            if (result.mood != null) {
                result.message = "Playing " + result.mood + " music for you";
            } else if (result.genre != null) {
                result.message = "Playing " + result.genre + " songs";
            } else {
                result.message = "Starting playback";
            }
            
        } else if (lowerCommand.contains("pause") || lowerCommand.contains("stop")) {
            result.action = "pause";
            result.message = "Music paused";
            
        } else if (lowerCommand.contains("resume") || lowerCommand.contains("continue")) {
            result.action = "resume";
            result.message = "Resuming playback";
            
        } else if (lowerCommand.contains("next") || lowerCommand.contains("skip")) {
            result.action = "next";
            result.message = "Playing next song";
            
        } else if (lowerCommand.contains("previous") || lowerCommand.contains("back")) {
            result.action = "previous";
            result.message = "Playing previous song";
            
        } else if (lowerCommand.contains("volume")) {
            result.action = "volume";
            if (lowerCommand.contains("up") || lowerCommand.contains("increase")) {
                result.volumeChange = 10;
                result.message = "Volume increased";
            } else if (lowerCommand.contains("down") || lowerCommand.contains("decrease")) {
                result.volumeChange = -10;
                result.message = "Volume decreased";
            } else if (lowerCommand.contains("max") || lowerCommand.contains("100")) {
                result.volume = 100;
                result.message = "Volume set to maximum";
            } else if (lowerCommand.contains("mute") || lowerCommand.contains("0")) {
                result.volume = 0;
                result.message = "Muted";
            }
            
        } else if (lowerCommand.contains("favorite") || lowerCommand.contains("like")) {
            result.action = "favorite";
            result.message = "Added to favorites";
            
        } else if (lowerCommand.contains("history") || lowerCommand.contains("played")) {
            result.action = "history";
            result.message = "Showing play history";
            
        } else if (lowerCommand.contains("playlist")) {
            result.action = "playlist";
            if (lowerCommand.contains("create")) {
                result.message = "Creating new playlist";
            } else if (lowerCommand.contains("show") || lowerCommand.contains("list")) {
                result.message = "Showing your playlists";
            }
            
        } else if (lowerCommand.contains("recommend") || lowerCommand.contains("suggest")) {
            result.action = "recommend";
            result.message = "Here are some recommendations for you";
            
        } else if (lowerCommand.contains("sleep") || lowerCommand.contains("timer")) {
            result.action = "sleep_timer";
            // Try to extract minutes
            for (int i = 10; i <= 120; i += 10) {
                if (lowerCommand.contains(String.valueOf(i))) {
                    result.sleepMinutes = i;
                    break;
                }
            }
            if (result.sleepMinutes == 0) {
                result.sleepMinutes = 30; // Default
            }
            result.message = "Sleep timer set for " + result.sleepMinutes + " minutes";
            
        } else if (lowerCommand.contains("status") || lowerCommand.contains("playing") || 
                   lowerCommand.contains("what")) {
            result.action = "status";
            result.message = "Getting current status";
        }
        
        if (result.action == null) {
            result.action = "play"; // Default action
            result.message = "Playing music based on your request";
        }
        
        return result;
    }
    
    /**
     * Parse AI response text into structured command
     */
    private AiCommandResult parseAiResponse(String response) {
        AiCommandResult result = new AiCommandResult();
        result.originalCommand = response;
        
        // Simple parsing - in production, use structured output from AI
        if (response.toLowerCase().contains("play")) {
            result.action = "play";
        } else if (response.toLowerCase().contains("pause")) {
            result.action = "pause";
        } else {
            result.action = "play";
        }
        
        result.message = response;
        return result;
    }
    
    /**
     * Get mood from command using AI
     */
    public String getMood(String command) {
        AiCommandResult result = processCommand(command);
        return result.mood != null ? result.mood : "general";
    }
    
    /**
     * Generate playlist description based on mood
     */
    public String generatePlaylistDescription(String mood) {
        return switch (mood.toLowerCase()) {
            case "relaxing" -> "Calm and peaceful tracks to help you unwind";
            case "happy" -> "Upbeat songs to brighten your day";
            case "energetic" -> "High-energy tracks for workouts and activities";
            case "sad" -> "Melancholic melodies for reflective moments";
            case "focus" -> "Instrumental tracks to enhance concentration";
            default -> "A curated mix of great songs";
        };
    }
    
    // Inner class for command result
    public static class AiCommandResult {
        public String originalCommand;
        public String action;
        public String mood;
        public String genre;
        public String message;
        public Integer volume;
        public Integer volumeChange;
        public Integer sleepMinutes;
        
        public AiCommandResult() {}
    }
    
    // Helper class for API request
    private static class ObjectMapperMapper {
        static ObjectNode createMessage(String model, String command) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode root = mapper.createObjectNode();
            root.put("model", model);
            
            ObjectNode input = mapper.createObjectNode();
            ArrayNode messages = mapper.createArrayNode();
            
            ObjectNode userMsg = mapper.createObjectNode();
            userMsg.put("role", "user");
            userMsg.put("content", "Interpret this music command and respond with just the action: " + command);
            messages.add(userMsg);
            
            input.set("messages", messages);
            root.set("input", input);
            
            ObjectNode params = mapper.createObjectNode();
            params.put("result_format", "text");
            root.set("parameters", params);
            
            return root;
        }
    }
}
