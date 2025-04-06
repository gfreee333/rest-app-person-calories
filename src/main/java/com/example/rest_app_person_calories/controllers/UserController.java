package com.example.rest_app_person_calories.controllers;
import com.example.rest_app_person_calories.dto.UserDTO;
import com.example.rest_app_person_calories.model.Users;
import com.example.rest_app_person_calories.services.UsersService;
import com.example.rest_app_person_calories.util.EmailAlreadyExistsException;
import com.example.rest_app_person_calories.util.ErrorResponse;
import com.example.rest_app_person_calories.util.ValidationUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    //Реализую POST запрос для получения данных от пользователя в формате JSON
    @PostMapping("/add")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        return ValidationUtils.handleValidationErrors(bindingResult);
    } else {
        // Автоматический подсчет каллорий при создание пользователя
        float calories = usersService.numberOfColorsPerDay(
                userDTO.getWeight(),
                userDTO.getHeight(),
                userDTO.getAge(),
                userDTO.getPurpose()
        );
        Users users = convertToUser(userDTO);
        users.setNormCalories(calories);
        usersService.saveUser(users);
        // Отображение кол-во каллорий после POST запроса.
        Map<String, Float> response = new HashMap<>();
        response.put("calories", calories);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<String> handlerIllegalArgumentException(IllegalArgumentException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    private ResponseEntity<ErrorResponse> handlerEmailAlreadyException(EmailAlreadyExistsException e){
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "Email-conflict");
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    private Users convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, Users.class);
    }

}
