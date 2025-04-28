package com.project.finnote.services;

import com.project.finnote.entity.Category;
import com.project.finnote.enums.TypeCategoryEnum;
import com.project.finnote.interfaces.ICategoryService;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class CategoryService implements ICategoryService {
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
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT category_id, name, type, created_at FROM finnote_categories";

        try (Connection conn = connectionToDataBase()){
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer id   = rs.getInt("category_id");
                String name  = rs.getString("name");
                TypeCategoryEnum type = TypeCategoryEnum.valueOf(rs.getString("type"));
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

                list.add(new Category(id, name, type, createdAt));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Category findById(int categoryId) {
        //TODO overread this method
        return null;
    }
}
