package com.example.rest_app_person_calories.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "!!!Укажите имя, поле не должно быть пустым!!!")
    @Size(min = 3, max = 100, message = "!!!Неправильное имя пользователя!!!")
    private String name;
    @Email(message = "!!!Некорректный email!!!")
    private String email;
    @NotNull(message = "!!!Укажите возраст, поле не должно быть пустым!!!")
    private int age;
    @NotNull(message = "!!!Укажите вес, поле не должно быть пустым!!!")
    @Min(value = 40, message = "!!!Некоректный вес!!!")
    @Max(value = 210, message = "!!!Некоректный вес!!!")
    private float weight;
    @NotNull(message = "!!!Укажите рост, поле не должно быть пустым!!!")
    @Min(value = 120, message = "!!!Некоректный рост!!!")
    @Max(value = 210, message = "!!!Некоректный рост!!!")
    private int height;
    // Сюда загонять цель в качестве ENUM ( Сейчас разберусь как )
    @NotEmpty(message = "!!!Укажите цель, поле не должно быть пустым!!!")
    private String purpose;
    @NotNull(message = "!!!Ошибка вычисления дневной нормы!!!")
    private Float normCalories;
    @OneToMany(mappedBy = "user")
    private List<Meal> meal_id;
}
