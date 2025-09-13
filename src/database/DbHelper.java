package database;

import java.sql.*;

public class DbHelper {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Warning: MySQL driver not found on classpath. Add mysql-connector-java.jar");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DbConfig.DB_URL, DbConfig.DB_USER, DbConfig.DB_PASS);
    }

    public static void createTableIfNotExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "student_id VARCHAR(10) PRIMARY KEY," +
                "full_name VARCHAR(100)," +
                "dob DATE," +
                "major VARCHAR(10)," +
                "gpa DOUBLE," +
                "class_name VARCHAR(50)" +
                ")";
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }
}

