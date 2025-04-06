package com.example.rest_app_person_calories.dto;

import lombok.Data;

@Data
public class CaloriesResponse {
    private String message;
    private boolean totalCalories;
}
