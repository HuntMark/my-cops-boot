package com.example.copsboot.report;

import com.example.copsboot.user.UserId;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZonedDateTime;

public interface ReportService {
    Report createReport(UserId userId, ZonedDateTime dateTime, String description, MultipartFile image);
}
