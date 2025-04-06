package com.example.rest_app_person_calories.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dishes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "!!!Поле с названием блюда не должно быть пустым!!!")
    @Size(min = 2, max = 100, message = "!!!Неправильное название блюда!!!")
    private String name;
    @NotNull(message = "!!!Поле с кол-вом каллорий не должно быть пустым!!!")
    private float caloriesPerPortion;
    @Pattern(regexp = "^Белки: *\\d+, *Жиры: *\\d+, *Углеводы: *\\d+$",
             message = "Неверный формат БЖУ. Ожидается: 'Белки: число, Жиры: число, Углеводы: число'")
    private String nutrients;
    @ManyToMany(mappedBy = "dishes")
    private List<Meal> meals;
}
