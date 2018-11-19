package com.example.copsboot.report;

import com.example.copsboot.user.UserId;
import com.example.copsboot.user.UserNotFoundException;
import com.example.copsboot.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Report createReport(UserId userId, ZonedDateTime dateTime, String description) {
        return reportRepository.save(new Report(reportRepository.nextId(),
                userRepository.findById(userId.getId()).orElseThrow(() -> new UserNotFoundException(userId)), dateTime, description));
    }
}
