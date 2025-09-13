package model;
import model.SinhVien;
import database.DbHelper;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
public class StudentDAO {
	public boolean insertStudent(SinhVien s) throws SQLException {
		String sql = "INSERT INTO students (student_id, full_name, dob, major, gpa, class_name) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DBHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
		pst.setString(1, s.getStudentId());
		pst.setString(2, s.getFullName());
		pst.setDate(3, Date.valueOf(s.getDob()));
		pst.setString(4, s.getMajor());
		pst.setDouble(5, s.getGpa());
		pst.setString(6, s.getClassName());
		return pst.executeUpdate() > 0;
		}
		}


		public boolean updateStudent(SinhVien s) throws SQLException {
		String sql = "UPDATE students SET full_name=?, dob=?, major=?, gpa=?, class_name=? WHERE student_id=?";
		try (Connection conn = DbHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
		pst.setString(1, s.getFullName());
		pst.setDate(2, Date.valueOf(s.getDob()));
		pst.setString(3, s.getMajor());
		pst.setDouble(4, s.getGpa());
		pst.setString(5, s.getClassName());
		pst.setString(6, s.getStudentId());
		return pst.executeUpdate() > 0;
		}
		}


		public boolean deleteStudent(String studentId) throws SQLException {
		String sql = "DELETE FROM students WHERE student_id=?";
		try (Connection conn = DBHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
		pst.setString(1, studentId);
		return pst.executeUpdate() > 0;
		}
		}


		public boolean exists(String studentId) throws SQLException {
		String sql = "SELECT 1 FROM students WHERE student_id=?";
		try (Connection conn = DBHelper.getConnection(); PreparedStatement pst = conn.prepareStatement(sql)) {
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


		public List<SinhVien>getAll() throws SQLException {
			
			return null;
		}
}