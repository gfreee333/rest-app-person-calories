package com.example.rest_app_person_calories.services;
import com.example.rest_app_person_calories.dto.MealHistory;
import com.example.rest_app_person_calories.model.Meal;
import com.example.rest_app_person_calories.repositories.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class MealService {
    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }
    @Transactional
    public void save(Meal meal){
        meal.setDate(LocalDate.now());
        mealRepository.save(meal);
    }

    public List<MealHistory> getMealHistory(Integer userId) {
        return mealRepository.getMealHistory(userId);
    }

    public Float getTotalCaloriesForToday(Integer user_id){
        return mealRepository.getTotalCaloriesForToday(user_id);
    }

    public boolean isNormExceeded(Float totalCalories, float caloriesNorm) {
        return totalCalories == null || totalCalories <= caloriesNorm;
    }
}
