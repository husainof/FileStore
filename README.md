# File Storage Microservice
___
## Описание

Этот микросервис реализует хранилище для различных файлов и их атрибутов. Он предоставляет HTTP API, позволяющее создавать и получать файлы в формате JSON. Микросервис разработан на Java с использованием Spring Boot, обернут в docker-контейнер и подключается к контейнеру PostgreSQL для хранения данных.
Основные функции

- Создание файла: принимает файл в формате base64 и его атрибуты (название, дата и время отправки, краткое описание) и возвращает уникальный идентификатор созданного файла.
- Получение файла: по уникальному идентификатору возвращает файл и его атрибуты.
- Получение списка всех файлов с поддержкой пагинации и сортировки по времени создания.

## Инструкция по запуску приложения
### Требования:

- Docker (для контейнеризации);
- Docker compose (для запуска бд в контейнере).

### Запуск с Docker

Склонируйте git-репозиторий:
```declarative
git clone https://github.com/husainof/FileStore.git
```
Опционально, если нужно подтянуть внесенные изменения в коде:
    - Соберить проект через IDE.
    - Запустите команду `./mvnw package`, в папке target скомпилируется jar-файл.
Запустите `docker-compose` в корне приложения:
```declarative
docker compose up
```
Микросервис будет доступен на порту 8080.

## Примеры тестовых запросов
### 1. Создание файла

Запрос:
```http

POST /api/files
Content-Type: application/json

{
    "file": "data:text/plain;base64,SGVsbG8sIFdvcmxkIQ==",
    "title": "Document Title",
    "creation_date": "2024-09-03T14:49:00",
    "description": "Description of the document"
}
```
Ответ:
```json
1
```

### 2. Получение файла

Запрос:
```http
GET /api/files/{id}
```

Ответ:
```json

{
    "data": "data:text/plain;base64,SGVsbG8sIFdvcmxkIQ==",
    "title": "Document Title",
    "creation_date": "2024-09-03T14:49:00",
    "description": "Description of the document"
}
```

### 3. Получение постраничного списка файлов

Запрос:
```http

GET /api/files?page=0&size=2
```

Ответ:
```json
[
    {
      "data": "data:text/plain;base64,SGVsbG8sIFdvcmxkIQ==",
      "title": "Document Title 1",
      "creation_date": "2024-09-01T14:49:00",
      "description": "Description 1"
    },
    {
      "data": "data:text/plain;base64,SGVsbG8sIFdvcmxkIQ==",
      "title": "Document Title 2",
      "creation_date": "2024-09-02T14:49:00",
      "description": "Description 2"
    }
]
```
