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
ЭНДПОИНТЫ
------------------------------------------------------------------------------
* POST /api/add: Добавление нового пользователя в таблицу.
  Пример запроса: JSON
  {   
    "name": "Снежанна",
    "email": "Снежок@mail.com",
    "age": "20",
    "weight": "65",
    "height": "165",
    "purpose": "Поддержание"
}
* POST /api/dishes/add: Добавление нового блюда в таблицу.
  Пример запроса: JSON
  {
    "name": "Творог",
    "caloriesPerPortion": 240,
    "nutrients": "Белки: 30, Жиры: 50, Углеводы: 10"
}
* POST /api/meal/add: Добавление нового приема пищи в таблицу.
  Пример запроса: JSON
  {
  "mealName": "обед",
  "user_id": 1,
  "dishIds": [5, 3]
}
  
* GET /api/meal/calories/today/{user_id}: Получение количества потребляемых калорий у конкретного пользователя за сегодня. Замените {user_id} на ID пользователя.
  Пример ответа: JSON
  {
    "message": "Пользователь | User_id = 1 | Кол-во потребленных калорий пользователем за сутки: 480.0  Дневная норма пользователя: | 3064.284 | ",
    "totalCalories": true
}
* GET /api/meal/history/{user_id}: Получение истории питания конкретного пользователя по дням. Замените {user_id} на ID пользователя.
  Пример ответа: JSON
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
------------------------------------------------------------------------------
Примеры запросов при помощи POSTMAN:
https://winter-robot-780381.postman.co/workspace/My-Workspace~a0d022f3-59f2-4cb0-8134-6c7af68f7448/collection/18993989-edf75407-4aeb-4727-8020-945037383eca?action=share&creator=18993989
------------------------------------------------------------------------------



