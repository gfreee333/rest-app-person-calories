# RestAppPersonCalories
REST API для отслеживания калорий и истории питания пользователей.
------------------------------------------------------------------------------
## Предварительные требования
*   Java Development Kit (JDK) 17 или выше: [Скачать JDK 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
*   PostgreSQL (версия 12 или выше): [Скачать PostgreSQL](https://www.postgresql.org/download/)
*   Важно: Установка Apache Maven не требуется, так как в проекте используется [Maven Wrapper](https://maven.apache.org/wrapper/).
    Maven Wrapper обеспечивает консистентную сборку проекта, используя предопределенную версию Maven.
------------------------------------------------------------------------------
Структура базы данных
------------------------------------------------------------------------------
CREATE TABLE Users(
    id int generated always as identity primary key,
    name varchar(20) not null,
    email varchar(100) not null unique,
    age int not null,
    weight float not null,
    height int not null,
    purpose varchar not null,
    norm_calories float not null
);

create table Dishes(
    id int generated always as identity primary key,
    name varchar(100) not null,
    calories_per_portion float not null,
    nutrients varchar(150) not null
);

create table Meal(
    id int generated always as identity primary key,
    meal_name varchar(50) not null,
    date date not null,
    user_id int references Users(id)
);

create table Meal_Dishes(
    id_meal int references Meal(id),
    id_dish int references Dishes(id)
);

------------------------------------------------------------------------------
## Запуск приложения
------------------------------------------------------------------------------
1.  **Запуск из IDE (IntelliJ IDEA):**
    Найдите класс `RestAppPersonCaloriesApplication` в папке `src/main/java`.
    Щелкните правой кнопкой мыши на классе и выберите "Run 'RestAppPersonCaloriesApplication'".

2.  **Запуск из командной строки (JAR-файл):**
    ```bash
    java -jar target/rest-app-person-calories-0.0.1-SNAPSHOT.jar
------------------------------------------------------------------------------
## Эндпоинты

### 1. Добавление нового пользователя

*   `POST /api/add`

    **Пример запроса (curl):**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{
        "name": "Снежанна",
        "email": "Снежок@mail.com",
        "age": 20,
        "weight": 65,
        "height": 165,
        "purpose": "Поддержание"
    }' http://localhost:8080/api/add
    ```

    **Пример ответа (успех):**

    ```json
    {
        "id": 1,
        "name": "Снежанна",
        "email": "Снежок@mail.com",
        "age": 20,
        "weight": 65,
        "height": 165,
        "purpose": "Поддержание"
    }
    ```

### 2. Добавление нового блюда

*   `POST /api/dishes/add`

    **Пример запроса (curl):**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{
        "name": "Творог",
        "caloriesPerPortion": 240,
        "nutrients": "Белки: 30, Жиры: 50, Углеводы: 10"
    }' http://localhost:8080/api/dishes/add
    ```

    **Пример ответа (успех):**

    ```json
    {
        "id": 1,
        "name": "Творог",
        "caloriesPerPortion": 240,
        "nutrients": "Белки: 30, Жиры: 50, Углеводы: 10"
    }
    ```

### 3. Добавление нового приема пищи

*   `POST /api/meal/add`

    **Пример запроса (curl):**

    ```bash
    curl -X POST -H "Content-Type: application/json" -d '{
        "mealName": "обед",
        "user_id": 1,
        "dishIds": [5, 3]
    }' http://localhost:8080/api/meal/add
    ```

    **Пример ответа (успех):**

    ```json
    {
        "id": 1,
        "mealName": "обед",
        "userId": 1,
        "dishIds": [5, 3],
        "date": "2024-01-01"
    }
    ```

### 4. Получение количества потребляемых калорий за сегодня

*   `GET /api/meal/calories/today/{user_id}`
    *   Замените `{user_id}` на ID пользователя.

    **Пример запроса (curl):**

    ```bash
    curl http://localhost:8080/api/meal/calories/today/1
    ```

    **Пример ответа:**

    ```json
    {
        "message": "Пользователь | User_id = 1 | Кол-во потребленных калорий пользователем за сутки: 480.0  Дневная норма пользователя: | 3064.284 | ",
        "totalCalories": true
    }
    ```

### 5. Получение истории питания конкретного пользователя по дням

*   `GET /api/meal/history/{user_id}`
    *   Замените `{user_id}` на ID пользователя.

    **Пример запроса (curl):**

    ```bash
    curl http://localhost:8080/api/meal/history/1
    ```

    **Пример ответа:**

    ```json
    [
        {
            "mealDate": "2025-04-06",
            "mealName": "Полноценный обед",
            "dishName": "Свинина",
            "dishCalories": 240.0,
            "dishNutrients": "Белки: 30, Жиры: 50, Углеводы: 10"
        },
        {
            "mealDate": "2025-04-06",
            "mealName": "Полноценный обед",
            "dishName": "Сало",
            "dishCalories": 120.0,
            "dishNutrients": "Белки: 10, Жиры: 50, Углеводы: 10"
        }
    ]
    ```
------------------------------------------------------------------------------
Для удобного тестирования API доступна коллекция Postman.

1.  **Импортируйте коллекцию:**  Перейдите по [ссылке на коллекцию Postman](https://winter-robot-780381.postman.co/workspace/My-Workspace~a0d022f3-59f2-4cb0-8134-6c7af68f7448/collection/18993989-edf75407-4aeb-4727-8020-945037383eca?action=share&creator=18993989).
2.  **Импортируйте коллекцию в свой Postman:** Нажмите на кнопку "Run in Postman" (или похожую) в интерфейсе Postman или скопируйте содержимое коллекции в Postman.
3.  **Настройте переменные окружения (если необходимо):** Убедитесь, что в Postman настроены правильные переменные окружения (например, URL вашего сервера).
4.  **Тестируйте эндпоинты:** Отправляйте запросы из коллекции Postman и проверяйте ответы.



