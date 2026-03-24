# API Documentation

Complete API reference for AI Music Player.

Base URL: `http://localhost:8080/api`

---

## Authentication

Currently, no authentication is required. All endpoints are publicly accessible.

---

## Response Format

All responses follow this structure:

```json
{
  "success": true,
  "message": "Success",
  "data": { ... }
}
```

**Error Response:**
```json
{
  "success": false,
  "message": "Error description",
  "data": null
}
```

---

## Playback Control

### Play Music

**POST** `/music/play`

Start playing music.

**Request Body:**
```json
{
  "command": "play relaxing music"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Playing relaxing music for you",
  "data": {
    "playing": true,
    "paused": false,
    "volume": 70,
    "currentTrack": {
      "id": "2",
      "title": "Midnight Jazz",
      "artist": "Jazz Quartet",
      "album": "Night Sessions",
      "duration": "4:20",
      "mood": "relaxing",
      "genre": "Jazz"
    }
  }
}
```

---

### Pause

**POST** `/music/pause`

Pause current playback.

**Response:**
```json
{
  "success": true,
  "message": "Paused",
  "data": {
    "playing": false,
    "paused": true
  }
}
```

---

### Resume

**POST** `/music/resume`

Resume paused playback.

**Response:**
```json
{
  "success": true,
  "message": "Resumed",
  "data": {
    "playing": true,
    "paused": false
  }
}
```

---

### Stop

**POST** `/music/stop`

Stop playback completely.

**Response:**
```json
{
  "success": true,
  "message": "Stopped",
  "data": {
    "playing": false,
    "paused": false
  }
}
```

---

### Next Track

**POST** `/music/next`

Skip to next track in playlist.

**Response:**
```json
{
  "success": true,
  "message": "Next song",
  "data": { ... }
}
```

---

### Previous Track

**POST** `/music/previous`

Go to previous track.

**Response:**
```json
{
  "success": true,
  "message": "Previous song",
  "data": { ... }
}
```

---

### Get Status

**GET** `/music/status`

Get current playback status.

**Response:**
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "playing": true,
    "paused": false,
    "volume": 70,
    "currentTrack": { ... },
    "playlistSize": 5,
    "historySize": 12,
    "favoritesCount": 8
  }
}
```

---

## Volume Control

### Set Volume

**POST** `/music/volume`

Set volume level (0-100).

**Request Body:**
```json
{
  "volume": 80
}
```

**Response:**
```json
{
  "success": true,
  "message": "Volume set",
  "data": {
    "volume": 80
  }
}
```

---

### Get Volume

**GET** `/music/volume`

Get current volume level.

**Response:**
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "volume": 70
  }
}
```

---

## Search

### Search Tracks

**GET** `/music/search?query={query}`

Search for tracks by title, artist, genre, or mood.

**Parameters:**
- `query` (required): Search term

**Example:**
```
GET /music/search?query=jazz
```

**Response:**
```json
{
  "success": true,
  "message": "Search results",
  "data": {
    "tracks": [
      {
        "id": "2",
        "title": "Midnight Jazz",
        "artist": "Jazz Quartet",
        "genre": "Jazz",
        "mood": "relaxing"
      }
    ],
    "count": 1
  }
}
```

---

## Favorites

### Add to Favorites

**POST** `/music/favorite/{trackId}`

Add a track to favorites.

**Response:**
```json
{
  "success": true,
  "message": "Added to favorites",
  "data": null
}
```

---

### Remove from Favorites

**DELETE** `/music/favorite/{trackId}`

Remove a track from favorites.

**Response:**
```json
{
  "success": true,
  "message": "Removed from favorites",
  "data": null
}
```

---

### Get Favorites

**GET** `/music/favorites`

Get all favorite track IDs.

**Response:**
```json
{
  "success": true,
  "message": "Favorites",
  "data": {
    "ids": ["1", "3", "5"],
    "count": 3
  }
}
```

---

## History

### Get Play History

**GET** `/music/history?limit={limit}`

Get recently played tracks.

**Parameters:**
- `limit` (optional): Number of tracks to return (default: 10)

**Response:**
```json
{
  "success": true,
  "message": "Play history",
  "data": {
    "tracks": [ ... ],
    "count": 10
  }
}
```

