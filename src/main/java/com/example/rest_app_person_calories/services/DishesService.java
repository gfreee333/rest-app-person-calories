package com.example.rest_app_person_calories.services;
import com.example.rest_app_person_calories.model.Dishes;
import com.example.rest_app_person_calories.repositories.DishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DishesService {
    private final DishesRepository dishesRepository;
    @Autowired
    public DishesService(DishesRepository dishesRepository) {
        this.dishesRepository = dishesRepository;
    }
    public Optional<Dishes> findById(int id){
        return dishesRepository.findById(id);
    }

    public List<Dishes> findAllById(List<Integer> id){
        List<Integer> dishIdsLong = new ArrayList<>(id);
        return dishesRepository.findAllById(dishIdsLong);
    }

    @Transactional
    public void save(Dishes dishes){
        dishesRepository.save(dishes);
    }

}
