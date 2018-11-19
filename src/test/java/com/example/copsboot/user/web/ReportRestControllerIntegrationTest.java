package com.example.copsboot.user.web;

import com.example.copsboot.user.UserService;
import com.example.copsboot.user.Users;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReportRestControllerIntegrationTest {

    @LocalServerPort
    private int serverPort;

    @Autowired
    private UserService userService;

    @Before
    public void setup() {
        RestAssured.port = serverPort;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    public void officerIsUnableToPostAReportIfFileSizeIsTooBig() {
        userService.createOfficer(Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);

        String dateTime = "2018-04-11T22:59:03.189+02:00";
        String description = "The suspect is wearing a black hat.";

        SecurityHelperForRestAssured.givenAuthenticatedUser(serverPort, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD)
                .when()
                .multiPart("image", new MultiPartSpecBuilder(new byte[2_000_000])
                        .fileName("picture.png")
                        .build())
                .formParam("dateTime", dateTime)
                .formParam("description", description)
                .post("/api/reports")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
