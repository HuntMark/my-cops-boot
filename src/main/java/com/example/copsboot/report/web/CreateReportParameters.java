package com.example.copsboot.report.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReportParameters {
    private ZonedDateTime dateTime;
    private String description;
}
