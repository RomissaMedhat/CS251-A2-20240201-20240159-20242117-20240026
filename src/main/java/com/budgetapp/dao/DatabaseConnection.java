package com.budgetapp.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:budgeting.db";
    private static Connection connection = null;
    private static boolean initialized = false;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
            if (!initialized) {
                initializeDatabase();
                initialized = true;
            }
        }
        return connection;
    }

    private static void initializeDatabase() {
        try (InputStream is = DatabaseConnection.class.getResourceAsStream("/database_schema.sql")) {
            if (is == null) {
                createTablesManually();
                return;
            }
            String sql = new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));
            try (Statement stmt = connection.createStatement()) {
                for (String s : sql.split(";")) {
                    if (!s.trim().isEmpty()) stmt.execute(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTablesManually() throws SQLException {
        String[] tables = {
                "CREATE TABLE IF NOT EXISTS users (user_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, email TEXT UNIQUE NOT NULL, password_hash TEXT NOT NULL, currency TEXT DEFAULT 'EGP', language TEXT DEFAULT 'en', budget_alerts_enabled BOOLEAN DEFAULT 1, goal_reminders_enabled BOOLEAN DEFAULT 1, created_at DATETIME DEFAULT CURRENT_TIMESTAMP)",
                "CREATE TABLE IF NOT EXISTS categories (category_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, name TEXT NOT NULL, is_default BOOLEAN DEFAULT 0, FOREIGN KEY (user_id) REFERENCES users(user_id))",
                "INSERT OR IGNORE INTO categories (name, is_default) VALUES ('Food',1), ('Transport',1), ('Bills',1), ('Entertainment',1), ('Shopping',1), ('Health',1)",
                "CREATE TABLE IF NOT EXISTS transactions (transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, amount REAL NOT NULL, type TEXT CHECK(type IN ('INCOME','EXPENSE')) NOT NULL, category_id INTEGER, description TEXT, transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (user_id) REFERENCES users(user_id), FOREIGN KEY (category_id) REFERENCES categories(category_id))",
                "CREATE TABLE IF NOT EXISTS budgets (budget_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, category_id INTEGER NOT NULL, amount REAL NOT NULL, spent REAL DEFAULT 0, month INTEGER NOT NULL, year INTEGER NOT NULL, alert_threshold INTEGER DEFAULT 75, FOREIGN KEY (user_id) REFERENCES users(user_id), FOREIGN KEY (category_id) REFERENCES categories(category_id), UNIQUE(user_id, category_id, month, year))",
                "CREATE TABLE IF NOT EXISTS goals (goal_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, name TEXT NOT NULL, target_amount REAL NOT NULL, current_amount REAL DEFAULT 0, deadline DATE NOT NULL, status TEXT DEFAULT 'IN_PROGRESS', FOREIGN KEY (user_id) REFERENCES users(user_id))",
                "CREATE TABLE IF NOT EXISTS notifications (notification_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER NOT NULL, type TEXT NOT NULL, message TEXT NOT NULL, is_read BOOLEAN DEFAULT 0, created_at DATETIME DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (user_id) REFERENCES users(user_id))"
        };
        try (Statement stmt = connection.createStatement()) {
            for (String sql : tables) stmt.execute(sql);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}