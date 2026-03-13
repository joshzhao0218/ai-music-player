package com.josh.music.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.josh.music.service.QwenService;
import com.josh.music.service.MusicService;
import java.util.Map;

@RestController
@RequestMapping("/api/music")
public class MusicController {
    
    private final QwenService qwenService;
    private final MusicService musicService;
    
    public MusicController(QwenService qwenService, MusicService musicService) {
        this.qwenService = qwenService;
        this.musicService = musicService;
    }
    
    // Play music with AI command
    @PostMapping("/play")
    @ResponseBody
    public Map<String, Object> playMusic(@RequestBody Map<String, Object> request) {
        String command = (String) request.get("command");
        String aiResponse = qwenService.processCommand(command);
        musicService.play(aiResponse);
        return Map.of("success", true, "action", aiResponse);
    }
    
    // Pause music
    @PostMapping("/pause")
    @ResponseBody
    public Map<String, Object> pauseMusic() {
        musicService.pause();
        return Map.of("success", true, "action", "paused");
    }
    
    // Next song
    @PostMapping("/next")
    @ResponseBody
    public Map<String, Object> nextSong() {
        musicService.next();
        return Map.of("success", true, "action", "next");
    }
    
    // Get status
    @GetMapping("/status")
    @ResponseBody
    public Map<String, Object> getStatus() {
        return musicService.getStatus();
    }
    
    // Search songs
    @PostMapping("/search")
    @ResponseBody
    public Map<String, Object> search() {
        return Map.of("success", true);
    }
}
