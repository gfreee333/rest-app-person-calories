package com.example.rest_app_person_calories.repositories;
import com.example.rest_app_person_calories.dto.MealHistory;
import com.example.rest_app_person_calories.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {
   // Запрос для получения суточного кол-ва колорий у пользователя.
    @Query(value = "SELECT SUM(D.calories_per_portion) FROM Users AS U " +
            "INNER JOIN Meal AS M ON U.id = M.user_id " +
            "INNER JOIN Meal_Dishes AS MD ON M.id = MD.id_meal " +
            "INNER JOIN Dishes AS D ON MD.id_dish = D.id " +
            "WHERE U.id = :userId AND CAST(M.date AS DATE) = CURRENT_DATE", nativeQuery = true)
    Float getTotalCaloriesForToday(@Param("userId") Integer userId);

    @Query(value = "SELECT M.date AS meal_date, M.meal_name AS meal_name, D.name AS dish_name, D.calories_per_portion AS dish_calories, D.nutrients AS dish_nutrients " +
            "FROM Users AS U " +
            "INNER JOIN Meal AS M ON U.id = M.user_id " +
            "INNER JOIN Meal_Dishes AS MD ON M.id = MD.id_meal " +
            "INNER JOIN Dishes AS D ON MD.id_dish = D.id " +
            "WHERE U.id = :userId " +
            "ORDER BY M.date", nativeQuery = true)
    List<MealHistory> getMealHistory(@Param("userId") Integer userId);

}
