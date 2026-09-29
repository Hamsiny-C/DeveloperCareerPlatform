# DevCareer OS

A personal **Developer Career Operating System** — track your skills, DSA practice,
learning progress, projects, certifications, job applications and interviews,
all in one place, with a single dashboard that shows how "placement ready" you are.

Built with **Spring Boot + MySQL** (backend) and **React** (frontend).
Runs entirely on your own machine — no Docker, no cloud, nothing to deploy.
You'll add that yourself later once you're ready.

---

## 1. Required Software

Install these before you start:

| Tool | Version | Notes |
|---|---|---|
| JDK | 21 or 25 | The project compiles to Java 21 bytecode (LTS), which runs perfectly fine on a JDK 25 installation — Java is backward compatible. |
| Maven | 3.9+ | Usually bundled with Spring Tools for Eclipse / most IDEs |
| MySQL | 8.x | MySQL Community Server + MySQL Workbench (or any MySQL client) |
| Node.js | 18+ | For running the React frontend (includes npm) |
| An IDE | — | Spring Tools for Eclipse / IntelliJ / VS Code — whatever you prefer |

Check your installed versions:

```bash
java -version
mvn -version
mysql --version
node -version
npm -version
```

---

## 2. Project Structure

```
devcareer-os/
├── backend/                  Spring Boot application (Maven project)
│   ├── src/main/java/com/devcareeros/
│   │   ├── config/           Security & CORS configuration
│   │   ├── controller/       REST controllers (@RestController)
│   │   ├── dto/               Data Transfer Objects (request/response shapes)
│   │   ├── entity/            JPA entities (@Entity classes -> MySQL tables)
│   │   ├── exception/         Custom exceptions + global error handler
│   │   ├── repository/        Spring Data JPA repositories
│   │   ├── security/          JWT filter, JWT util, UserDetails implementation
│   │   ├── service/           Business logic
│   │   └── DevcareerOsApplication.java   <- main() entry point
│   ├── src/main/resources/application.properties
│   └── pom.xml
│
├── frontend/                  React application (Vite)
│   └── src/
│       ├── api/                axios instance + AuthContext
│       ├── components/         Sidebar/layout, progress bars, modal, etc.
│       ├── pages/               Login, Register, Dashboard, Skills, DSA, ...
│       └── styles/global.css    All the app's visual styling
│
├── database/
│   └── setup.sql               Creates the empty MySQL database
│
└── README.md                   You are here
```

---

## 3. Database Setup (MySQL)

1. Open MySQL Workbench (or your CLI / DBeaver) and connect to your local MySQL server.
2. Run the script at `database/setup.sql`:

   ```sql
   CREATE DATABASE IF NOT EXISTS devcareer_os
       CHARACTER SET utf8mb4
       COLLATE utf8mb4_unicode_ci;
   ```

   That's it — **you don't need to create any tables yourself.** Hibernate (via
   `spring.jpa.hibernate.ddl-auto=update`) will automatically create every table
   the first time you start the backend, based on the `@Entity` classes.

3. Make sure the database credentials in
   `backend/src/main/resources/application.properties` match your local MySQL
   setup:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/devcareer_os?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
   spring.datasource.username=root
   spring.datasource.password=root
   ```

   Change `username`/`password` to your own MySQL root password (or the
   dedicated user you create in `setup.sql`).

---

## 4. Running the Backend

From inside the `backend/` folder:

```bash
cd backend
mvn spring-boot:run
```

Or, from your IDE: right-click `DevcareerOsApplication.java` → **Run As → Java
Application** (or **Spring Boot App**).

If everything is configured correctly, you'll see:

```
DevCareer OS backend is running!
API base URL: http://localhost:8080/api
```

Hibernate will print a series of `create table ...` statements the very first
time it runs — that's it building your schema automatically. On every run
after that, it will just verify/update the schema.

**Building a runnable JAR** (optional, still 100% local — no Docker):

```bash
mvn clean package
java -jar target/devcareer-os.jar
```

---

## 5. Running the Frontend

From inside the `frontend/` folder:

```bash
cd frontend
npm install
npm run dev
```

Vite will start the React dev server, normally at:

```
http://localhost:5173
```

Open that URL in your browser. Register a new account, log in, and start
using the app. The frontend is pre-configured to call the backend at
`http://localhost:8080/api` (see `src/api/axios.js`), and the backend's CORS
config already allows requests from `http://localhost:5173`.

---

## 6. Testing the API with Postman

1. **Register a user**

   `POST http://localhost:8080/api/auth/register`

   Body (raw JSON):
   ```json
   {
     "fullName": "Jane Doe",
     "email": "jane@example.com",
     "password": "password123"
   }
   ```

   Response includes a `token` field — copy it.

2. **Login**

   `POST http://localhost:8080/api/auth/login`
   ```json
   { "email": "jane@example.com", "password": "password123" }
   ```

3. **Call a protected endpoint**

   For every other endpoint, add this header in Postman:

   ```
   Authorization: Bearer <paste the token here>
   ```

   Example — add a skill:

   `POST http://localhost:8080/api/skills`
   ```json
   { "name": "Java", "category": "Backend", "level": "Advanced", "progressPercentage": 80 }
   ```

   `GET http://localhost:8080/api/skills` (with the same header) will now
   return that skill.

Full list of endpoints:

