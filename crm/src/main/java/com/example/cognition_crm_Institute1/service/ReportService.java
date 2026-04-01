package com.example.cognition_crm_Institute1.service;

import java.util.Map;

public interface ReportService {

    Map<String, Long> getStatusReport();

    Map<String, Long> getDailyReport();

    Map<String, Long> getCourseReport();

    Long getTotalEnquiries();

    Long getConvertedEnquiries();

    Double getConversionRate();
}
