package com.example.copsboot.report.web;

import static com.example.copsboot.report.web.ConstraintViolationSetAssert.assertThat;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.ZonedDateTime;
import java.util.Set;

public class CreateReportParametersValidatorTest {

    @Test
    public void givenTrafficIncidentButInvolvedCarsZero_invalid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(), "The suspect was wearing a black hat", true, 0);
        Set<ConstraintViolation<CreateReportParameters>> violationSet = validator.validate(parameters);
        assertThat(violationSet).hasViolationOnPath("");
    }

    @Test
    public void givenTrafficIncident_involvedCarsMustBePositive() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(), "The suspect was wearing a black hat.", true, 2);
        Set<ConstraintViolation<CreateReportParameters>> violationSet = validator.validate(parameters);
        assertThat(violationSet).hasNoViolations();
    }
}