```
POST   /api/auth/register
POST   /api/auth/login

GET    /api/profile
PUT    /api/profile

GET    /api/dashboard/summary

GET    /api/skills            POST /api/skills
PUT    /api/skills/{id}       DELETE /api/skills/{id}

GET    /api/dsa               POST /api/dsa
PUT    /api/dsa/{id}          DELETE /api/dsa/{id}

GET    /api/learning          POST /api/learning
PUT    /api/learning/{id}     DELETE /api/learning/{id}

GET    /api/projects          POST /api/projects
PUT    /api/projects/{id}     DELETE /api/projects/{id}

GET    /api/certifications        POST /api/certifications
PUT    /api/certifications/{id}   DELETE /api/certifications/{id}

GET    /api/applications      POST /api/applications
PUT    /api/applications/{id} DELETE /api/applications/{id}

GET    /api/interviews        POST /api/interviews
PUT    /api/interviews/{id}   DELETE /api/interviews/{id}
```

---

## 7. Verifying Data in MySQL

After adding a skill (or any other item) through Postman or the React app,
open MySQL Workbench and run:

```sql
USE devcareer_os;
SELECT * FROM users;
SELECT * FROM skills;
SELECT * FROM dsa_problems;
SELECT * FROM learning_topics;
SELECT * FROM projects;
SELECT * FROM certifications;
SELECT * FROM job_applications;
SELECT * FROM interviews;
```

You should see your data there — this is the best way to build a mental model
of how the React form → REST API → Service → Repository → Hibernate → MySQL
chain actually works.

---

## 8. Default Configuration Reference

| Setting | Value |
|---|---|
| Backend URL | `http://localhost:8080` |
| Frontend URL (dev) | `http://localhost:5173` |
| MySQL database name | `devcareer_os` |
| Default DB username | `root` (change in `application.properties`) |
| JWT token lifetime | 24 hours |
| Table creation strategy | `spring.jpa.hibernate.ddl-auto=update` (auto) |

---

## 9. Core Concepts Used in This Project (quick glossary)

- **Object** — a runtime instance of a class, holding actual data (e.g. one specific `User`).
- **Class** — the blueprint/template that defines what fields and behavior objects of that type will have.
- **Constructor** — a special method used to create and initialize a new object.
- **Interface** — a contract listing method signatures with no implementation; a class "implements" it and provides the actual code (e.g. `UserRepository extends JpaRepository`).
- **Dependency Injection (DI)** — instead of a class creating its own dependencies with `new`, Spring "injects" them automatically (via `@Autowired`). This makes code loosely coupled and easy to test.
- **REST** — an architectural style for web APIs based on resources (URLs) and standard HTTP verbs (GET/POST/PUT/DELETE).
- **HTTP** — the protocol browsers/clients use to talk to servers (requests and responses).
- **JDBC** — the low-level Java API for talking directly to a relational database with SQL.
- **JPA** — a specification (a set of interfaces) describing how Java objects map to database tables.
- **Hibernate** — the actual implementation of JPA that Spring Boot uses under the hood.
- **Entity** — a Java class annotated with `@Entity`, representing one database table.
- **Repository** — an interface (usually extending `JpaRepository`) that gives you database CRUD operations without writing SQL.
- **Service** — a class that holds business logic, sitting between controllers and repositories.
- **Controller** — a class annotated with `@RestController` that exposes REST endpoints (URLs) to the outside world.
- **DTO (Data Transfer Object)** — a plain class used to shape the JSON that goes in/out of the API, separate from the database entity.
- **JWT (JSON Web Token)** — a signed token issued at login, sent on every future request to prove who you are, without the server needing to store a session.
- **Spring Security** — the framework that intercepts every request, checks the JWT, and decides whether it's allowed through.

Key annotations you'll see throughout the backend:

| Annotation | What it does |
|---|---|
| `@Entity` | Marks a class as mapped to a database table |
| `@Id` | Marks the primary key field |
| `@GeneratedValue` | Tells the DB to auto-increment the id |
| `@Service` | Marks a class as a Spring-managed service bean |
| `@Repository` | Marks a class/interface as a Spring-managed repository bean |
| `@RestController` | Marks a class as a REST API controller |
| `@GetMapping` / `@PostMapping` / `@PutMapping` / `@DeleteMapping` | Map an HTTP verb + path to a method |
| `@Autowired` | Injects a required dependency (constructor/field injection) |

---

## 10. What's intentionally NOT included

As requested, this project contains **no** Docker, Dockerfile, docker-compose,
AWS/VPC/EC2/RDS, Terraform, CI/CD (GitHub Actions), Kubernetes, or deployment
scripts of any kind. It's a plain local full-stack app you run with `mvn
spring-boot:run` and `npm run dev`. Containerizing and deploying it is left
for you to do as your next learning step.

---

## 11. Suggested Next Steps

1. Run the backend, confirm the tables appear in MySQL.
2. Run the frontend, register an account, and poke around every page.
3. Read through `SecurityConfig.java` and `JwtAuthFilter.java` — this is the
   heart of the authentication flow and a very common interview topic.
4. Once comfortable, containerize the backend and frontend with Docker on
   your own, and eventually deploy them (e.g. to AWS) as your next milestone.

Good luck with your placement prep — you've now got a real project to talk
about in interviews, and the practical example to actually reason about JPA,
Spring Security and REST design when asked.
