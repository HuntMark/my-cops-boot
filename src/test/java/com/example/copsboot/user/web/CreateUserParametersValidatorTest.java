package com.example.copsboot.user.web;

import static com.example.copsboot.report.web.ConstraintViolationSetAssert.assertThat;
import static org.mockito.Mockito.when;

import com.example.copsboot.user.User;
import com.example.copsboot.user.UserId;
import com.example.copsboot.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class CreateUserParametersValidatorTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ValidatorFactory factory;

    @Test
    public void invalidIfAlreadyUserWithGivenEmail() {
        String email = "wim.deblauwe@example.com";
        when(userRepository.findByEmailIgnoreCase(email))
                .thenReturn(Optional.of(User.createOfficer(new UserId(UUID.randomUUID()),
                        email, encoder.encode("testing1234"))));

        Validator validator = factory.getValidator();
        CreateOfficerParameters userParameters = new CreateOfficerParameters();
        userParameters.setEmail(email);
        userParameters.setPassword("my-secret-pwd-1234");
        Set<ConstraintViolation<CreateOfficerParameters>> violationSet = validator.validate(userParameters);
//        assertThat(violationSet).hasViolationSize(2).hasViolationOnPath("email");
    }

    @Test
    public void validIfNoUserWithGivenEmail() {
        String email = "wim.deblauwe@example.com";
        when(userRepository.findByEmailIgnoreCase(email))
                .thenReturn(Optional.empty());
        Validator validator = factory.getValidator();
        CreateOfficerParameters userParameters = new CreateOfficerParameters();
        userParameters.setEmail(email);
        userParameters.setPassword("my-secret-pwd-1234");
        Set<ConstraintViolation<CreateOfficerParameters>> violationSet = validator.validate(userParameters);
        assertThat(violationSet).hasNoViolations();
    }
}