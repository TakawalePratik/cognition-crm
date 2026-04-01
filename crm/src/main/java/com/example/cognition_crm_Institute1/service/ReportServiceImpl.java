package com.example.cognition_crm_Institute1.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cognition_crm_Institute1.repository.EnquiryRepository;

import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Override
    public Map<String, Long> getStatusReport() {
        Map<String, Long> map = new HashMap<>();
        for (Object[] row : enquiryRepository.countByStatus()) {
            map.put((String) row[0], (Long) row[1]);
        }
        return map;
    }

    @Override
    public Map<String, Long> getDailyReport() {
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : enquiryRepository.countByDate()) {
            map.put(row[0].toString(), (Long) row[1]);
        }
        return map;
    }

    @Override
    public Map<String, Long> getCourseReport() {
        Map<String, Long> map = new HashMap<>();
        for (Object[] row : enquiryRepository.countByCourse()) {
            map.put((String) row[0], (Long) row[1]);
        }
        return map;
    }

    @Override
    public Long getTotalEnquiries() {
        return enquiryRepository.totalEnquiries();
    }

    @Override
    public Long getConvertedEnquiries() {
        return enquiryRepository.convertedEnquiries();
    }

    @Override
    public Double getConversionRate() {
        Long total = getTotalEnquiries();
        Long converted = getConvertedEnquiries();

        if (total == 0) return 0.0;

        return (converted * 100.0) / total;
    }
}
