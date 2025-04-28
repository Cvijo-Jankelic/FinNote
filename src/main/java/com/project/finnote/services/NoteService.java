package com.project.finnote.services;

import com.project.finnote.entity.Category;
import com.project.finnote.entity.Notes;
import com.project.finnote.interfaces.INoteService;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class NoteService implements INoteService {
    private final CategoryService categoryService = new CategoryService();
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

    public List<Notes> findAll() {
        // Map categoryId → Category
        Map<Integer, Category> catMap = categoryService.findAll()
                .stream()
                .collect(Collectors.toMap(Category::getCategoryId, c -> c));

        List<Notes> list = new ArrayList<>();
        String sql = "SELECT note_id, user_id, title, content, category_id, created_at, updated_at "
                + "FROM finnote_notes";

        try (Connection conn = connectionToDataBase()){
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Integer noteId    = rs.getInt("note_id");
                Integer userId    = rs.getInt("user_id");
                String title      = rs.getString("title");
                String content    = rs.getString("content");
                Category category = catMap.get(rs.getInt("category_id"));
                LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
                LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

                list.add(new Notes(
                        noteId, userId, title, content,
                        category, createdAt, updatedAt
                ));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Notes addNote(String title, String content, int categoryId) {
        //TODO override this method!
        return null;
    }
}
