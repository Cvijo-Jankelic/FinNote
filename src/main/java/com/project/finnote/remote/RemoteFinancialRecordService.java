package com.project.finnote.remote;

import com.fasterxml.jackson.core.type.TypeReference;
import com.project.finnote.entity.FinancialRecord;
import com.project.finnote.interfaces.IFinancialRecordService;

import java.math.BigDecimal;
import java.util.List;

public class RemoteFinancialRecordService implements IFinancialRecordService {
    private final RemoteServiceHelper helper = new RemoteServiceHelper();

    @Override
    public List<FinancialRecord> findAll() {
        try {
            String json = helper.sendCommand("GET_RECORDS");
            return helper.mapper().readValue(json, new TypeReference<List<FinancialRecord>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Remote fetch of financial records failed", e);
        }
    }

    @Override
    public FinancialRecord addRecord(BigDecimal amount, String currency, int categoryId, String note) {
        try {
            String cmd = String.join("|", "ADD_RECORD", amount.toPlainString(), currency,
                    String.valueOf(categoryId), note);
            String json = helper.sendCommand(cmd);
            return helper.mapper().readValue(json, FinancialRecord.class);
        } catch (Exception e) {
            throw new RuntimeException("Remote add financial record failed", e);
        }
    }
}