---

## Playlists

### Get All Playlists

**GET** `/music/playlists`

Get all user playlists.

**Response:**
```json
{
  "success": true,
  "message": "Your playlists",
  "data": {
    "playlists": [
      {
        "id": "pl1",
        "name": "Relaxing Vibes",
        "description": "Calm and peaceful tracks",
        "mood": "relaxing",
        "tracks": [ ... ],
        "createdAt": "2026-03-24T10:00:00"
      }
    ],
    "count": 2
  }
}
```

---

### Get Playlist

**GET** `/music/playlist/{id}`

Get details of a specific playlist.

**Response:**
```json
{
  "success": true,
  "message": "Relaxing Vibes",
  "data": {
    "id": "pl1",
    "name": "Relaxing Vibes",
    "mood": "relaxing",
    "tracks": [ ... ]
  }
}
```

---

### Create Playlist

**POST** `/music/playlist`

Create a new playlist.

**Request Body:**
```json
{
  "name": "My Playlist",
  "description": "My favorite songs",
  "mood": "happy"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Playlist created",
  "data": {
    "id": "pl1234567890",
    "name": "My Playlist",
    "description": "My favorite songs",
    "mood": "happy",
    "tracks": [],
    "createdAt": "2026-03-24T10:00:00"
  }
}
```

---

### Add Track to Playlist

**POST** `/music/playlist/{id}/add`

Add a track to an existing playlist.

**Request Body:**
```json
{
  "trackId": "5"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Track added",
  "data": null
}
```

---

## Recommendations

### Get Recommendations

**GET** `/music/recommendations`

Get song recommendations based on current track.

**Response:**
```json
{
  "success": true,
  "message": "Recommended for you",
  "data": {
    "tracks": [ ... ],
    "count": 5
  }
}
```

---

## Sleep Timer

### Set Sleep Timer

**POST** `/music/sleep-timer`

Set sleep timer to stop music after specified minutes.

**Request Body:**
```json
{
  "minutes": 30
}
```

**Response:**
```json
{
  "success": true,
  "message": "Sleep timer set for 30 minutes",
  "data": null
}
```

---

### Cancel Sleep Timer

**DELETE** `/music/sleep-timer`

Cancel active sleep timer.

**Response:**
```json
{
  "success": true,
  "message": "Sleep timer cancelled",
  "data": null
}
```

---

### Get Sleep Timer Status

**GET** `/music/sleep-timer`

Check if sleep timer is active.

**Response:**
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "active": true
  }
}
```

---

## AI Commands

### Process AI Command

**POST** `/ai/command`

Process a natural language command using AI.

**Request Body:**
```json
{
  "command": "play some relaxing jazz music"
}
```

**Response:**
```json
{
  "success": true,
  "message": "Playing relaxing music for you",
  "data": {
    "action": "play",
    "mood": "relaxing",
    "genre": "Jazz",
    "status": {
      "playing": true,
      "currentTrack": { ... }
    }
  }
}
```

**Supported Commands:**
- "play [mood] music" - e.g., "play relaxing music"
- "play [genre] music" - e.g., "play jazz music"
- "pause" / "resume" / "stop"
- "next song" / "previous song"
- "volume up" / "volume down"
- "set volume to 50"
- "add to favorites"
- "show history"
- "create playlist"
- "recommend something"
- "set sleep timer for 30 minutes"

---

### Analyze Mood from Command

**GET** `/ai/mood/{command}`

Analyze the mood from a text command.

**Example:**
```
GET /ai/mood/play%20some%20relaxing%20music
```

**Response:**
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "mood": "relaxing",
    "description": "Calm and peaceful tracks to help you unwind",
    "tracks": [ ... ]
  }
}
```

---

## Error Codes

| HTTP Status | Meaning |
|-------------|---------|
| 200 | Success |
| 400 | Bad Request - Invalid parameters |
| 404 | Not Found - Resource doesn't exist |
| 500 | Internal Server Error |

---

## Rate Limiting

Currently, no rate limiting is implemented.

---

## CORS

CORS is enabled for all origins (`*`). In production, configure specific allowed origins in `MusicController`.

---

## Version

API Version: 1.0.0
