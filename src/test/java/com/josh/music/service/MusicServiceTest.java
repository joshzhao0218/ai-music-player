package com.josh.music.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for MusicService
 */
@SpringBootTest
class MusicServiceTest {

    @Autowired
    private MusicService musicService;

    @BeforeEach
    void setUp() {
        // Reset state before each test
    }

    @Test
    void testPlayMusic() {
        musicService.play("test song");
        assertTrue(musicService.isPlaying());
        assertNotNull(musicService.getCurrentTrack());
    }

    @Test
    void testPauseMusic() {
        musicService.play("test song");
        assertTrue(musicService.isPlaying());
        
        musicService.pause();
        assertFalse(musicService.isPlaying());
    }

    @Test
    void testResumeMusic() {
        musicService.play("test song");
        musicService.pause();
        assertFalse(musicService.isPlaying());
        
        musicService.resume();
        assertTrue(musicService.isPlaying());
    }

    @Test
    void testNextTrack() {
        musicService.next();
        assertNotNull(musicService.getCurrentTrack());
    }

    @Test
    void testPreviousTrack() {
        musicService.previous();
        assertNotNull(musicService.getCurrentTrack());
    }

    @Test
    void testSetVolume() {
        musicService.setVolume(80);
        assertEquals(80, musicService.getVolume());
        
        musicService.setVolume(0);
        assertEquals(0, musicService.getVolume());
        
        musicService.setVolume(100);
        assertEquals(100, musicService.getVolume());
    }

    @Test
    void testVolumeBounds() {
        musicService.setVolume(150);  // Should cap at 100
        assertEquals(100, musicService.getVolume());
        
        musicService.setVolume(-10);  // Should floor at 0
        assertEquals(0, musicService.getVolume());
    }

    @Test
    void testSearchTrack() {
        List<com.josh.music.model.MusicTrack> results = musicService.searchTrack("jazz");
        assertFalse(results.isEmpty());
        
        results = musicService.searchTrack("nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    void testGetTracksByMood() {
        List<com.josh.music.model.MusicTrack> relaxing = musicService.getTracksByMood("relaxing");
        assertFalse(relaxing.isEmpty());
        
        relaxing.forEach(track -> {
            assertEquals("relaxing", track.getMood());
        });
    }

    @Test
    void testGetTracksByGenre() {
        List<com.josh.music.model.MusicTrack> jazz = musicService.getTracksByGenre("Jazz");
        assertFalse(jazz.isEmpty());
        
        jazz.forEach(track -> {
            assertEquals("Jazz", track.getGenre());
        });
    }

    @Test
    void testFavorites() {
        String trackId = "1";
        
        assertFalse(musicService.isFavorite(trackId));
        
        musicService.addToFavorites(trackId);
        assertTrue(musicService.isFavorite(trackId));
        
        musicService.removeFromFavorites(trackId);
        assertFalse(musicService.isFavorite(trackId));
    }

    @Test
    void testGetStatus() {
        var status = musicService.getStatus();
        
        assertTrue(status.containsKey("playing"));
        assertTrue(status.containsKey("volume"));
        assertTrue(status.containsKey("currentTrack"));
        assertTrue(status.containsKey("playlistSize"));
    }

    @Test
    void testGetRecommendations() {
        musicService.play("test");
        List<com.josh.music.model.MusicTrack> recommendations = musicService.getRecommendations();
        
        assertNotNull(recommendations);
        assertTrue(recommendations.size() <= 5);
    }

    @Test
    void testCreatePlaylist() {
        var playlist = musicService.createPlaylist("Test Playlist", "Test description", "happy");
        
        assertNotNull(playlist);
        assertNotNull(playlist.getId());
        assertEquals("Test Playlist", playlist.getName());
        assertEquals("happy", playlist.getMood());
    }

    @Test
    void testGetAllPlaylists() {
        var playlists = musicService.getAllPlaylists();
        
        assertNotNull(playlists);
        assertTrue(playlists.size() >= 2);  // Default playlists
    }
}
