package com.example.rest_app_person_calories.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.sql.Date;

@Data
@AllArgsConstructor
public class MealHistory {
    private Date mealDate;
    private String mealName;
    private String dishName;
    private Double dishCalories;
    private String dishNutrients;
}
