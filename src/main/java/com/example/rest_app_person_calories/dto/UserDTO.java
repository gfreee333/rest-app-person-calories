package com.example.rest_app_person_calories.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDTO {
    @NotEmpty(message = "!!!Укажите имя, поле не должно быть пустым!!!")
    @Size(min = 3, max = 100, message = "!!!Неправильное имя пользователя!!!")
    private String name;
    @Email(message = "!!!Некорректный email!!!")
    private String email;
    @NotNull(message = "!!!Укажите возраст, поле не должно быть пустым!!!")
    private Integer age;
    @NotNull(message = "!!!Укажите вес, поле не должно быть пустым!!!")
    @Min(value = 40, message = "!!!Некоректный вес!!!")
    @Max(value = 210, message = "!!!Некоректный вес!!!")
    private Float weight;
    @NotNull(message = "!!!Укажите рост, поле не должно быть пустым!!!")
    @Min(value = 120, message = "!!!Некоректный рост!!!")
    @Max(value = 210, message = "!!!Некоректный рост!!!")
    private Integer height;
    // Сюда загонять цель в качестве ENUM ( Сейчас разберусь как )
    @NotEmpty(message = "!!!Укажите цель, поле не должно быть пустым!!!")
    @Pattern(regexp = "^(Похудение|Поддержание|Набор массы)$", message = "Недопустимое значение цели. Допустимые значения: Похудение|Поддержание|Набор массы ")
    private String purpose;
}
