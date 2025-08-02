
# 🐾 PetNexus AI — Intelligent Chatbot for Pet Healthcare

**PetNexus AI** is an intelligent chatbot service designed to assist pet owners with healthcare-related inquiries. It allows authenticated users to chat with a polite, AI-driven assistant that provides contextual information about veterinary appointments, procedures, and health tips.

> 🧠 Built as part of the **AI-Powered Chatbot Order Status Challenge**.

---

## 📸 Preview

| Login Page | Chatbot Session |
|------------|-----------------|
| ![Login](docs/screenshots/login.png) | ![Chat](docs/screenshots/chat.png) |

---

## 🚀 Features

- ✅ RESTful endpoints for registration, login, and chat
- 🔐 JWT-based authentication
- 💬 Chat endpoint integrated with OpenAI
- 📚 Retrieval-Augmented Generation (RAG) with local knowledge base
- 🧪 Full test suite with controller + service layer mocks
- 🐳 Dockerized & ready for Azure deployment
- 🔁 CI/CD pipeline via `azure-pipelines.yml`

---

## 📦 Tech Stack

| Layer          | Tech                          |
|----------------|-------------------------------|
| Backend        | Java 17 + Spring Boot         |
| Database       | PostgreSQL (can switch to H2) |
| AI Integration | OpenAI API (GPT models)       |
| Security       | Spring Security + JWT         |
| Testing        | JUnit 5 + MockMvc             |
| DevOps         | Docker + Azure Pipelines      |

---

## 🧪 API Endpoints

### Auth

- `POST /auth/register` — User registration
- `POST /auth/login` — Login and receive JWT

### Chat

- `POST /chat` — Submit a message and receive AI-generated response (JWT required)

---

## 📚 Knowledge Base (RAG)

The chatbot enriches prompts by dynamically retrieving relevant documents from a local JSON-based knowledge base, simulating Retrieval-Augmented Generation (RAG) logic.

```json
{
  "id": "faq_001",
  "question": "How often should I deworm my puppy?",
  "answer": "Puppies should be dewormed every 2 weeks until 12 weeks old."
}
```

---

## 🛠️ Getting Started

### Prerequisites

- Java 17+
- Apache Maven
- PostgreSQL
- OpenAI API key
- JWT secret

### Running Locally

```bash
# Clone the repo
git clone https://your-repo-url.git
cd PetNexusAI

# Set env variables or edit application.properties
export OPENAI_API_KEY=your-key
export JWT_SECRET=your-secret

# Run the app
mvn spring-boot:run
```

---

## 🧪 Running Tests

```bash
mvn test
```

Includes:
- Auth flow tests
- Chat controller and fallback logic
- Negative and unauthorized scenarios

---

## 🐳 Docker

```bash
docker build -t petnexus-ai .
docker run -p 8080:8080 petnexus-ai
```

Also includes `docker-compose.yml` for local DB setup.

---

## ☁️ Deployment on Azure

1. Push Docker image to Azure Container Registry
2. Create App Service to host container
3. Configure environment variables (API key, DB)
4. Use `azure-pipelines.yml` for automatic CI/CD

---

## ✅ Challenge Checklist

| Requirement                         | Status |
|-------------------------------------|--------|
| User Stories                        | ✅ Done (`user-stories.md`) |
| Auth endpoints + JWT                | ✅ Done |
| Chat API with OpenAI integration    | ✅ Done |
| Knowledge base retrieval (RAG)      | ✅ Done |
| Unit + Integration Tests            | ✅ Done |
| CI/CD via Azure Pipelines           | ✅ Done |
| Cloud deployment (Docker)           | ✅ Ready |
| Bonus: Frontend integration         | 🟡 In Progress |
| Bonus: Observability & Metrics      | ⬜ Not included |

---

## 📄 License

MIT License

---

Made with 💙 by Camilo Ñustes for the AI Workshop.
