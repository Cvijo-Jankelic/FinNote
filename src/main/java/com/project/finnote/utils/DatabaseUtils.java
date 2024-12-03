package com.project.finnote.utils;

import com.project.finnote.entity.Category;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseUtils {
    private static final String DATABASE_FILE = "database-properties/database.properties";

    //  TODO -- Add logging class for logging warning ans exceptions in class
    public static Connection connectionToDataBase() throws SQLException, IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_FILE));
        String dataBaseUrl = properties.getProperty("databaseUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection connection = DriverManager.getConnection(dataBaseUrl, username, password);

        return connection;
    }


    public static List<Category> getCategoriesFromDatabase() throws SQLException, IOException {
        List<Category> categoryList = new ArrayList<>();
        try(Connection connection = connectionToDataBase()){
            String sqlQuery ="SELECT * FROM categories";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){

            }
        }catch (SQLException | IOException ex){
            String msg = "Exception with getting categories from database";
            System.out.println(msg);
        }
        return categoryList;
    }


}