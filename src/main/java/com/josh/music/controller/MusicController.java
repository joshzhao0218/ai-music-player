package com.josh.music.controller;

import org.springframework.web.bind.annotation.*;
import com.josh.music.service.QwenService;
import com.josh.music.service.MusicService;
import com.josh.music.model.*;
import java.util.*;

/**
 * Music Controller - REST API Endpoints
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MusicController {
    
    private final QwenService qwenService;
    private final MusicService musicService;
    
    public MusicController(QwenService qwenService, MusicService musicService) {
        this.qwenService = qwenService;
        this.musicService = musicService;
    }
    
    // ============ Playback Control ============
    
    @PostMapping("/music/play")
    @ResponseBody
    public ApiResponse playMusic(@RequestBody(required = false) AiCommandRequest request) {
        if (request == null || request.getCommand() == null) {
            musicService.play("");
            return ApiResponse.ok("Playing music", musicService.getStatus());
        }
        
        QwenService.AiCommandResult result = qwenService.processCommand(request.getCommand());
        
        switch (result.action) {
            case "play":
                if (result.mood != null) {
                    List<MusicTrack> tracks = musicService.getTracksByMood(result.mood);
                    if (!tracks.isEmpty()) {
                        musicService.playTrack(tracks.get(0).getId());
                    }
                } else if (result.genre != null) {
                    List<MusicTrack> tracks = musicService.getTracksByGenre(result.genre);
                    if (!tracks.isEmpty()) {
                        musicService.playTrack(tracks.get(0).getId());
                    }
                } else {
                    musicService.play(request.getCommand());
                }
                break;
            case "resume":
                musicService.resume();
                break;
            default:
                musicService.play(request.getCommand());
        }
        
        return ApiResponse.ok(result.message, musicService.getStatus());
    }
    
    @PostMapping("/music/pause")
    @ResponseBody
    public ApiResponse pauseMusic() {
        musicService.pause();
        return ApiResponse.ok("Paused", musicService.getStatus());
    }
    
    @PostMapping("/music/resume")
    @ResponseBody
    public ApiResponse resumeMusic() {
        musicService.resume();
        return ApiResponse.ok("Resumed", musicService.getStatus());
    }
    
    @PostMapping("/music/stop")
    @ResponseBody
    public ApiResponse stopMusic() {
        musicService.stop();
        return ApiResponse.ok("Stopped", musicService.getStatus());
    }
    
    @PostMapping("/music/next")
    @ResponseBody
    public ApiResponse nextSong() {
        musicService.next();
        return ApiResponse.ok("Next song", musicService.getStatus());
    }
    
    @PostMapping("/music/previous")
    @ResponseBody
    public ApiResponse previousSong() {
        musicService.previous();
        return ApiResponse.ok("Previous song", musicService.getStatus());
    }
    
    @GetMapping("/music/status")
    @ResponseBody
    public ApiResponse getStatus() {
        return ApiResponse.ok(musicService.getStatus());
    }
    
    // ============ Volume Control ============
    
    @PostMapping("/music/volume")
    @ResponseBody
    public ApiResponse setVolume(@RequestBody Map<String, Integer> request) {
        Integer volume = request.get("volume");
        if (volume != null) {
            musicService.setVolume(volume);
        }
        return ApiResponse.ok("Volume set", Map.of("volume", musicService.getVolume()));
    }
    
    @GetMapping("/music/volume")
    @ResponseBody
    public ApiResponse getVolume() {
        return ApiResponse.ok(Map.of("volume", musicService.getVolume()));
    }
    
    // ============ Search ============
    
    @GetMapping("/music/search")
    @ResponseBody
    public ApiResponse search(@RequestParam String query) {
        List<MusicTrack> results = musicService.searchTrack(query);
        return ApiResponse.ok("Search results", Map.of("tracks", results, "count", results.size()));
    }
    
    // ============ Favorites ============
    
    @PostMapping("/music/favorite/{trackId}")
    @ResponseBody
    public ApiResponse addToFavorite(@PathVariable String trackId) {
        musicService.addToFavorites(trackId);
        return ApiResponse.ok("Added to favorites", null);
    }
    
    @DeleteMapping("/music/favorite/{trackId}")
    @ResponseBody
    public ApiResponse removeFromFavorite(@PathVariable String trackId) {
        musicService.removeFromFavorites(trackId);
        return ApiResponse.ok("Removed from favorites", null);
    }
    
    @GetMapping("/music/favorites")
    @ResponseBody
    public ApiResponse getFavorites() {
        Set<String> favorites = musicService.getFavorites();
        List<MusicTrack> favoriteTracks = new ArrayList<>();
        // In production, fetch full track details
        return ApiResponse.ok("Favorites", Map.of("ids", favorites, "count", favorites.size()));
    }
    
    // ============ History ============
    
    @GetMapping("/music/history")
    @ResponseBody
    public ApiResponse getHistory(@RequestParam(defaultValue = "10") int limit) {
        List<MusicTrack> history = musicService.getHistory();
        if (history.size() > limit) {
            history = history.subList(0, limit);
        }
        return ApiResponse.ok("Play history", Map.of("tracks", history, "count", history.size()));
    }
    
    // ============ Playlists ============
    
    @GetMapping("/music/playlists")
    @ResponseBody
    public ApiResponse getPlaylists() {
        Collection<Playlist> playlists = musicService.getAllPlaylists();
        return ApiResponse.ok("Your playlists", Map.of("playlists", playlists, "count", playlists.size()));
    }
    
    @GetMapping("/music/playlist/{id}")
    @ResponseBody
    public ApiResponse getPlaylist(@PathVariable String id) {
        Playlist playlist = musicService.getPlaylist(id);
        if (playlist != null) {
            return ApiResponse.ok(playlist.getName(), playlist);
        }
        return ApiResponse.error("Playlist not found");
    }
    
    @PostMapping("/music/playlist")
    @ResponseBody
    public ApiResponse createPlaylist(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String description = request.get("description");
        String mood = request.get("mood");
        
        if (name == null || name.isEmpty()) {
            return ApiResponse.error("Playlist name is required");
        }
        
        Playlist playlist = musicService.createPlaylist(name, description, mood);
        return ApiResponse.ok("Playlist created", playlist);
    }
    
    @PostMapping("/music/playlist/{id}/add")
    @ResponseBody
    public ApiResponse addToPlaylist(@PathVariable String id, @RequestBody Map<String, String> request) {
        String trackId = request.get("trackId");
        if (trackId != null) {
            musicService.addToPlaylist(id, trackId);
            return ApiResponse.ok("Track added", null);
        }
        return ApiResponse.error("Track ID required");
    }
    
    // ============ Recommendations ============
    
    @GetMapping("/music/recommendations")
    @ResponseBody
    public ApiResponse getRecommendations() {
        List<MusicTrack> recommendations = musicService.getRecommendations();
        return ApiResponse.ok("Recommended for you", Map.of("tracks", recommendations, "count", recommendations.size()));
    }
    
    // ============ Sleep Timer ============
    
    @PostMapping("/music/sleep-timer")
    @ResponseBody
    public ApiResponse setSleepTimer(@RequestBody Map<String, Integer> request) {
        Integer minutes = request.get("minutes");
        if (minutes != null && minutes > 0) {
            musicService.setSleepTimer(minutes);
            return ApiResponse.ok("Sleep timer set for " + minutes + " minutes", null);
        }
        return ApiResponse.error("Invalid minutes");
    }
    
    @DeleteMapping("/music/sleep-timer")
    @ResponseBody
    public ApiResponse cancelSleepTimer() {
        // Note: Need to add cancel method to MusicService
        return ApiResponse.ok("Sleep timer cancelled", null);
    }
    
    @GetMapping("/music/sleep-timer")
    @ResponseBody
    public ApiResponse getSleepTimerStatus() {
        boolean hasTimer = musicService.hasSleepTimer();
        return ApiResponse.ok(Map.of("active", hasTimer));
    }
    
    // ============ AI Commands ============
    
    @PostMapping("/ai/command")
    @ResponseBody
    public ApiResponse processAiCommand(@RequestBody AiCommandRequest request) {
        if (request == null || request.getCommand() == null) {
            return ApiResponse.error("Command is required");
        }
        
        QwenService.AiCommandResult result = qwenService.processCommand(request.getCommand());
        
        // Execute the action
        switch (result.action) {
            case "play":
                if (result.mood != null) {
                    List<MusicTrack> tracks = musicService.getTracksByMood(result.mood);
                    if (!tracks.isEmpty()) {
                        musicService.playTrack(tracks.get(0).getId());
                    }
                } else {
                    musicService.play(request.getCommand());
                }
                break;
            case "pause":
                musicService.pause();
                break;
            case "resume":
                musicService.resume();
                break;
            case "next":
                musicService.next();
                break;
            case "previous":
                musicService.previous();
                break;
            case "volume":
                if (result.volume != null) {
                    musicService.setVolume(result.volume);
                } else if (result.volumeChange != null) {
                    musicService.setVolume(musicService.getVolume() + result.volumeChange);
                }
                break;
            case "favorite":
                if (musicService.getCurrentTrack() != null) {
                    musicService.addToFavorites(musicService.getCurrentTrack().getId());
                }
                break;
            case "sleep_timer":
                if (result.sleepMinutes != null) {
                    musicService.setSleepTimer(result.sleepMinutes);
                }
                break;
        }
        
        return ApiResponse.ok(result.message, Map.of(
            "action", result.action,
            "mood", result.mood,
            "genre", result.genre,
            "status", musicService.getStatus()
        ));
    }
    
    @GetMapping("/ai/mood/{command}")
    @ResponseBody
    public ApiResponse getMoodFromCommand(@PathVariable String command) {
        String mood = qwenService.getMood(command);
        String description = qwenService.generatePlaylistDescription(mood);
        return ApiResponse.ok(Map.of(
            "mood", mood,
            "description", description,
            "tracks", musicService.getTracksByMood(mood)
        ));
    }
}
