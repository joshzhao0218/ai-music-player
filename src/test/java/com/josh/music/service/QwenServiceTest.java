package com.josh.music.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for QwenService
 */
@SpringBootTest
class QwenServiceTest {

    @Autowired
    private QwenService qwenService;

    @BeforeEach
    void setUp() {
        // Reset state before each test
    }

    @Test
    void testProcessPlayCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("play some relaxing music");
        
        assertEquals("play", result.action);
        assertEquals("relaxing", result.mood);
    }

    @Test
    void testProcessHappyCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("play happy upbeat music");
        
        assertEquals("play", result.action);
        assertEquals("happy", result.mood);
    }

    @Test
    void testProcessEnergeticCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("play energetic workout music");
        
        assertEquals("play", result.action);
        assertEquals("energetic", result.mood);
    }

    @Test
    void testProcessJazzGenreCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("play jazz music");
        
        assertEquals("play", result.action);
        assertEquals("Jazz", result.genre);
    }

    @Test
    void testProcessRockGenreCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("play rock songs");
        
        assertEquals("play", result.action);
        assertEquals("Rock", result.genre);
    }

    @Test
    void testProcessClassicalGenreCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("play classical music");
        
        assertEquals("play", result.action);
        assertEquals("Classical", result.genre);
    }

    @Test
    void testProcessPauseCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("pause the music");
        
        assertEquals("pause", result.action);
    }

    @Test
    void testProcessResumeCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("resume playback");
        
        assertEquals("resume", result.action);
    }

    @Test
    void testProcessNextCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("skip to next song");
        
        assertEquals("next", result.action);
    }

    @Test
    void testProcessPreviousCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("go back to previous song");
        
        assertEquals("previous", result.action);
    }

    @Test
    void testProcessVolumeUpCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("turn volume up");
        
        assertEquals("volume", result.action);
        assertEquals(10, result.volumeChange);
    }

    @Test
    void testProcessVolumeDownCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("decrease volume");
        
        assertEquals("volume", result.action);
        assertEquals(-10, result.volumeChange);
    }

    @Test
    void testProcessSleepTimerCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("set sleep timer for 30 minutes");
        
        assertEquals("sleep_timer", result.action);
        assertEquals(30, result.sleepMinutes);
    }

    @Test
    void testProcessFavoriteCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("add this to favorites");
        
        assertEquals("favorite", result.action);
    }

    @Test
    void testProcessHistoryCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("show play history");
        
        assertEquals("history", result.action);
    }

    @Test
    void testProcessRecommendCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("recommend something");
        
        assertEquals("recommend", result.action);
    }

    @Test
    void testProcessUnknownCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("play music");
        
        assertEquals("play", result.action);
        assertNotNull(result.message);
    }

    @Test
    void testGetMood() {
        String mood = qwenService.getMood("play relaxing calm music");
        assertEquals("relaxing", mood);
        
        mood = qwenService.getMood("play happy fun songs");
        assertEquals("happy", mood);
    }

    @Test
    void testGeneratePlaylistDescription() {
        String desc = qwenService.generatePlaylistDescription("relaxing");
        assertTrue(desc.toLowerCase().contains("calm") || desc.toLowerCase().contains("peaceful"));
        
        desc = qwenService.generatePlaylistDescription("happy");
        assertTrue(desc.toLowerCase().contains("upbeat") || desc.toLowerCase().contains("bright"));
        
        desc = qwenService.generatePlaylistDescription("unknown");
        assertNotNull(desc);
    }

    @Test
    void testEmptyCommand() {
        QwenService.AiCommandResult result = qwenService.processCommand("");
        
        assertNotNull(result);
        assertNotNull(result.action);
    }

    @Test
    void testNullCommand() {
        assertThrows(NullPointerException.class, () -> {
            qwenService.processCommand(null);
        });
    }
}
