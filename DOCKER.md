# Docker Configuration for AI Music Player

## Build Docker Image

```bash
docker build -t ai-music-player .
```

## Run Container

```bash
docker run -d \
  -p 8080:8080 \
  -e QWEN_API_KEY=your-api-key \
  --name ai-music-player \
  ai-music-player
```

## Docker Compose

```yaml
version: '3.8'

services:
  ai-music-player:
    build: .
    ports:
      - "8080:8080"
    environment:
      - QWEN_API_KEY=${QWEN_API_KEY}
      - JAVA_OPTS=-Xmx512m
    volumes:
      - ./logs:/app/logs
    restart: unless-stopped
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/api/music/status"]
      interval: 30s
      timeout: 10s
      retries: 3
```

## Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `QWEN_API_KEY` | Qwen AI API key | `demo-key` |
| `SERVER_PORT` | Server port | `8080` |
| `JAVA_OPTS` | JVM options | `-Xmx512m` |
| `LOG_LEVEL` | Logging level | `INFO` |

## Production Deployment

```bash
# Build for production
docker build -t ai-music-player:latest .

# Push to registry
docker push registry.example.com/ai-music-player:latest

# Deploy with docker-compose
docker-compose -f docker-compose.prod.yml up -d
```

## Logs

```bash
# View logs
docker logs ai-music-player

# Follow logs
docker logs -f ai-music-player

# View last 100 lines
docker logs --tail 100 ai-music-player
```

## Health Check

```bash
curl http://localhost:8080/actuator/health
```
