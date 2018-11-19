package com.example.copsboot.report.web;

import static com.example.copsboot.infrastructure.security.SecurityHelperForMockMvc.HEADER_AUTHORIZATION;
import static com.example.copsboot.infrastructure.security.SecurityHelperForMockMvc.bearer;
import static com.example.copsboot.infrastructure.security.SecurityHelperForMockMvc.obtainAccessToken;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.copsboot.report.Report;
import com.example.copsboot.report.ReportId;
import com.example.copsboot.report.ReportService;
import com.example.copsboot.user.Users;
import com.example.copsboot.user.web.CopsbootControllerTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.UUID;

@RunWith(SpringRunner.class)
@CopsbootControllerTest(ReportRestController.class)
public class ReportRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReportService service;

    @Test
    public void officerIsAbleToPostAReport() throws Exception {
        String accessToken = obtainAccessToken(mockMvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);
        String dateTime = "2018-04-11T22:59:03.189+02:00";
        String description = "This is a test report description.";
        MockMultipartFile image = createMockImage();
        CreateReportParameters parameters = new CreateReportParameters(ZonedDateTime.parse(dateTime), description, false, 0, image);
        when(service.createReport(eq(Users.officer().getId()), any(ZonedDateTime.class), eq(description), any(MockMultipartFile.class)))
                .thenReturn(new Report(new ReportId(UUID.randomUUID()), Users.officer(), ZonedDateTime.parse(dateTime), description));

        mockMvc.perform(multipart("/api/reports")
                .file(image)
                .header(HEADER_AUTHORIZATION, bearer(accessToken))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(parameters))
                .param("dateTime", dateTime)
                .param("description", description))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("reporter").value(Users.OFFICER_EMAIL))
                .andExpect(jsonPath("dateTime").value("2018-04-11T22:59:03.189+02:00"))
                .andExpect(jsonPath("description").value(description));
    }

    private MockMultipartFile createMockImage() {
        return new MockMultipartFile("image", "picture.png", "image/png", new byte[] {1, 2, 3});
    }
}