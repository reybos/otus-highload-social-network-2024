# otus-highload-social-network-2024

## Технологии

<ul>
    <li>Java 17</li>
    <li>Spring Boot</li>
    <li>Spring Data JDBC</li>
    <li>PostgreSQL</li>
    <li>Maven</li>
    <li>docker</li>
</ul>

## Инструкция

1. Установка и запуск (Все команды выполняются из директории проекта)
    1. Перейти в директорию проекта
    2. Выполнить команду `docker compose up -d --build`
    3. Проверка API:
        1. Импортировать Postman коллекцию `./postman/social_network.postman_collection.json`
        2. Выполнить запрос с названием `registerUser`. Результат:
       ```json 
       {
         "user_id": "l0s2ELyatOg0EDKYq0d0YdDTGBBuIX"
       }
       ```
       
        3. Выполнить запрос с названием `login`, подставив user_id с прошлого шага. Результат:
       ```json
       {
         "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwOTYxNWY2YS00OGU1LTRiOWUtODFhMS03ZDU3M2VhNGM..."
       }
       ```
       
        5. Выполнить запрос с названием `getUser`. В заголовок Authorization добавить токен из прошлого запроса, а в URL, user_id из первого запроса. Результат:
       ```json
       {
          "user_id": "l0s2ELyatOg0EDKYq0d0YdDTGBBuIX",
          "first_name": "Andrew",
          "second_name": "Bosyi",
          "birthdate": "2024-06-07",
          "biography": "books",
          "city": "Belgrade"
        }
       ```