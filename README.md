# 🎵 AI Music Player with Qwen LLM

An intelligent music player powered by **Qwen Large Language Model** that understands natural language commands to control music playback.

## ✨ Features

- 🎤 **Voice/Text Control** - Control playback using natural language
- 🤖 **Qwen AI Integration** - Powered by Alibaba's Qwen LLM
- 🎵 **Music Playback** - Play, pause, skip, search songs
- 🌐 **Web Interface** - Clean and intuitive UI
- 🔌 **RESTful API** - Easy integration with other apps
- 📝 **Smart Playlists** - AI-generated playlists based on mood

## 🏗️ Architecture

```
┌─────────────────┐     ┌──────────────┐     ┌─────────────────┐
│   Web Browser   │────▶│ Spring Boot  │────▶│   Qwen API      │
│   (Frontend)    │◀────│   Backend    │◀────│   (AI Model)    │
└─────────────────┘     └──────────────┘     └─────────────────┘
                              │
                              ▼
                       ┌──────────────┐
                       │ Music Source │
                       │ (Spotify/MP3)│
                       └──────────────┘
```

## 📁 Project Structure

```
ai-music-player/
├── README.md                    # This file
├── pom.xml                      # Maven dependencies
├── .gitignore
├── src/
│   └── main/
│       ├── java/com/josh/music/
│       │   ├── MusicPlayerApplication.java    # Main entry point
│       │   ├── controller/
│       │   │   └── MusicController.java       # REST API endpoints
│       │   ├── service/
│       │   │   ├── QwenService.java           # Qwen AI integration
│       │   │   └── MusicService.java          # Music playback logic
│       │   └── model/
│       │       └── MusicCommand.java          # Command model
│       └── resources/
│           ├── application.yml                # Configuration
│           └── static/
│               └── index.html                 # Web UI
└── docs/
    └── API.md                                 # API documentation
```

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Qwen API Key (from Alibaba Cloud)

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/joshzhao0218/ai-music-player.git
cd ai-music-player
```

2. **Configure API Key**

Edit `src/main/resources/application.yml`:
```yaml
qwen:
  api-key: YOUR_QWEN_API_KEY
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

## 🎮 Usage Examples

### Voice/Text Commands

| Command | Action |
|---------|--------|
| "Play some relaxing music" | Plays calming songs |
| "Pause" | Pauses current song |
| "Next song" | Skips to next track |
| "Play jazz music" | Searches and plays jazz |
| "Create a happy playlist" | AI generates upbeat playlist |
| "What's playing?" | Shows current track info |
| "Volume up" | Increases volume |
| "Stop music" | Stops playback |

### API Examples

**Play Music**
```bash
curl -X POST http://localhost:8080/api/music/play \
  -H "Content-Type: application/json" \
  -d '{"command": "play some pop music"}'
```

**Get Current Status**
```bash
curl http://localhost:8080/api/music/status
```

**Pause Playback**
```bash
curl -X POST http://localhost:8080/api/music/pause
```

## 🖥️ User Interface

### Main Screen

```
┌────────────────────────────────────────────┐
│         🎵 AI Music Player                 │
├────────────────────────────────────────────┤
│                                            │
│   ┌──────────────────────────────────┐    │
│   │     Now Playing                  │    │
│   │     Song: Summer Breeze          │    │
│   │     Artist: The Waves            │    │
│   │     Album: Ocean Sounds          │    │
│   │     [▮▮▮▮▮▮▮▯▯▯] 2:34 / 4:12    │    │
│   └──────────────────────────────────┘    │
│                                            │
│   ⏮️  ⏸️  ▶️  ⏭️  🔊                       │
│                                            │
├────────────────────────────────────────────┤
│  💬 Ask AI: "play something relaxing"     │
│  [_________________________________] [🎵] │
└────────────────────────────────────────────┘
```

## ⚙️ Configuration

### application.yml

```yaml
server:
  port: 8080

qwen:
  api-key: ${QWEN_API_KEY}
  model: qwen-plus
  base-url: https://dashscope.aliyuncs.com/api/v1

music:
  source: spotify  # or 'local'
  default-volume: 70
```

## 📚 API Documentation

See [docs/API.md](docs/API.md) for complete API reference.

### Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/music/play` | Play music with AI command |
| POST | `/api/music/pause` | Pause current song |
| POST | `/api/music/next` | Skip to next song |
| GET | `/api/music/status` | Get playback status |
| POST | `/api/music/search` | Search for songs |
| GET | `/api/ai/chat` | Chat with AI assistant |

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.x
- **AI Model**: Qwen (Alibaba Cloud)
- **Frontend**: HTML5, CSS3, JavaScript
- **Build Tool**: Maven
- **API**: RESTful

## 📝 License

MIT License - See [LICENSE](LICENSE) file

## 👨‍💻 Author

**Josh Zhao** - High school AI enthusiast

---

*Enjoy music with AI!* 🎵🤖
