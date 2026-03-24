# 🎵 AI Music Player with Qwen LLM

An intelligent music player powered by **Qwen Large Language Model** that understands natural language commands to control music playback.

## ✨ Features

### 🎤 AI-Powered Control
- **Natural Language Commands** - Just say "play relaxing music" or "create a happy playlist"
- **Mood Detection** - AI understands relaxing, happy, energetic, sad moods
- **Genre Recognition** - Supports Jazz, Rock, Pop, Classical, Electronic, Lofi, and more
- **Smart Recommendations** - Get song suggestions based on what you're listening to

### 🎵 Music Management
- **Playback Control** - Play, pause, resume, stop, next, previous
- **Volume Control** - Adjust volume with voice commands or slider
- **Favorites** - Save your favorite tracks
- **Play History** - Track what you've listened to
- **Custom Playlists** - Create and manage playlists

### ⏰ Smart Features
- **Sleep Timer** - Set timer to stop music automatically (10-120 minutes)
- **Quick Actions** - One-click access to common moods and genres
- **Real-time Status** - See what's playing with progress bar

### 🌐 Web Interface
- **Beautiful UI** - Modern gradient design with smooth animations
- **Responsive** - Works on desktop, tablet, and mobile
- **Real-time Updates** - Status syncs every 3 seconds

### 🔌 RESTful API
- Complete API for integration with other apps
- CORS enabled for cross-origin requests
- JSON responses for easy parsing

---

## 🏗️ Architecture

```
┌─────────────────┐     ┌──────────────┐     ┌─────────────────┐
│   Web Browser   │────▶│ Spring Boot  │────▶│   Qwen API      │
│   (Frontend)    │◀────│   Backend    │◀────│   (AI Model)    │
│                 │     │              │◀────│   (or local)    │
└─────────────────┘     └──────────────┘
                               │
                               ▼
                        ┌──────────────┐
                        │ Music Database│
                        │ (Built-in)   │
                        └──────────────┘
```

---

## 📁 Project Structure

```
ai-music-player/
├── README.md                        # This file
├── pom.xml                          # Maven dependencies
├── .gitignore
├── src/main/
│   ├── java/com/josh/music/
│   │   ├── MusicPlayerApplication.java    # Main entry point
│   │   ├── controller/
│   │   │   └── MusicController.java       # REST API endpoints
│   │   ├── service/
│   │   │   ├── QwenService.java           # Qwen AI integration
│   │   │   └── MusicService.java          # Music playback logic
│   │   └── model/
│   │       ├── MusicTrack.java            # Track model
│   │       ├── Playlist.java              # Playlist model
│   │       ├── AiCommandRequest.java      # AI command model
│   │       └── ApiResponse.java           # API response model
│   └── resources/
│       ├── application.yml                # Configuration
│       └── static/
│           └── index.html                 # Web UI
└── docs/
    └── API.md                             # API documentation
```

---

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Qwen API Key (optional - has local fallback)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/joshzhao0218/ai-music-player.git
cd ai-music-player
```

2. **Configure API Key (Optional)**

Edit `src/main/resources/application.yml`:
```yaml
qwen:
  api-key: YOUR_QWEN_API_KEY  # Leave as 'demo-key' for local parsing
  model: qwen-plus
```

3. **Build the project**
```bash
mvn clean install
```

4. **Run the application**
```bash
mvn spring-boot:run
```

5. **Open in browser**
```
http://localhost:8080
```

---

## 🎮 Usage Examples

### Voice/Text Commands

| Command | Action |
|---------|--------|
| "Play some relaxing music" | Plays calming songs |
| "Play jazz music" | Searches and plays jazz |
| "Pause" | Pauses current song |
| "Resume" | Resumes playback |
| "Next song" | Skips to next track |
| "Volume up" / "Volume down" | Adjusts volume |
| "Create a happy playlist" | AI generates upbeat playlist |
| "What's playing?" | Shows current track info |
| "Set sleep timer for 30 minutes" | Sets auto-stop timer |
| "Add to favorites" | Saves current track |
| "Show my history" | Displays play history |
| "Recommend something" | Gets song suggestions |

### Quick Actions (UI)

Click the quick action buttons for instant playback:
- 😌 Relax - Calming music
- 😊 Happy - Upbeat songs
- ⚡ Energy - Workout music
- 🎷 Jazz - Jazz genre
- 🎤 Pop - Pop hits
- 🎻 Classical - Classical pieces

### API Examples

**Play Music with AI Command**
```bash
curl -X POST http://localhost:8080/api/ai/command \
  -H "Content-Type: application/json" \
  -d '{"command": "play relaxing music"}'
