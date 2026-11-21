# Quiz Application

A full-stack quiz app: a **Spring Boot REST API** with MySQL and a **React** single-page frontend.
Players pick a topic, answer multiple-choice questions, get a score with a per-question review,
and see a leaderboard.

## Features
- Topic-wise quizzes (Java, SQL, Spring Boot) seeded automatically on first run
- One-question-at-a-time flow with progress bar and previous/next navigation
- Server-side scoring (correct answers are never sent to the browser before submitting)
- Result page with answer review and top-10 leaderboard per quiz
- Request validation and JSON error responses; CORS configured for the React app

## Tech stack
| Layer | Technology |
|-------|-----------|
| Backend | Java 17, Spring Boot 3, Spring Web (REST), Spring Data JPA / Hibernate, Bean Validation |
| Database | MySQL |
| Frontend | React 18 (hooks), React Router, Vite, plain CSS |
| Build | Maven, npm |

## REST API
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/quizzes` | List all quizzes |
| GET | `/api/quizzes/{id}` | Quiz with its questions (no answers) |
| POST | `/api/quizzes/{id}/submit` | Body: `{ "playerName": "...", "answers": { "<questionId>": <optionIndex> } }` returns score and review |
| GET | `/api/quizzes/{id}/leaderboard` | Top 10 scores |

## Project structure
```
quiz-app
├── backend    Spring Boot API (model, repository, service, controller, dto, config)
└── frontend   React app (pages: Home, Quiz, Result; api.js)
```

## Run locally
**Backend** (needs JDK 17 and MySQL running)
1. Set your MySQL password: edit `backend/src/main/resources/application.properties`
   or set the `DB_USERNAME` / `DB_PASSWORD` environment variables.
2. Run `QuizApplication` (Eclipse: Run As > Java Application, or `mvn spring-boot:run`).
3. API runs at http://localhost:8081. The `quiz_db` database and sample quizzes are created automatically.

**Frontend** (needs Node.js 18+)
```
cd frontend
npm install
npm run dev
```
Open http://localhost:5173

## Screenshots
_Add screenshots here._
