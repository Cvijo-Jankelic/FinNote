package com.project.finnote.utils;

import com.project.finnote.builders.CategoryBuilder;
import com.project.finnote.entity.Category;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.project.finnote.entity.Notes;
import com.project.finnote.enums.TypeCategoryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseUtils {
    private static final String DATABASE_FILE = "database-properties/database.properties";
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

    public static Connection connectionToDataBase() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_FILE));
        String dataBaseUrl = properties.getProperty("databaseUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection connection = DriverManager.getConnection(dataBaseUrl, username, password);

        return connection;
    }


    public static List<Category> getCategoriesFromDatabase() {
        List<Category> categoryList = new ArrayList<>();
        try(Connection connection = connectionToDataBase()){
            String sqlQuery ="SELECT * FROM categories";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                Integer id = rs.getInt("categories_id");
                String name = rs.getString("name");
                TypeCategoryEnum type = TypeCategoryEnum.valueOf(rs.getString("type"));
                LocalDateTime createdAt = rs.getObject("created_at", LocalDateTime.class);
                Category newCategory = new CategoryBuilder()
                        .setCategoryId(id)
                        .setName(name)
                        .setType(type)
                        .setCreatedAt(createdAt)
                        .createCategory();

                categoryList.add(newCategory);
            }
        }catch (SQLException | IOException ex){
            String msg = "Exception with getting categories from database";
            System.out.println(msg);
            logger.info(msg, ex);
            logger.error(msg, ex);
        }
        return categoryList;
    }

    public static void saveNotesToDatabase(Notes notesToInsert){

        try(Connection connection = connectionToDataBase()){
            String sqlQuery = "INSER INTO notes(note_id, user_id, title, content, category, created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setInt(1, notesToInsert.getNoteId());
            pstmt.setInt(2, notesToInsert.getUserId());
            pstmt.setString(3, notesToInsert.getTitle());
            // zavrsiti do kraja sve parametre

        }catch (IOException | SQLException ex){
            String message = "Exception with saving notes to database";
            logger.error(message, ex);
            logger.info(message, ex);
            System.out.println(message);
        }

    }



}