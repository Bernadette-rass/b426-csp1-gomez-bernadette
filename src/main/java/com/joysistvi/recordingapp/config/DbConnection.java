package com.joysistvi.recordingapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private final static String url = "jdbc:mysql://localhost:3306/recording_studio_db";
    private final static String username = "root";
    private final static String password = "";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
