package com.example.rest_app_person_calories.repositories;

import com.example.rest_app_person_calories.model.Dishes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DishesRepository extends JpaRepository<Dishes, Integer> {
    List<Dishes> findAllById(Iterable<Integer> ids);
}
