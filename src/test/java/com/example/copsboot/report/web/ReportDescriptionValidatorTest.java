package com.example.copsboot.report.web;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.ZonedDateTime;
import java.util.Set;

public class ReportDescriptionValidatorTest {

    @Test
    public void givenEmptyString_notValid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(), "", false, 0);
        Set<ConstraintViolation<CreateReportParameters>> violationSet = validator.validate(parameters);
        ConstraintViolationSetAssert.assertThat(violationSet).hasViolationOnPath("description");
    }

    @Test
    public void givenSuspectWordPresent_valid() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.now(), "The suspect was wearing a black hat.", false, 0);
        Set<ConstraintViolation<CreateReportParameters>> violationSet = validator.validate(parameters);
        ConstraintViolationSetAssert.assertThat(violationSet).hasNoViolations();
    }
}