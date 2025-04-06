package com.example.rest_app_person_calories.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DishesDTO {
    @NotEmpty(message = "!!!Поле с названием блюда не должно быть пустым!!!")
    @Size(min = 2, max = 100, message = "!!!Неправильное название блюда!!!")
    private String name;
    @NotNull(message = "!!!Поле с кол-вом каллорий не должно быть пустым!!!")
    private Float caloriesPerPortion;
    @Pattern(regexp = "^Белки: *\\d+, *Жиры: *\\d+, *Углеводы: *\\d+$",
            message = "Неверный формат БЖУ. Ожидается: 'Белки: число, Жиры: число, Углеводы: число'")
    private String nutrients;
}
