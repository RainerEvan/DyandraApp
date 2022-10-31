package com.app.demo.resolver.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.demo.model.Reports;
import com.app.demo.service.ReportService;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ReportQueryResolver implements GraphQLQueryResolver {
    
    @Autowired
    private final ReportService reportService;

    public List<Reports> getAllReports(){
        return reportService.getAllReports();
    }

    public Reports getReportByReportId(String reportId){
        return reportService.getReportByReportId(reportId);
    }
}