```

**Get Current Status**
```bash
curl http://localhost:8080/api/music/status
```

**Search Songs**
```bash
curl "http://localhost:8080/api/music/search?query=jazz"
```

**Set Volume**
```bash
curl -X POST http://localhost:8080/api/music/volume \
  -H "Content-Type: application/json" \
  -d '{"volume": 80}'
```

**Get Recommendations**
```bash
curl http://localhost:8080/api/music/recommendations
```

---

## 📚 API Reference

### Playback Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/music/play` | Play music |
| POST | `/api/music/pause` | Pause playback |
| POST | `/api/music/resume` | Resume playback |
| POST | `/api/music/stop` | Stop playback |
| POST | `/api/music/next` | Next track |
| POST | `/api/music/previous` | Previous track |
| GET | `/api/music/status` | Get playback status |

### Volume Control

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/music/volume` | Set volume (0-100) |
| GET | `/api/music/volume` | Get current volume |

### Search & Discovery

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/music/search?query=` | Search tracks |
| GET | `/api/music/recommendations` | Get recommendations |
| GET | `/api/ai/mood/{command}` | Analyze mood from command |

### Favorites

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/music/favorite/{trackId}` | Add to favorites |
| DELETE | `/api/music/favorite/{trackId}` | Remove from favorites |
| GET | `/api/music/favorites` | Get all favorites |

### Playlists

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/music/playlists` | List all playlists |
| GET | `/api/music/playlist/{id}` | Get playlist details |
| POST | `/api/music/playlist` | Create new playlist |
| POST | `/api/music/playlist/{id}/add` | Add track to playlist |

### Sleep Timer

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/music/sleep-timer` | Set sleep timer (minutes) |
| DELETE | `/api/music/sleep-timer` | Cancel sleep timer |
| GET | `/api/music/sleep-timer` | Check timer status |

### AI Commands

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/ai/command` | Process natural language command |

---

## ⚙️ Configuration

### application.yml

```yaml
server:
  port: 8080

qwen:
  api-key: ${QWEN_API_KEY:demo-key}  # Use env var or default
  model: qwen-plus
  base-url: https://dashscope.aliyuncs.com/api/v1

music:
  source: spotify  # or 'local'
  default-volume: 70

logging:
  level:
    root: INFO
    com.josh: DEBUG
```

---

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.2.0
- **AI Model**: Qwen (Alibaba Cloud DashScope)
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Build Tool**: Maven
- **API**: RESTful JSON
- **Styling**: Custom CSS with gradients and animations

---

## 🎯 AI Features

### Mood Detection
The AI recognizes these moods:
- **relaxing** - Calm, peaceful, sleep, meditation
- **happy** - Cheerful, upbeat, fun, joyful
- **energetic** - Workout, gym, run, energy
- **sad** - Melancholy, emotional

### Genre Recognition
Supported genres:
- Jazz, Rock, Pop, Classical
- Electronic, EDM, Lofi
- Acoustic, Hip-Hop, R&B

### Command Parsing
- Works with **Qwen API** when API key is provided
- Falls back to **local parsing** for demo/offline use
- Understands context and intent

---

## 📝 Sample Response

**Request:**
```json
POST /api/ai/command
{"command": "play some relaxing jazz music"}
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
      "currentTrack": {
        "title": "Midnight Jazz",
        "artist": "Jazz Quartet",
        "mood": "relaxing",
        "genre": "Jazz"
      },
      "volume": 70
    }
  }
}
```

---

## 🔒 Security Notes

- API key is stored in `application.yml` (not committed to git)
- Use environment variable `QWEN_API_KEY` in production
- CORS is enabled for all origins (customize for production)

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

---

## 📄 License

MIT License

---

## 👨‍💻 Author

**Josh Zhao** - High school AI enthusiast  
GitHub: [@joshzhao0218](https://github.com/joshzhao0218)

---

*Enjoy music with AI!* 🎵🤖
