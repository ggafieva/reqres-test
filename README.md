# Reqres-Test

## Описание проекта
Проект представляет собой автоматизированные тесты для API сервиса Reqres, который предоставляет тестовые данные для пользователей и ресурсов. Тесты реализованы с использованием фреймворка TestNG, библиотеки RestAssured для работы с API и Allure для генерации отчетов.

### Проект включает в себя несколько наборов тестов, которые проверяют:

1. Получение списка пользователей и данных одного пользователя.
2. Получение списка ресурсов и данных одного ресурса.
3. Проверку успешной и неуспешной регистрации.
4. Проверку успешного и неуспешного входа в систему.
   
## Структура проекта

1. ReqresTest - Основной класс, содержащий тесты для всех API методов.
2. Specifications - Класс для настройки спецификаций запросов и ответов, таких как статус код и контент тип.
3. UserData, ResourceData, Register, Login и др. - Классы моделей для работы с данными, возвращаемыми API.

## Демонстрация

https://github.com/user-attachments/assets/1a18f5e7-3d19-4a7f-bdcb-411ff803ed0e
