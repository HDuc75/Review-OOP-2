package model;

import model.SinhVien;

import database.DbHelper;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class SinhVienDAO {

    public boolean insertStudent(SinhVien s) throws SQLException {
        String sql = "INSERT INTO students (student_id, full_name, dob, major, gpa, class_name) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DbHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getMaSinhVien());
            pst.setString(2, s.getHoTen());
            pst.setDate(3, Date.valueOf(s.getNgaySinh()));
            pst.setString(4, s.getNganhDaoTao());
            pst.setDouble(5, s.getDiemTrungBinh());
            pst.setString(6, s.getLopSinhHoat());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean updateStudent(SinhVien s) throws SQLException {
        String sql = "UPDATE students SET full_name=?, dob=?, major=?, gpa=?, class_name=? WHERE student_id=?";
        try (Connection conn = DbHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getHoTen());
            pst.setDate(2, Date.valueOf(s.getNgaySinh()));
            pst.setString(3, s.getNganhDaoTao());
            pst.setDouble(4, s.getDiemTrungBinh());
            pst.setString(5, s.getLopSinhHoat());
            pst.setString(6, s.getMaSinhVien());
            return pst.executeUpdate() > 0;
        }
    }

    public boolean deleteStudent(String studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (Connection conn = DbHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, studentId);
            return pst.executeUpdate() > 0;
        }
    }

    public boolean exists(String studentId) throws SQLException {
        String sql = "SELECT 1 FROM students WHERE student_id=?";
        try (Connection conn = DbHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, studentId);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        }
    }

    private List<SinhVien> mapResultSet(ResultSet rs) throws SQLException {
        List<SinhVien> list = new ArrayList<>();
        while (rs.next()) {
            String id = rs.getString("student_id");
            String name = rs.getString("full_name");
            LocalDate dob = rs.getDate("dob").toLocalDate();
            String major = rs.getString("major");
            double gpa = rs.getDouble("gpa");
            String cls = rs.getString("class_name");
            list.add(new SinhVien(id, name, dob, major, gpa, cls));
        }
        return list;
    }

    public List<SinhVien> getAll() throws SQLException {
        String sql = "SELECT * FROM students";
        try (Connection conn = DbHelper.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return mapResultSet(rs);
        }
    }

    public List<SinhVien> getByClass(String className) throws SQLException {
        String sql = "SELECT * FROM students WHERE class_name=?";
        try (Connection conn = DbHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, className);
            try (ResultSet rs = pst.executeQuery()) {
                return mapResultSet(rs);
            }
        }
    }

    public List<SinhVien> getByMajor(String major) throws SQLException {
        String sql = "SELECT * FROM students WHERE major=?";
        try (Connection conn = DbHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, major);
            try (ResultSet rs = pst.executeQuery()) {
                return mapResultSet(rs);
            }
        }
    }

    public List<SinhVien> getSortedByGpaDesc() throws SQLException {
        String sql = "SELECT * FROM students ORDER BY gpa DESC";
        try (Connection conn = DbHelper.getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return mapResultSet(rs);
        }
    }

    public List<SinhVien> getByBirthMonth(int month) throws SQLException {
        String sql = "SELECT * FROM students WHERE MONTH(dob)=?";
        try (Connection conn = DbHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, month);
            try (ResultSet rs = pst.executeQuery()) {
                return mapResultSet(rs);
            }
        }
    }

	public List<SinhVien> getAll(String cls) {
		return null;
	}
}
