package com.josh.music.service;

import org.springframework.stereotype.Service;
import com.josh.music.model.MusicTrack;
import com.josh.music.model.Playlist;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Music Playback Service
 * Handles music playback, playlists, favorites, and history
 */
@Service
public class MusicService {
    
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private MusicTrack currentTrack;
    private int volume = 70;
    private int currentTrackIndex = 0;
    private List<MusicTrack> playlist = new ArrayList<>();
    private List<MusicTrack> history = new ArrayList<>();
    private Set<String> favorites = ConcurrentHashMap.newKeySet();
    private Map<String, Playlist> userPlaylists = new ConcurrentHashMap<>();
    private Timer sleepTimer = null;
    
    // Sample music database
    private final List<MusicTrack> musicDatabase = Arrays.asList(
        new MusicTrack("1", "Summer Breeze", "The Waves", "Ocean Sounds", "3:45", "relaxing", "Pop", ""),
        new MusicTrack("2", "Midnight Jazz", "Jazz Quartet", "Night Sessions", "4:20", "relaxing", "Jazz", ""),
        new MusicTrack("3", "Happy Days", "Sunshine Band", "Good Vibes", "3:30", "happy", "Pop", ""),
        new MusicTrack("4", "Workout Energy", "Fit Beats", "Gym Mix", "4:00", "energetic", "Electronic", ""),
        new MusicTrack("5", "Classical Peace", "Symphony Orchestra", "Calm Collection", "5:15", "relaxing", "Classical", ""),
        new MusicTrack("6", "Rock Anthem", "The Rockers", " Loud & Proud", "4:45", "energetic", "Rock", ""),
        new MusicTrack("7", "Chill Lofi", "Lofi Beats", "Study Session", "3:00", "relaxing", "Lofi", ""),
        new MusicTrack("8", "Dance Floor", "DJ Max", "Club Hits", "3:50", "energetic", "Electronic", ""),
        new MusicTrack("9", "Acoustic Morning", "Coffee Shop", "Unplugged", "3:25", "relaxing", "Acoustic", ""),
        new MusicTrack("10", "Power Up", "Electric Dreams", "Energy Boost", "4:10", "energetic", "Rock", "")
    );
    
    public MusicService() {
        // Initialize with a default playlist
        playlist = new ArrayList<>(musicDatabase.subList(0, 5));
        if (!playlist.isEmpty()) {
            currentTrack = playlist.get(0);
        }
        
        // Create sample playlists
        Playlist relaxingPlaylist = new Playlist("pl1", "Relaxing Vibes", "relaxing");
        relaxingPlaylist.setTracks(new ArrayList<>(Arrays.asList(
            musicDatabase.get(0), musicDatabase.get(1), musicDatabase.get(4), musicDatabase.get(6), musicDatabase.get(8)
        )));
        userPlaylists.put("pl1", relaxingPlaylist);
        
        Playlist energeticPlaylist = new Playlist("pl2", "Energy Boost", "energetic");
        energeticPlaylist.setTracks(new ArrayList<>(Arrays.asList(
            musicDatabase.get(3), musicDatabase.get(5), musicDatabase.get(7), musicDatabase.get(9)
        )));
        userPlaylists.put("pl2", energeticPlaylist);
    }
    
    public void play(String query) {
        // Search for matching track
        MusicTrack found = searchTrack(query);
        if (found != null) {
            addToHistory(currentTrack);
            currentTrack = found;
            isPlaying = true;
            isPaused = false;
        } else {
            // Play from playlist
            if (!playlist.isEmpty()) {
                addToHistory(currentTrack);
                currentTrack = playlist.get(currentTrackIndex);
                isPlaying = true;
                isPaused = false;
            }
        }
    }
    
    public void playTrack(String trackId) {
        for (MusicTrack track : musicDatabase) {
            if (track.getId().equals(trackId)) {
                addToHistory(currentTrack);
                currentTrack = track;
                isPlaying = true;
                isPaused = false;
                break;
            }
        }
    }
    
    public void pause() {
        if (isPlaying) {
            isPaused = true;
            isPlaying = false;
        }
    }
    
    public void resume() {
        if (isPaused) {
            isPlaying = true;
            isPaused = false;
        }
    }
    
    public void stop() {
        isPlaying = false;
        isPaused = false;
        cancelSleepTimer();
    }
    
    public void next() {
        if (!playlist.isEmpty()) {
            addToHistory(currentTrack);
            currentTrackIndex = (currentTrackIndex + 1) % playlist.size();
            currentTrack = playlist.get(currentTrackIndex);
            isPlaying = true;
            isPaused = false;
        }
    }
    
