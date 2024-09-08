# Тестовое заданике - микроскервис FileService
___
 

## Описание решения
___

Микросервис выполняет роль хранилища различных файлов и их атрибутов.
Он предоставляет HTTP API для создания и получения файлов вместе с их атрибутами.
Для хранения данных используется база данных PostgreSQL.

Технологический стек:
+ Backend: Java, Spring Boot, Spring Data JPA
+ База данных: PostgreSQL
+ Тестирование: JUnit
+ Контейнеризация: Docker
+ Формат данных: JSON

## Реализованные API-методы
___
1. **Создание файла**:
    - **Входной JSON**:
      ```json
      {
        "title": "example",
        "creation_date": "2024-01-01T12:00:00",
        "description": "Example file",
        "filedata": "SGVsbG8gd29ybGQh"  
      }
      ```
    - **Выходной JSON**:
      ```json
      {
        "id": 1
      }
      ```

2. **Получение файла по id**:
    - **Входной параметр**: `id` файла
    - **Выходной JSON**:
      ```json
      {
        "title": "example.txt",
        "creation_date": "2023-01-01T12:00:00",
        "description": "Example file",
        "filedata": "SGVsbG8gd29ybGQh"   
      }
      ```

3. **Получение списка файлов отсортированных по дате и времени создания** :
    - **Входные параметры**: отсутствуют
    - **Выходной JSON**:
    - ```json
      [
        {
          "fileData": "SGVsbG8gd29ybGQh",
          "title": "example2",
          "creationDate": "2024-01-01T12:00:00",
          "description": "Second Example file"
        },
        {  
          "fileData": "SGVsbG8gd29ybGQh",
          "title": "example3",
          "creationDate": "2024-02-01T12:00:00",
          "description": "Third Example file"
        },
        {
          "fileData": "SGVsbG8gd29ybGQh",
          "title": "example1",
          "creationDate": "2024-02-01T13:00:00",
          "description": "First Example file"
        }
      ]
    ```
### Дополнительные возможности

- Тестирование с использованием JUnit.
- Docker-контейнеризация микросервиса вместе с базой данных PostgreSQL.

## Инструкция по запуску приложения
___

### Требования

- Docker
- Docker Compose

### Шаги для запуска

1. **Клонируйте репозиторий:**

   ```shell
   git clone https://github.com/TBagirov/GreenatomTestTask.git
   cd GreenatomTestTask

2. **Соберите и запустите Docker-контейнеры:**
   
   ```shell
   docker-compose up --build
   ```
   эта команда создаст и запустит контейнеры для микросервиса и базы данных PostgreSQL.

3. **Доступ к api**
   После запуска контейнеров, микросервис будет доступен по адресу `http://localhost:8080`

## Примеры тестовых запросов для проверки API-методов
___
+ ### Создание файла
   Адрес: `http://localhost:8080/api/file_store` <br>
   HTTP-метод: `POST` <br>
   Несколько примеров тела запроса(для дальнейшей проверки получения всех файлов с сортировкой):  
   ```json
  {
  "title": "example1",
  "creationDate": "2024-02-01T13:00:00",
  "description": "First Example file",
  "fileData": "Rmlyc3QgSGVsbG8gV29ybGQ="  
  } 
  ```
   
  ```json  
  {
  "title": "example2",
  "creationDate": "2024-01-01T12:00:00",
  "description": "Second Example file",
  "fileData": "U2Vjb25kIEhlbGxvIFdvcmxk"  
  }
  ```
  ```json  
  {
  "title": "example3",
  "creationDate": "2024-02-01T12:00:00",
  "description": "Third Example file",
  "fileData": "VGhpcmQgSGVsbG8gV29ybGQ="  
  }
  ```
  *При отсутствии нужного поля в теле запроса предусмотрен json c exception* <br>
+ ### Получение файла по id
  Адрес: `http://localhost:8080/api/file_store/{id}` <br>
  HTTP-метод: `GET` <br>
  *При указании несуществующего id предусмотрен json c exception* <br>
+ ### Получение списка файлов отсортированных по дате и времени создания
  Адрес: `http://localhost:8080/api/file_store` <br>
  HTTP-метод: `GET` <br> 