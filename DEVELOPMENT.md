# 🎵 AI Music Player - Development Guide

## Project Overview

AI Music Player is a Spring Boot application that provides intelligent music playback control using natural language processing powered by Qwen AI.

---

## Development Environment Setup

### Prerequisites

- **Java**: 17 or higher
- **Maven**: 3.6+
- **Git**: Latest version
- **IDE**: IntelliJ IDEA / Eclipse / VS Code (recommended: IntelliJ)

### Setup Steps

1. **Clone the repository**
```bash
git clone https://github.com/joshzhao0218/ai-music-player.git
cd ai-music-player
```

2. **Configure environment**
```bash
cp .env.example .env
# Edit .env with your API key
```

3. **Build the project**
```bash
mvn clean install
```

4. **Run tests**
```bash
mvn test
```

5. **Run application**
```bash
mvn spring-boot:run
```

6. **Access UI**
```
http://localhost:8080
```

---

## Project Structure

```
ai-music-player/
├── src/
│   ├── main/
│   │   ├── java/com/josh/music/
│   │   │   ├── MusicPlayerApplication.java    # Entry point
│   │   │   ├── controller/                    # REST controllers
│   │   │   │   └── MusicController.java
│   │   │   ├── service/                       # Business logic
│   │   │   │   ├── MusicService.java
│   │   │   │   └── QwenService.java
│   │   │   ├── model/                         # Data models
│   │   │   │   ├── MusicTrack.java
│   │   │   │   ├── Playlist.java
│   │   │   │   ├── AiCommandRequest.java
│   │   │   │   └── ApiResponse.java
│   │   │   └── exception/                     # Exception handling
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       ├── application.yml                # Configuration
│   │       └── static/                        # Frontend
│   │           └── index.html
│   └── test/
│       ├── java/com/josh/music/
│       │   └── service/
│       │       ├── MusicServiceTest.java
│       │       └── QwenServiceTest.java
│       └── resources/
│           └── application-test.yml
├── docs/
│   └── API.md                                 # API documentation
├── Dockerfile                                 # Docker build
├── docker-compose.yml                         # Docker Compose
├── pom.xml                                    # Maven config
└── README.md                                  # Project overview
```

---

## Architecture

### Layers

1. **Controller Layer** (`controller/`)
   - Handles HTTP requests
   - Validates input
   - Returns JSON responses

2. **Service Layer** (`service/`)
   - Business logic
   - AI command processing
   - Music playback management

3. **Model Layer** (`model/`)
   - Data transfer objects
   - Request/response models

4. **Exception Layer** (`exception/`)
   - Global error handling
   - Custom error responses

### Data Flow

```
User Request → Controller → Service → Music Database
                          ↓
                       Qwen AI (optional)
```

---

## Key Components

### MusicService

Manages all music playback operations:
- Playback control (play, pause, resume, stop)
- Track navigation (next, previous)
- Volume control
- Favorites management
- Play history
- Playlist management
- Sleep timer
- Recommendations

**Key Methods:**
```java
void play(String query)
void pause()
void resume()
void next()
void previous()
void setVolume(int volume)
void addToFavorites(String trackId)
List<MusicTrack> searchTrack(String query)
Playlist createPlaylist(String name, String desc, String mood)
void setSleepTimer(int minutes)
```

### QwenService

Processes natural language commands:
- AI-powered command parsing (with Qwen API)
- Local fallback parsing
- Mood detection
- Genre recognition

**Supported Moods:**
- relaxing
- happy
- energetic
- sad

**Supported Genres:**
- Jazz, Rock, Pop, Classical
- Electronic, Lofi, Acoustic

---

## Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn test -Dtest=MusicServiceTest
```

### Test Coverage
```bash
mvn clean test jacoco:report
```

### Test Categories

1. **Unit Tests** (`*Test.java`)
   - Test individual components
   - Mock external dependencies

2. **Integration Tests** (`*IT.java`)
   - Test component interactions
   - Use embedded server

---

## Building

### Development Build
```bash
mvn clean package
```

### Production Build (Skip Tests)
```bash
mvn clean package -DskipTests
```

### Check for Dependency Updates
```bash
mvn versions:display-dependency-updates
```

---

## Running

### Local Development
```bash
mvn spring-boot:run
```

### With Custom Profile
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### JAR File
```bash
java -jar target/ai-music-player-1.0.0.jar
```

### Docker
```bash
docker build -t ai-music-player .
docker run -p 8080:8080 ai-music-player
```

### Docker Compose
```bash
docker-compose up -d
```

---

## Configuration

### application.yml

Key configuration sections:

**Server:**
```yaml
server:
  port: 8080
```

**Qwen AI:**
```yaml
qwen:
  api-key: ${QWEN_API_KEY:demo-key}
  model: qwen-plus
  base-url: https://dashscope.aliyuncs.com/api/v1
```

**Music:**
```yaml
music:
  source: local
  default-volume: 70
  max-history-size: 50
```

**Logging:**
```yaml
logging:
  level:
    com.josh.music: DEBUG
  file:
    name: logs/ai-music-player.log
```

---

## API Development

### Adding New Endpoint

1. **Add method to Controller:**
```java
@PostMapping("/music/new-feature")
@ResponseBody
public ApiResponse newFeature(@RequestBody RequestDto request) {
    // Implementation
    return ApiResponse.ok("Success", data);
}
```

2. **Add business logic to Service:**
```java
public void newFeature(String param) {
    // Implementation
}
```

3. **Add tests:**
```java
@Test
void testNewFeature() {
    // Test implementation
}
```

4. **Update API documentation:**
Edit `docs/API.md`

---

## Debugging

### Enable Debug Logging
```yaml
logging:
  level:
    root: DEBUG
    com.josh.music: DEBUG
```

### View Logs
```bash
tail -f logs/ai-music-player.log
```

### Docker Logs
```bash
docker logs -f ai-music-player
```

---

## Common Issues

### Port Already in Use
```bash
# Change port in application.yml
server:
  port: 8081
```

### Qwen API Connection Failed
- Check API key is valid
- Verify network connection
- Use local fallback (demo-key)

### Build Fails
```bash
# Clean and rebuild
mvn clean install -U
```

---

## Code Style

- Follow Java naming conventions
- Use meaningful variable names
- Add JavaDoc for public methods
- Keep methods small and focused
- Write tests for new features

---

## Git Workflow

### Feature Branch
```bash
git checkout -b feature/new-feature
# Make changes
git add .
git commit -m "feat: add new feature"
git push origin feature/new-feature
```

### Pull Request
1. Push feature branch
2. Create PR on GitHub
3. Request review
4. Merge after approval

---

## Deployment

### Production Checklist

- [ ] Set production API keys
- [ ] Configure logging level to INFO
- [ ] Enable security (if needed)
- [ ] Set up monitoring
- [ ] Configure backup strategy
- [ ] Test in staging environment

### Environment Variables

Production environment should use:
```bash
QWEN_API_KEY=production-key
SERVER_PORT=8080
JAVA_OPTS=-Xmx1024m -Xms512m
LOG_LEVEL=INFO
```

---

## Monitoring

### Health Check
```bash
curl http://localhost:8080/actuator/health
```

### Metrics
```bash
curl http://localhost:8080/actuator/metrics
```

---

## Contributing

1. Fork the repository
2. Create feature branch
3. Make changes
4. Write tests
5. Submit pull request

---

## Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Qwen API Documentation](https://help.aliyun.com/zh/dashscope/)
- [Maven Documentation](https://maven.apache.org/guides/)

---

**Last Updated**: 2026-03-24  
**Version**: 1.0.0
