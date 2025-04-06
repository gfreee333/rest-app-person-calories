package com.example.rest_app_person_calories.services;
import com.example.rest_app_person_calories.model.Users;
import com.example.rest_app_person_calories.repositories.UsersRepository;
import com.example.rest_app_person_calories.util.EmailAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {
    private final UsersRepository usersRepository;
    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public Optional<Users> findById(int id){
        return usersRepository.findById(id);
    }
    @Transactional
    public void saveUser(Users users){
        try {
            usersRepository.save(users);
        }
        catch (DataIntegrityViolationException e){
            throw new EmailAlreadyExistsException("Этот email уже используется");
        }
    }

    public float getNormCalories(int id){
        Optional<Users> usersOptional = usersRepository.findById(id);
        if(usersOptional.isPresent()){
            Users user = usersOptional.get();
            return user.getNormCalories();
        } else return 0;
    }

    public float numberOfColorsPerDay(float weight, int height, int age, String purpose){
        final double formula = 66.5 + (13.75 * weight) + (5.003 * height) - (6.777 * age);
        return switch (purpose) {
            case "Похудение" -> (float) (formula * 0.80);
            case "Поддержание" -> (float) formula;
            case "Набор массы" -> (float) (formula * 1.5);
            default -> 0;
        };
    }
}
