package com.example.players.validation;

import com.example.players.entity.Player;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Period;

@Component
public class PlayerValidate implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Player player = (Player) target;

        String name = player.getName();
        if (name == null || name.trim().isEmpty()) {
            errors.rejectValue("fullName", "fullName.empty", "Tên không được để trống");
        } else if (name.length() < 5 || name.length() > 100) {
            errors.rejectValue("fullName", "fullName.length", "Tên phải từ 5 đến 100 ký tự");
        } else if (!name.matches("^[\\p{L}\\s]+$")) {
            errors.rejectValue("fullName", "fullName.invalid", "Tên không được chứa ký tự đặc biệt");
        }

        if (player.getDayOfBirth() == null) {
            errors.rejectValue("dayOfBirth", "dayOfBirth.null", "Ngày sinh không được để trống");
        } else {
            int age = Period.between(player.getDayOfBirth(), LocalDate.now()).getYears();
            if (age < 16 || age > 100) {
                errors.rejectValue("dayOfBirth", "dayOfBirth.range", "Tuổi phải từ 16 đến 100");
            }
        }

        try {
            int exp = Integer.parseInt(player.getExperience().trim());
            if (exp <= 0) {
                errors.rejectValue("experience", "experience.invalid", "Kinh nghiệm phải là số nguyên dương");
            }
        } catch (Exception e) {
            errors.rejectValue("experience", "experience.invalid", "Kinh nghiệm phải là số nguyên dương");
        }
    }
}
