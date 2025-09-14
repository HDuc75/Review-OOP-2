package util;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import model.SinhVien;
import model.SinhVienDAO;

public class Utils {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");


	public static String normalizeName(String name) {
            if (name == null) return "";
               name = name.trim().replaceAll("\\s+", " ");
               String[] parts = name.toLowerCase().split(" ");
               StringBuilder sb = new StringBuilder();
               
               for (String p : parts) {
            	   	if (p.length() > 0) {
            	   		sb.append(Character.toUpperCase(p.charAt(0)));
            	   		if (p.length() > 1) sb.append(p.substring(1));
            	   			sb.append(' ');
            	   	}
               }
          return sb.toString().trim();
	 }


	public static LocalDate parseDate(String s) throws DateTimeParseException {
		return LocalDate.parse(s, DATE_FORMAT);
	}


	public static boolean validateMajor(String m) {
		if (m == null) return false;
			String mm = m.trim().toUpperCase();
			return mm.equals("CNTT") || mm.equals("KTPM");
	}


	public static boolean validateStudentIdByMajor(String id, String major) {
			if (id == null || !id.matches("\\d{10}")) return false;
			if ("CNTT".equalsIgnoreCase(major)) return id.startsWith("455105");
			if ("KTPM".equalsIgnoreCase(major)) return id.startsWith("455109");
			return false;
}


	public static boolean validateGpa(double gpa) { 
		return gpa >= 0.0 && gpa <= 10.0; 
	}


	public static boolean validateAge(LocalDate dob) {
		int age = Period.between(dob, LocalDate.now()).getYears();
		return age >= 15 && age <= 110;
	}
}