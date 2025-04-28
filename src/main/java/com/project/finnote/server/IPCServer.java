// File: src/main/java/com/project/finnote/server/IPCServer.java
package com.project.finnote.server;

import com.project.finnote.entity.Category;
import com.project.finnote.entity.FinancialRecord;
import com.project.finnote.entity.Notes;
import com.project.finnote.entity.ReportItem;
import com.project.finnote.services.CategoryService;
import com.project.finnote.services.NoteService;
import com.project.finnote.services.FinancialRecordService;
import com.project.finnote.services.ReportService;
import com.project.finnote.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.math.BigDecimal;

/**
 * Simple TCP CRUD & Sync Server
 */
public class IPCServer {
    private static final int PORT = 5555;
    private final CategoryService categoryService = new CategoryService();
    private final NoteService notesService       = new NoteService();
    private final FinancialRecordService recordService = new FinancialRecordService();
    private final ReportService reportService    = new ReportService();
    private final ObjectMapper mapper = JsonUtils.MAPPER;

    public void start() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("IPCServer listening on port " + PORT);
            while (true) {
                Socket client = serverSocket.accept();
                new Thread(() -> handleClient(client)).start();
            }
        }
    }

    private void handleClient(Socket client) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer    = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true)
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split("\\|", -1);
                String cmd = parts[0];
                switch (cmd) {
                    case "GET_CATEGORIES": {
                        List<Category> cats = categoryService.findAll();
                        writer.println(mapper.writeValueAsString(cats));
                        break;
                    }
                    case "GET_NOTES": {
                        List<Notes> notes = notesService.findAll();
                        writer.println(mapper.writeValueAsString(notes));
                        break;
                    }
                    case "GET_RECORDS": {
                        List<FinancialRecord> recs = recordService.findAll();
                        writer.println(mapper.writeValueAsString(recs));
                        break;
                    }
                    case "GET_REPORT": {
                        List<ReportItem> report = reportService.generateReport();
                        writer.println(mapper.writeValueAsString(report));
                        break;
                    }
                    case "ADD_NOTE": {
                        Notes n = notesService.addNote(parts[1], parts[2], Integer.parseInt(parts[3]));
                        writer.println(mapper.writeValueAsString(n));
                        break;
                    }
                    case "ADD_RECORD": {
                        try {
                            // Parse amount as BigDecimal
                            BigDecimal amount = new BigDecimal(parts[1]);
                            String currency = parts[2];
                            int categoryId = Integer.parseInt(parts[3]);
                            String noteText = parts[4];

                            FinancialRecord r = recordService.addRecord(
                                    amount,
                                    currency,
                                    categoryId,
                                    noteText
                            );
                            writer.println(mapper.writeValueAsString(r));
                        } catch (Exception ex) {
                            ObjectNode err = mapper.createObjectNode();
                            err.put("error", "ADD_RECORD failed: " + ex.getMessage());
                            writer.println(mapper.writeValueAsString(err));
                        }
                        break;
                    }
                    case "QUIT": {
                        writer.println("{\"status\":\"bye\"}");
                        client.close();
                        return;
                    }
                    default: {
                        ObjectNode err = mapper.createObjectNode();
                        err.put("error", "Unknown command: " + cmd);
                        writer.println(mapper.writeValueAsString(err));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new IPCServer().start();
    }
}
