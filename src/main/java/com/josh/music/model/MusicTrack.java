package com.josh.music.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Music Track Model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicTrack {
    private String id;
    private String title;
    private String artist;
    private String album;
    private String duration;
    private String mood;
    private String genre;
    private String coverUrl;
}
