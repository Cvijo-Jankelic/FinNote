package com.project.finnote.services;

import com.project.finnote.entity.FinancialRecord;           // entitet FinancialRecord :contentReference[oaicite:4]{index=4}&#8203;:contentReference[oaicite:5]{index=5}
import com.project.finnote.entity.Category;
import com.project.finnote.interfaces.IFinancialRecordService;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class FinancialRecordService implements IFinancialRecordService {
    private static final String DATABASE_FILE = "database-properties/database.properties";
    public static Connection connectionToDataBase() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_FILE));
        String dataBaseUrl = properties.getProperty("databaseUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection connection = DriverManager.getConnection(dataBaseUrl, username, password);

        return connection;
    }
    private final CategoryService categoryService = new CategoryService();

    public List<FinancialRecord> findAll() {
        // Preload kategorije
        Map<Integer,Category> catMap = categoryService.findAll()
                .stream()
                .collect(Collectors.toMap(Category::getCategoryId, c -> c));

        List<FinancialRecord> list = new ArrayList<>();
        String sql = "SELECT record_id, user_id, amount, currency, category_id, note, created_at "
                + "FROM finnote_financial_records";

        try (Connection conn = connectionToDataBase()){
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer recordId = rs.getInt("record_id");
                Integer userId   = rs.getInt("user_id");
                BigDecimal amount = rs.getBigDecimal("amount");
                String currency   = rs.getString("currency");
                Category category = catMap.get(rs.getInt("category_id"));
                String note       = rs.getString("note");
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                list.add(new FinancialRecord(
                        recordId, userId, amount, currency,
                        category, note, createdAt
                ));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public FinancialRecord addRecord(BigDecimal amount, String currency, int categoryId, String note) {
        String sql = "INSERT INTO finnote_financial_records (user_id, amount, currency, category_id, note, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        int currentUserId = 1; // TODO: replace with actual user context
        LocalDateTime now = LocalDateTime.now();
        Category cat = categoryService.findById(categoryId);

        try (Connection conn = connectionToDataBase();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, currentUserId);
            ps.setBigDecimal(2, amount);
            ps.setString(3, currency);
            ps.setInt(4, categoryId);
            ps.setString(5, note);
            ps.setTimestamp(6, Timestamp.valueOf(now));

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating financial record failed, no rows affected.");
            }

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    return new FinancialRecord(id, currentUserId, amount, currency, cat, note, now);
                } else {
                    throw new SQLException("Creating financial record failed, no ID obtained.");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding financial record", e);
        }
    }
}
}
