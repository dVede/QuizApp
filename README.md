```http request
- POST /auth/login
    - Content-Type: application/json
    - JSON{"login": "String", "pwdHash": "String"}
    - Ответы:
        - HttpStatusCode.NotFound
            - Пользователь с таким никнеймом не найден.
        - HttpStatusCode.BadRequest
            - Никнейм или пароль пустые поля.
        - HttpStatusCode.OK
            - Успешная авторизация пользователя
            - JSON{"token": "String"}
```
