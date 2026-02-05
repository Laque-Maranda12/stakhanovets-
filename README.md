# Стахановец — Система аренды строительного инструмента

Система для сдачи строительного инструмента в аренду с возможностью выкупа, а также оформления заявок на мелкие ремонтные работы и расчёта стоимости.

## Архитектура

Проект состоит из трёх компонентов:

| Компонент | Технологии | Расположение |
|-----------|-----------|--------------|
| **Backend (REST API)** | Java 17, Spring Boot 3.4.2, PostgreSQL, Spring Security + JWT | `stakhanovets-backend/stakhanovets-backend/` |
| **Админ-панель** | Java 17, JavaFX 21, OkHttp, Jackson | `stakhanovets-admin/stakhanovets-admin/` |
| **Клиентский сайт** | HTML5, CSS3, JavaScript, Bootstrap 5 | `stakhanovets-site/` |

## Требования

- **Java** 17+
- **Maven** 3.8+
- **PostgreSQL** 14+
- **Браузер** (Chrome, Firefox, Edge — последние версии)

## Настройка базы данных

### Вариант 1: Автоматическая инициализация (рекомендуется)

Просто создайте пустую базу данных, бэкенд при запуске автоматически создаст все таблицы (Hibernate `ddl-auto: update`) и заполнит начальные данные (роли, администратор, категории, инструменты):

```bash
psql -U postgres -c "CREATE DATABASE \"Strahovets\";"
```

### Вариант 2: Импорт дампа

```bash
psql -U postgres -d Strahovets -f strahovets_db.sql
```

### Параметры подключения (по умолчанию)

| Параметр | Значение |
|----------|----------|
| Host | localhost |
| Port | 5432 |
| Database | Strahovets |
| Username | postgres |
| Password | postgres |

Параметры можно изменить в файле `stakhanovets-backend/stakhanovets-backend/src/main/resources/application.yml`.

## Запуск

### 1. Backend (REST API сервер)

```bash
cd stakhanovets-backend/stakhanovets-backend
mvn spring-boot:run
```

Сервер запустится на **http://localhost:8080**.

При первом запуске автоматически будут созданы:
- Роли: ADMIN, MANAGER, MASTER, CLIENT
- Администратор: `admin@stakhanovets.ru` / `admin123`
- 6 категорий инструментов
- ~16 инструментов с реальными названиями и ценами

### 2. Клиентский сайт

Откройте `stakhanovets-site/index.html` в браузере. Либо запустите через Live Server (расширение VS Code) или любой HTTP-сервер:

```bash
cd stakhanovets-site
python3 -m http.server 3000
```

Сайт будет доступен на **http://localhost:3000**.

### 3. Админ-панель (JavaFX)

```bash
cd stakhanovets-admin/stakhanovets-admin
mvn javafx:run
```

Или запустите класс `ru.stakhanovets.admin.MainApp` из IDE (IntelliJ IDEA, Eclipse).

> Если бэкенд недоступен, админ-панель работает в **DEMO-режиме** с демонстрационными данными.

## Учётные данные по умолчанию

| Роль | Email | Пароль |
|------|-------|--------|
| Администратор | admin@stakhanovets.ru | admin123 |

## API Эндпоинты

### Аутентификация (`/api/auth`)
| Метод | Путь | Описание |
|-------|------|----------|
| POST | `/api/auth/login` | Вход (email, password → token) |
| POST | `/api/auth/register` | Регистрация (fullName, email, password → token) |
| GET | `/api/auth/me` | Текущий пользователь |

### Инструменты (`/api/tools`)
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/tools` | Список всех инструментов |
| GET | `/api/tools?categoryId=1` | Инструменты по категории |
| PUT | `/api/tools/{id}/status` | Обновить статус |

### Категории (`/api/categories`)
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/categories` | Список категорий |

### Аренда (`/api/rentals`)
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/rentals` | Список всех аренд |
| POST | `/api/rentals` | Создать заявку на аренду |
| POST | `/api/rentals/calculate` | Калькулятор стоимости |
| PUT | `/api/rentals/{id}/approve` | Одобрить |
| PUT | `/api/rentals/{id}/issue` | Выдать |
| PUT | `/api/rentals/{id}/return` | Возврат |

### Ремонт (`/api/repairs`)
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/repairs` | Список заявок на ремонт |
| POST | `/api/repairs` | Создать заявку |
| PUT | `/api/repairs/{id}/status` | Обновить статус |
| PUT | `/api/repairs/{id}/cost` | Обновить стоимость |

### Выкуп (`/api/buyouts`)
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/buyouts` | Список заявок на выкуп |
| POST | `/api/buyouts` | Создать заявку |

### Консультации (`/api/consultations`)
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/consultations` | Список заявок |
| POST | `/api/consultations` | Отправить заявку |

### Пользователи (`/api/users`)
| Метод | Путь | Описание |
|-------|------|----------|
| GET | `/api/users` | Список пользователей |
| PUT | `/api/users/{id}/block` | Заблокировать |

## Структура проекта

```
stakhanovets/
├── README.md
├── strahovets_db.sql                    # Дамп БД (опционально)
├── stakhanovets-backend/
│   └── stakhanovets-backend/
│       ├── pom.xml
│       └── src/main/
│           ├── resources/application.yml
│           └── java/ru/stakhanovets/toolrentalsystem/
│               ├── StakhanovetsApplication.java
│               ├── config/DataInitializer.java
│               ├── controller/                  # REST контроллеры
│               ├── dto/                         # Data Transfer Objects
│               ├── error/                       # Обработка ошибок
│               ├── model/                       # JPA сущности
│               ├── repository/                  # Spring Data репозитории
│               ├── security/                    # JWT + Spring Security
│               └── service/                     # Бизнес-логика
├── stakhanovets-admin/
│   └── stakhanovets-admin/
│       ├── pom.xml
│       └── src/main/java/ru/stakhanovets/admin/
│           ├── MainApp.java
│           ├── api/                             # API клиент
│           ├── config/                          # Конфигурация
│           ├── controller/                      # JavaFX контроллеры
│           ├── model/                           # Модели данных
│           └── util/                            # Утилиты
└── stakhanovets-site/
    ├── index.html                               # Главная страница
    ├── catalog.html                             # Каталог инструментов
    ├── cart.html                                # Корзина
    ├── repair.html                              # Заявка на ремонт
    ├── buyout.html                              # Заявка на выкуп
    ├── consultation.html                        # Заявка на консультацию
    ├── login.html                               # Вход / Регистрация
    ├── css/style.css                            # Стили
    └── js/app.js                                # Общий JS
```
