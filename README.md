# Fruit Salad REST API
Fruit Salad REST API - это веб-приложение для управления фруктовыми салатами.

---

## Основные возможности

### Получение списка салатов
- **Метод:** `GET`
- **URL:** `/api/v1/salads`

### Вычисление питательной ценности салата на 100 грамм
- **Метод:** `GET`
- **URL:** `/api/v1/salads/{saladId}/nutrition`

### Поиск дубликатов салатов
- **Метод:** `GET`
- **URL:** `/api/v1/salads/duplicates`

### Добавление нового салата
- **Метод:** `POST`
- **URL:** `/api/v1/salads`
- **Пример запроса:**
  ```json
  {
  "name": "Тропический микс",
  "description": "Освежающий салат с экзотическими фруктами для жаркого летнего дня.",
  "saladRecipe": {
    "Mango": 150,
    "Pineapple": 100,
    "Banana": 120,
    "Kiwi": 80
  }
  }

  ```
### Редактирование существующего салата
- **Метод:** `PATCH`
- **URL:** `/api/v1/salads/{saladId}`
- **Пример запроса:**
  ```json
  {
  "name": "Тропический микс",
  "description": "Освежающий салат с экзотическими фруктами для жаркого летнего дня.",
  "saladRecipe": {
    "Mango": 150,
    "Pineapple": 100,
    "Banana": 120,
    "Kiwi": 80,
    "Papaya": 50
  }
  }
  ```
  
### Удаление салата
- **Метод:** `DELETE`
- **URL:** `/api/v1/salads/{saladId}`

---

## Технологии и зависимости

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **JUnit 5, Mockito**
- **SpringDoc OpenAPI**

---

## Установка и запуск
Скачиваем репозиторий:
```bash
git clone git@github.com:rodemark/fruit-salad-rest-api.git
```

Запускаем docker-compose:
```bash
docker-compose up -d
```
Сервис будет доступен по адресу: `http://localhost:8080`

Swagger будет доступен по адресу: `http://localhost:8080/swagger-ui.html`