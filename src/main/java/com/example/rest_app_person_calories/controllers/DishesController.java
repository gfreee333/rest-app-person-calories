package com.example.rest_app_person_calories.controllers;
import com.example.rest_app_person_calories.dto.DishesDTO;
import com.example.rest_app_person_calories.model.Dishes;
import com.example.rest_app_person_calories.services.DishesService;
import com.example.rest_app_person_calories.util.ValidationUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/dishes")
public class DishesController {
    private final DishesService dishesService;
    private final ModelMapper modelMapper;

    @Autowired
    public DishesController(DishesService dishesService, ModelMapper modelMapper) {
        this.dishesService = dishesService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveDishes(@RequestBody @Valid DishesDTO dishesDTO, BindingResult bindingResult){
      if(bindingResult.hasErrors()){
          return ValidationUtils.handleValidationErrors(bindingResult);
      }
      else {
          dishesService.save(convertToDishes(dishesDTO));
          return new ResponseEntity<>(HttpStatus.OK);
      }
    }

    private Dishes convertToDishes(DishesDTO dishesDTO){
        return modelMapper.map(dishesDTO,Dishes.class);
    }
}