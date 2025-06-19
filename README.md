# Дипломный проект «Доска объявлений»

Backend-приложение на Spring Boot, реализующее REST-API для сервиса размещения объявлений (аналог Avito). Проект соответствует [OpenAPI-спецификации](https://github.com/dmitry-bizin/front-react-avito/blob/v1.19/openapi.yaml) и предназначен для совместной работы с React-frontend.

## Функциональные возможности
* Регистрация и авторизация пользователей  
* Публикация, редактирование и удаление объявлений  
* Загрузка изображений объявлений и аватаров пользователей  
* Комментирование объявлений  
* Ролевое разграничение доступа (`USER`, `ADMIN`)  
* Документация Swagger UI (`/swagger-ui.html`)

## Технологический стек
* Java 11, Maven
* Spring Boot 2.7: Web, Security, Data JPA
* PostgreSQL 14 + Liquibase
* Lombok, MapStruct
* springdoc-openapi-ui 1.7

## Требования к окружению
* JDK 11+
* Maven 3.8+
* PostgreSQL (локально или в Docker)

### Быстрый старт (Unix/Mac)
```bash
git clone https://github.com/LoinRe/Graduate-work.git
cd Graduate-work
# настроить application.properties при необходимости
./mvnw spring-boot:run
```
Приложение будет доступно по адресу http://localhost:8080

### Сборка jar
```bash
./mvnw clean package
java -jar target/graduate-work-0.0.1-SNAPSHOT.jar
```

## Конфигурация
Все параметры задаются в `src/main/resources/application.properties`.

| Параметр | Назначение | Значение по умолчанию |
|----------|-----------|-----------------------|
| `spring.datasource.url` | URL БД PostgreSQL | `jdbc:postgresql://localhost:5432/Graduate-db` |
| `spring.datasource.username` | Пользователь | `GraduateUser` |
| `spring.datasource.password` | Пароль | `123` |
| `spring.liquibase.enabled` | Включить Liquibase | `true` |

## Liquibase
Схема БД управляется Liquibase. Стартовый changelog — `src/main/resources/liquibase/changelog-master.yml`,
который подключает файл `scripts/01-create-schema.sql`. При первом запуске создаются таблицы `users`, `ads`, `comments`, а Liquibase фиксирует изменения в системной таблице `databasechangelog`.

### Добавление новой миграции
1. Cоздайте SQL-файл в `src/main/resources/liquibase/scripts`, например `02-add-index.sql`.
2. Добавьте changeSet в `changelog-master.yml` с уникальными `id` и `author`.
3. При следующем запуске приложения Liquibase применит миграцию автоматически.

## Документация API
Swagger UI: `http://localhost:8080/swagger-ui.html`  
JSON-описание: `http://localhost:8080/v3/api-docs`

## Запуск фронтенда (Docker)
Для тестирования бэкенд вместе с пользовательским интерфейсом, запустите готовый Docker-образ React-приложения:

```bash
docker run -p 3000:3000 --rm ghcr.io/dmitry-bizin/front-react-avito:v1.21
```

Фронтенд будет доступен по адресу http://localhost:3000 и уже настроен на работу с бэкендом на порту 8080.

## Тестирование
Запуск всех тестов:
```bash
./mvnw test
```
Покрыты критические сценарии: регистрация, логин, CRUD объявлений и комментариев.

## Автор
LoinRe © 2025
 
