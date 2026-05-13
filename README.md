# Beedle Deals

**Beedle Deals** is a promotional deal broadcasting bot built with Spring Boot 4 and Java 17. It enables you to send affiliate product deals to registered chat channels through a simple REST API, following a **hexagonal (ports & adapters) architecture** for clean separation of concerns and easy extensibility.

## Architecture

The project is structured around hexagonal architecture principles, dividing the codebase into three distinct layers:

```
                     +-----------------------------+
                     |      INBOUND ADAPTERS       |
                     |  (driving / primary side)   |
                     |                             |
                     |  Web (REST controller)      |
                     |  Discord (slash command)    |
                     +------------+----------------+
                                  |
                                  | calls
                                  v
                     +-----------------------------+
                     |      APPLICATION LAYER      |
                     |   (use-case orchestrators)  |
                     |                             |
                     |  DealApplication            |
                     |  GroupApplication           |
                     +------------+----------------+
                                  |
                    implements    |    depends on
                      +-----------+-----------+
                      |                       |
                      v                       v
          +------------------+    +---------------------------+
          |   INBOUND PORTS  |    |    OUTBOUND PORTS         |
          | (use case ifaces)|    | (repository/gateway        |
          |                  |    |  interfaces)              |
          | PostDealUseCase  |    | NotificationServicePort   |
          | GroupUseCases    |    | GroupRepositoryPort        |
          +------------------+    +------------+---------------+
                                                |
                                   implements   |    depends on
                                      +---------+---------+
                                      |                   |
                                      v                   v
                          +-------------------+  +-------------------+
                          |  OUTBOUND ADAPTERS |  | OUTBOUND ADAPTERS |
                          | (driven side)      |  |                   |
                          |                    |  |                   |
                          | DiscordNotif.     |  | GroupRepoAdapter  |
                          | Service           |  | (JPA/PostgreSQL)  |
                          +-------------------+  +-------------------+
```

- **Core / Domain (`core/`)** — Pure Java with zero framework dependencies. Contains domain entities (`Product`, `Group`), inbound ports (use-case interfaces like `PostDealUseCase`, `GroupUseCases`), and outbound ports (interfaces like `NotificationServicePort`, `GroupRepositoryPort`).
- **Application (`application/`)** — Orchestrates use cases by implementing the inbound ports, coordinating between inbound adapters and outbound ports.
- **Adapters (`adapters/`)** — All framework and infrastructure code.
  - **Inbound adapters (`adapters/in/`):** Receive external input via REST (`POST /api/v1/notifications`) or Discord slash commands (`/add-channel`).
  - **Outbound adapters (`adapters/out/`):** Implement the outbound ports for actual side effects — sending Discord embeds (`DiscordNotificationService`) and persisting group registrations (`GroupRepositoryAdapter` via JPA/PostgreSQL).

This design makes it straightforward to add new platforms. For example, **Telegram** and **WhatsApp** can be added by simply implementing `NotificationServicePort` with their respective SDKs and creating the corresponding inbound adapters — no core or application code changes required.

## Features

- **Discord integration** — Register channels via `/add-channel` slash command and receive rich embed notifications with product name, price in BRL, image, and affiliate link.
- **REST API** — `POST /api/v1/notifications` to broadcast a deal to any registered channel.
- **API key authentication** — Protected behind an `X-API-KEY` header check in production mode.
- **PostgreSQL persistence** — Channel registrations stored in PostgreSQL with Flyway migrations.
- **OpenAPI docs** — Auto-generated API documentation via SpringDoc.
- **Docker support** — Multi-stage Docker build and docker-compose setup.

## Quick Start

### Prerequisites

- Java 17+ (for local development)
- Docker and Docker Compose (for containerized setup)
- A Discord bot token with the `applications.commands` scope enabled

### Environment Variables

| Variable        | Description                        |
|-----------------|------------------------------------|
| `DB_URL`        | PostgreSQL JDBC URL (host:port)    |
| `DB_USER`       | PostgreSQL user                    |
| `DB_PASSWORD`   | PostgreSQL password                |
| `DISCORD_TOKEN` | Discord bot token                  |
| `APP_MODE`      | Spring profile (`prod` or `dev`)   |
| `MASTER_API_KEY`| API key for REST endpoint auth     |

### Running with Docker Compose

```bash
# Create a .env file with your secrets
echo "DB_URL=db
DB_USER=beedle
DB_PASSWORD=root
DISCORD_TOKEN=your_discord_bot_token
APP_MODE=prod
MASTER_API_KEY=your_api_key" > .env

# Start the stack
docker compose up -d
```

The API will be available at `http://localhost:8080` and the Discord bot will join your servers.

### Pulling the Image

Pre-built images are available on GitHub Container Registry:

```bash
docker pull ghcr.io/dias-andre/beedle:latest
```

### Building Locally

```bash
./mvnw clean package -DskipTests
java -jar target/beedle-deals-*.jar
```

## API Usage

### Send a Deal Notification

```bash
curl -X POST http://localhost:8080/api/v1/notifications \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: your_api_key" \
  -d '{
    "product": {
      "name": "Gaming Headset",
      "affiliateUrl": "https://example.com/deal?ref=beedle",
      "imageUrl": "https://example.com/headset.jpg",
      "priceInCents": 19990,
      "discountPercent": 25
    },
    "externalGroupId": "123456789012345678"
  }'
```

The bot will send a Discord embed with the product details to the channel matching `externalGroupId`.

## Tech Stack

| Technology         | Version      |
|--------------------|--------------|
| Java               | 17           |
| Spring Boot        | 4.0.6        |
| JDA (Discord)      | 6.4.1        |
| PostgreSQL         | 16           |
| Flyway             | (Boot-managed) |
| SpringDoc OpenAPI  | 3.0.2        |
| Lombok             | (Boot-managed) |

## License

MIT
