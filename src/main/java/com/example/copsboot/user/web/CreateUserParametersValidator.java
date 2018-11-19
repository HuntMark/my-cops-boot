package com.example.copsboot.user.web;

import com.example.copsboot.user.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateUserParametersValidator implements ConstraintValidator<ValidCreateUserParameters, CreateOfficerParameters> {

    private final UserRepository userRepository;

    public CreateUserParametersValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(ValidCreateUserParameters constraintAnnotation) {
    }

    @Override
    public boolean isValid(CreateOfficerParameters userParameters, ConstraintValidatorContext context) {
        boolean result = true;
        if (userRepository.findByEmailIgnoreCase(userParameters.getEmail()).isPresent()) {
            context.buildConstraintViolationWithTemplate("There is already a user with the given email address.")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            result = false;
        }
        return result;
    }
}
