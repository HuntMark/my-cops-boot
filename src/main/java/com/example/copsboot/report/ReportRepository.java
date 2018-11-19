package com.example.copsboot.report;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ReportRepository extends CrudRepository<Report, UUID>, ReportRepositoryCustom {
}
