package com.project.finnote.interfaces;

import com.project.finnote.entity.ReportItem;

import java.util.List;

public interface IReportService {
    List<ReportItem> generateReport();

}
