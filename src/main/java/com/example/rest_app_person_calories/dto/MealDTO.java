package com.example.rest_app_person_calories.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class MealDTO {
    @NotEmpty(message = "!!!Поле meal_name не должно быть пустым!!!")
    private String mealName;
    @NotNull(message = "!!!Необходимо указать какой пользователь закреплен за приемом пищи!!!")
    private Integer user_id;
    @NotNull(message = "!!!Необходимо указать список блюд!!!")
    private List<Integer> dishIds;
}
