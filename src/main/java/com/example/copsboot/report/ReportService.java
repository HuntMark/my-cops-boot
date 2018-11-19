package com.example.copsboot.report;

import com.example.copsboot.user.UserId;

import java.time.ZonedDateTime;

public interface ReportService {
    Report createReport(UserId userId, ZonedDateTime dateTime, String description);
}
