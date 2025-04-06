package com.example.rest_app_person_calories.controllers;
import com.example.rest_app_person_calories.dto.MealDTO;
import com.example.rest_app_person_calories.dto.MealHistory;
import com.example.rest_app_person_calories.dto.CaloriesResponse;
import com.example.rest_app_person_calories.model.Dishes;
import com.example.rest_app_person_calories.model.Meal;
import com.example.rest_app_person_calories.model.Users;
import com.example.rest_app_person_calories.services.DishesService;
import com.example.rest_app_person_calories.services.MealService;
import com.example.rest_app_person_calories.services.UsersService;
import com.example.rest_app_person_calories.util.ValidationUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/meal")
public class MealController {
    private final UsersService usersService;
    private final MealService mealService;
    private final ModelMapper modelMapper;
    private final DishesService dishesService;

    @Autowired
    public MealController(UsersService usersService, MealService mealService, ModelMapper modelMapper, DishesService dishesService) {
        this.usersService = usersService;
        this.mealService = mealService;
        this.modelMapper = modelMapper;
        this.dishesService = dishesService;
    }
    // История питания конкретного пользователя по дням
    @GetMapping("/history/{user_id}")
    public ResponseEntity<List<MealHistory>> getHistory(@PathVariable Integer user_id) {
        List<MealHistory> mealHistory = mealService.getMealHistory(user_id);
        return ResponseEntity.ok(mealHistory);
    }
    // Отображает сколько калорий потребил конкретный пользователь за сутки.
    // ( Если пользователь уложился в дневную норму получим true в обратном случае false )
    @GetMapping("/calories/today/{userId}")
    public ResponseEntity<CaloriesResponse> getTotalCaloriesForToday(@PathVariable Integer userId) {
        Float totalCalories = mealService.getTotalCaloriesForToday(userId);
        CaloriesResponse response = new CaloriesResponse();
        float caloriesNorm =  usersService.getNormCalories(userId);
        response.setMessage("Пользователь | User_id = " + userId + " | "
                            + "Кол-во потребленных калорий пользователем за сутки: " + totalCalories + " "
                            + " Дневная норма пользователя: " + "| " + caloriesNorm + " | "
                            );
        boolean isNormExceeded = mealService.isNormExceeded(totalCalories, caloriesNorm);
        response.setTotalCalories(isNormExceeded);
        return new ResponseEntity<>(response, HttpStatus.OK); // Возвращаем значение и статус 200 OK
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveMealForUser(@RequestBody @Valid MealDTO mealDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ValidationUtils.handleValidationErrors(bindingResult);
        } else {
            Optional<Users> userOptional = usersService.findById(mealDTO.getUser_id());
            if(userOptional.isPresent()) {
                Users users = userOptional.get();
                Meal meal = convertToMeal(mealDTO);
                meal.setUser(users);
                List<Integer> id = mealDTO.getDishIds();
                List<Dishes> dishes = dishesService.findAllById(id);
                meal.setDishes(dishes);
                mealService.save(meal);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    private Meal convertToMeal(MealDTO mealDTO){
        return modelMapper.map(mealDTO,Meal.class);
    }
}
