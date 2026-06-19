# text-adventure-webapp

[![CI](https://github.com/Aberezhnoy1980/text-adventure-webapp/actions/workflows/ci.yml/badge.svg)](https://github.com/Aberezhnoy1980/text-adventure-webapp/actions/workflows/ci.yml)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Tomcat](https://img.shields.io/badge/Tomcat-9.0-F8DC75?logo=apachetomcat&logoColor=black)](https://tomcat.apache.org/)
[![Servlets](https://img.shields.io/badge/Servlets-4.0-6B9BD1)](https://javaee.github.io/servlet-spec/)
[![JSP](https://img.shields.io/badge/JSP-2.3-6B9BD1)](https://projects.eclipse.org/projects/ee4j.jsp)
[![JSTL](https://img.shields.io/badge/JSTL-1.2-6B9BD1)](https://jakarta.ee/specifications/tags-jsp-standard-tag-library/)
[![JUnit](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)](https://docs.docker.com/compose/)

Текстовый веб-квест на Java: ветвящийся сюжет про космическую встречу с НЛО, где каждый следующий вопрос зависит от предыдущего ответа. Учебный pet-проект в стиле production-ready репозитория: Servlet MVC, `HttpSession`, unit-тесты, Docker и CI.

## Стек

| Слой | Технологии |
|------|------------|
| Backend | Java 17, Servlets (`@WebServlet`), JSP, JSTL |
| Runtime | Apache Tomcat 9 |
| Build | Maven (WAR) |
| Tests | JUnit 5 |
| DevOps | Docker, Docker Compose, GitHub Actions, Cargo Maven Plugin |

> **Примечание по зависимостям:** target runtime — Tomcat 9, поэтому используется namespace `javax.servlet` (не `jakarta`). JSTL подключается в WAR; Servlet/JSP API — `provided` (их даёт контейнер).

## Сюжет и gameplay

Игрок просыпается у космопорта без памяти, знакомится с экипажем и вступает в контакт с чужим кораблём. Дальше — цепочка решений: принять или отклонить вызов, подняться на мостик, рассказать правду или солгать. Есть дополнительные ветки для исследования сценария.

```text
START ──► ACCEPTED_CHALLENGE ──► ON_BRIDGE ──► VICTORY / DEFEAT
  │              │
  INQUIRY    ESCORT_APPROACH
```

После победы или поражения можно начать игру заново — имя игрока сохраняется в сессии, счётчик сыгранных игр увеличивается.

## Структура проекта

```text
src/main/java/ru/javarush/textadventure/
├── model/          # GameState, GameChoice, GameSession
├── service/        # GameEngine — логика переходов
└── web/            # Servlets, CharacterEncodingFilter, SessionSupport

src/main/webapp/
├── WEB-INF/web.xml # welcome-file, session, error pages
├── welcome.jsp     # приветствие и ввод имени
├── game.jsp        # игровой экран и статистика
├── css/app.css
└── error.jsp

src/test/java/...   # GameEngineTest
```

Servlets регистрируются через `@WebServlet`; `web.xml` содержит deployment-настройки (welcome-file, session timeout, error pages).

## Быстрый старт (Docker)

Основной способ запуска:

```bash
docker compose up --build
```

Приложение: [http://localhost:8080](http://localhost:8080)

## Локальный запуск без Docker

### Cargo (встроенный Tomcat 9)

```bash
mvn cargo:run
```

Остановка: `Ctrl+C`. Порт по умолчанию: `8080` (настраивается в `pom.xml` → `cargo.port`).

### Ручной деплой WAR

```bash
mvn clean package
```

Скопировать `target/text-adventure-webapp.war` в `$CATALINA_HOME/webapps/` (например, как `ROOT.war`) и запустить Tomcat 9.

## Тесты

```bash
mvn test
```

Покрыта логика `GameEngine`: победные и проигрышные сценарии, дополнительные ветки, валидация выборов, сброс состояния.

## CI

GitHub Actions workflow [`.github/workflows/ci.yml`](.github/workflows/ci.yml) запускает `mvn verify` на push в `main`/`dev` и на pull request в `main`.

## HttpSession

В сессии хранятся:

- имя игрока;
- текущее состояние игры;
- результат (в процессе / победа / поражение);
- счётчик сыгранных игр;
- флаг escort-ветки (для текста на мостике).

Статистика отображается в footer игровой страницы.

## Ссылки

- Репозиторий: [github.com/Aberezhnoy1980/text-adventure-webapp](https://github.com/Aberezhnoy1980/text-adventure-webapp)
- CI: [Actions](https://github.com/Aberezhnoy1980/text-adventure-webapp/actions)
