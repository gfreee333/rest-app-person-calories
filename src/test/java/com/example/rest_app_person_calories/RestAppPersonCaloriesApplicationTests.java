package com.example.rest_app_person_calories;

import com.example.rest_app_person_calories.model.Users;
import com.example.rest_app_person_calories.services.MealService;
import com.example.rest_app_person_calories.services.UsersService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RestAppPersonCaloriesApplicationTests {

	@Autowired
	private UsersService usersService;
	@Autowired
	private MealService mealService;

	@Test
	public void testResultNormCalories() {

		Users user = new Users();
		user.setName("Valera");
		user.setAge(30);
		user.setWeight(80.0f);
		user.setEmail("Hello@email.com");
		user.setHeight(180);
		user.setPurpose("Поддержание");

		Users user2 = new Users();
		user2.setName("Svet222");
		user2.setAge(37);
		user2.setWeight(89.0f);
		user2.setEmail("TestуE@email.com");
		user2.setHeight(160);
		user2.setPurpose("Набор массы");

		Users user3 = new Users();
		user3.setName("Проверка");
		user3.setAge(37);
		user3.setWeight(89.0f);
		user3.setEmail("TeE@email.com");
		user3.setHeight(170);
		user3.setPurpose("Похудение");

		float dailyCalorieNormTest1 = usersService.numberOfColorsPerDay(user.getHeight(),
				user.getHeight(), user.getAge(), user.getPurpose());
		float dailyCalorieNormTest2 = usersService.numberOfColorsPerDay(user2.getHeight(),
				user2.getHeight(), user2.getAge(), user2.getPurpose());
		float dailyCalorieNormTest3 = usersService.numberOfColorsPerDay(user3.getHeight(),
				user3.getHeight(), user3.getAge(), user3.getPurpose());
		assertEquals(3238.73, dailyCalorieNormTest1, 1.0f); // Замените на ожидаемое значение
		assertEquals(4224.34,dailyCalorieNormTest2, 1.0f);
		assertEquals(2403.0,dailyCalorieNormTest3, 1.0f);
	}

	@Test
	public void testIsNormExceeded_TotalCaloriesLessThanNorm() {
		assertTrue(mealService.isNormExceeded(1500.0f, 2000.0f));
	}

	@Test
	public void testIsNormExceeded_TotalCaloriesEqualsNorm() {
		assertTrue(mealService.isNormExceeded(2000.0f, 2000.0f));
	}

	@Test
	public void testIsNormExceeded_TotalCaloriesGreaterThanNorm() {
		assertFalse(mealService.isNormExceeded(2500.0f, 2000.0f));
	}
}
