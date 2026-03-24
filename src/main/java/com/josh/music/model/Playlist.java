package com.josh.music.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Playlist Model
 */
@Data
@NoArgsConstructor
public class Playlist {
    private String id;
    private String name;
    private String description;
    private String mood;
    private java.util.List<MusicTrack> tracks;
    private java.time.LocalDateTime createdAt;
    
    public Playlist(String id, String name, String mood) {
        this.id = id;
        this.name = name;
        this.mood = mood;
        this.tracks = new java.util.ArrayList<>();
        this.createdAt = java.time.LocalDateTime.now();
    }
}
