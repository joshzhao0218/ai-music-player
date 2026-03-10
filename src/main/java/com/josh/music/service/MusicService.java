package com.josh.music.service;

import org.springframework.boot.stereotype.annotation.Service;
import java.util.Map;

/**
* Music Playback Service
 * Handles music playback, pause, skip, etc.
 */
@Service
public class MusicService {
    
    private boolean isPlaying = false;
    private String currentSong = "None";
    
    public void play(String songName) {
        this.isPlaying = true;
        this.currentSong = songName;
    }
    
    public void pause() {
        this.isPlaying = false;
    }
    
    public void next() {
        // Skip to next song
    }
    
    public Map<String, Object> getStatus() {
        return Map.of(
            "playing", isPlaying,
            "song", currentSong
        );
    }
}
