package com.project.finnote.remote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.finnote.entity.ReportItem;
import com.project.finnote.interfaces.IReportService;

import java.util.List;

public class RemoteReportService implements IReportService {
    private final RemoteServiceHelper helper = new RemoteServiceHelper();

    @Override
    public List<ReportItem> generateReport() {
        try {
            String json = helper.sendCommand("GET_REPORT");
            return helper.mapper().readValue(json, new TypeReference<List<ReportItem>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Remote fetch of report items failed", e);
        }
    }
}
