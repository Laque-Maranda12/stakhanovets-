# stakhanovets-admin (Java 17 + JavaFX)

## Запуск
1) Установите JDK 17
2) В корне проекта:
```bash
mvn clean javafx:run
```

## Сервер
По умолчанию приложение обращается к:
- `http://localhost:8080`

Если сервер недоступен — включится DEMO-режим (в ApiConfig.DEMO_FALLBACK=true) и будут показаны тестовые данные.

## Эндпоинты (ожидаемые)
- POST /api/auth/login -> { token }
- GET  /api/auth/me
- GET  /api/tools
- PUT  /api/tools/{id}/status
- GET  /api/rentals
- PUT  /api/rentals/{id}/approve|issue|return
- GET  /api/repairs
- PUT  /api/repairs/{id}/status
- PUT  /api/repairs/{id}/cost
- GET  /api/users
- PUT  /api/users/{id}/block