    public void previous() {
        if (!playlist.isEmpty()) {
            currentTrackIndex = (currentTrackIndex - 1 + playlist.size()) % playlist.size();
            currentTrack = playlist.get(currentTrackIndex);
            isPlaying = true;
            isPaused = false;
        }
    }
    
    public void setVolume(int volume) {
        this.volume = Math.max(0, Math.min(100, volume));
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void addToFavorites(String trackId) {
        favorites.add(trackId);
    }
    
    public void removeFromFavorites(String trackId) {
        favorites.remove(trackId);
    }
    
    public boolean isFavorite(String trackId) {
        return favorites.contains(trackId);
    }
    
    public Set<String> getFavorites() {
        return favorites;
    }
    
    public List<MusicTrack> getHistory() {
        return new ArrayList<>(history);
    }
    
    private void addToHistory(MusicTrack track) {
        if (track != null) {
            history.add(0, track);
            if (history.size() > 50) {
                history.remove(history.size() - 1);
            }
        }
    }
    
    public MusicTrack getCurrentTrack() {
        return currentTrack;
    }
    
    public boolean isPlaying() {
        return isPlaying;
    }
    
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("playing", isPlaying);
        status.put("paused", isPaused);
        status.put("volume", volume);
        status.put("currentTrack", currentTrack);
        status.put("playlistSize", playlist.size());
        status.put("historySize", history.size());
        status.put("favoritesCount", favorites.size());
        return status;
    }
    
    public List<MusicTrack> searchTrack(String query) {
        List<MusicTrack> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        
        for (MusicTrack track : musicDatabase) {
            if (track.getTitle().toLowerCase().contains(lowerQuery) ||
                track.getArtist().toLowerCase().contains(lowerQuery) ||
                track.getGenre().toLowerCase().contains(lowerQuery) ||
                track.getMood().toLowerCase().contains(lowerQuery)) {
                results.add(track);
            }
        }
        return results;
    }
    
    public List<MusicTrack> getTracksByMood(String mood) {
        List<MusicTrack> results = new ArrayList<>();
        for (MusicTrack track : musicDatabase) {
            if (track.getMood().equalsIgnoreCase(mood)) {
                results.add(track);
            }
        }
        return results;
    }
    
    public List<MusicTrack> getTracksByGenre(String genre) {
        List<MusicTrack> results = new ArrayList<>();
        for (MusicTrack track : musicDatabase) {
            if (track.getGenre().equalsIgnoreCase(genre)) {
                results.add(track);
            }
        }
        return results;
    }
    
    public Playlist createPlaylist(String name, String description, String mood) {
        String id = "pl" + System.currentTimeMillis();
        Playlist playlist = new Playlist(id, name, mood);
        playlist.setDescription(description);
        userPlaylists.put(id, playlist);
        return playlist;
    }
    
    public Playlist getPlaylist(String playlistId) {
        return userPlaylists.get(playlistId);
    }
    
    public Collection<Playlist> getAllPlaylists() {
        return userPlaylists.values();
    }
    
    public void addToPlaylist(String playlistId, String trackId) {
        Playlist pl = userPlaylists.get(playlistId);
        if (pl != null) {
            for (MusicTrack track : musicDatabase) {
                if (track.getId().equals(trackId)) {
                    pl.getTracks().add(track);
                    break;
                }
            }
        }
    }
    
    public void setSleepTimer(int minutes) {
        cancelSleepTimer();
        sleepTimer = new Timer();
        sleepTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                stop();
            }
        }, minutes * 60 * 1000L);
    }
    
    public void cancelSleepTimer() {
        if (sleepTimer != null) {
            sleepTimer.cancel();
            sleepTimer = null;
        }
    }
    
    public boolean hasSleepTimer() {
        return sleepTimer != null;
    }
    
    // Get recommendations based on current track
    public List<MusicTrack> getRecommendations() {
        List<MusicTrack> recommendations = new ArrayList<>();
        if (currentTrack != null) {
            String mood = currentTrack.getMood();
            String genre = currentTrack.getGenre();
            
            for (MusicTrack track : musicDatabase) {
                if (!track.equals(currentTrack) && 
                    (track.getMood().equals(mood) || track.getGenre().equals(genre))) {
                    recommendations.add(track);
                }
            }
        }
        return recommendations.size() > 5 ? recommendations.subList(0, 5) : recommendations;
    }
}
