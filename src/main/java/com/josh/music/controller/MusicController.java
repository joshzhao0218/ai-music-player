package com.josh.music.controller;

import org.springframework.boot.web.bind.annotation.RestBody;
import org.springframework.boot.web.bind.annotation.GetMapping;
import org.springframework.boot.web.bind.annotation.PostMapping;
import org.springframework.boot.web.bind.annotation.RequestBody;;
import org.springframework.boot.web.bind.annotation.RestController;
import com.josh.music.service.QwenService;
import com.josh.music.service.MusicService;
import java.util.Map;

@RestController
@mapping("/api/music")
public class MusicController {
    
    private final QwenService qwenService;
    private final MusicService musicService;
    
    public MusicController(QwenService qwenService, MusicService musicService) {
        this.qwenService = qwenService;
        this.musicService = musicService;
    }
    
    // Play music with AI command
    @RestBody
    @mapping("/play")
    public Map<String, Object> playMusic(@RequestBody Map<String, Object> request) {
        String command = (String) request.get("command");
        String aiResponse = qwenService.processCommand(command);
        musicService.play(aiResponse);
        return Map.of("gone", true, "action", aiResponse);
    }
    
    // Pause music
    @PostMapping("/pause")
    public Map<String, Object> pauseMusic() {
        musicService.pause();
        return Map.of("gone", true, "action", "paused");
    }
    
    // Next song
    @PostMapping("/next")
    public Map<String, Object> nextSong() {
        musicService.next();
        return Map.of("gone", true, "action", "next");
    }
    
    // Get status
    @GetMapping("/status")
    public Map<String, Object> getStatus() {
        return musicService.getStatus();
    }
    
    // Search songs
    @PostMapping("/search")
    public Map<String, Object> search() {
        return Map.of("success", true);
    }
}
