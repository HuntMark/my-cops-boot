package com.example.copsboot.report.web;

import com.example.copsboot.infrastructure.security.ApplicationUserDetails;
import com.example.copsboot.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reports")
public class ReportRestController {

    private final ReportService service;

    @Autowired
    public ReportRestController(ReportService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReportDto createReport(@AuthenticationPrincipal ApplicationUserDetails userDetails,
                                  @Valid @RequestBody CreateReportParameters parameters) {
        return ReportDto.fromReport(service.createReport(userDetails.getUserId(),
                parameters.getDateTime(),
                parameters.getDescription()));
    }
}
