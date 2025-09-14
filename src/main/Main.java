package main;

import model.SinhVienDAO;
import database.DbHelper;
import model.SinhVien;
import util.Utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final SinhVienDAO dao = new SinhVienDAO();

    public static void main(String[] args) {
        System.out.println("=== Quản lý sinh viên ===");
        try {
            DbHelper.createTableIfNotExists();
        } catch (Exception e) {
            System.out.println("Cannot create/connect DB: " + e.getMessage());
            return;
        }

        boolean run = true;
        while (run) {
            showMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addStudent(); break;
                case "2": deleteStudent(); break;
                case "3": editStudent(); break;
                case "4": printAll(); break;
                case "5": printByClass(); break;
                case "6": printByMajor(); break;
                case "7": printSortedByGpa(); break;
                case "8": printByBirthMonth(); break;
                case "9": insertSamples(); break;
            
                default: System.out.println("Chọn không hợp lệ.");
            }
        }
        System.out.println("Kết thúc");
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("1. Thêm sinh viên");
        System.out.println("2. Xóa sinh viên ");
        System.out.println("3. Sửa sinh viên");
        System.out.println("4. In danh sách theo lớp sinh hoạt");
        System.out.println("5. In toàn bộ sinh viên");
        System.out.println("6. In danh sách sinh viên của một ngành (CNTT/KTPM)");
        System.out.println("7. In danh sách sắp xếp theo điểm trung bình");
        System.out.println("8. In sinh viên sinh vào tháng X");
        System.out.println("9. Thoát");
        System.out.print("Hãy chọn:");
    }

    private static void addStudent() {
        try {
            System.out.print("Mã (10 chữ số): ");
            String id = sc.nextLine().trim();
            System.out.print("Họ tên: ");
            String name = sc.nextLine().trim();
            System.out.print("Ngày sinh dd/MM/yyyy: ");
            String dobStr = sc.nextLine().trim();
            System.out.print("Ngành (CNTT/KTPM): ");
            String major = sc.nextLine().trim().toUpperCase();
            System.out.print("GPA: ");
            String gpaStr = sc.nextLine().trim();
            System.out.print("Lớp: ");
            String cls = sc.nextLine().trim();

            if (!Utils.validateMajor(major)) { System.out.println("Ngành không hợp lệ."); return; }
            if (!id.matches("\\d{10}")) { System.out.println("Mã phải 10 chữ số."); return; }
            if (!Utils.validateStudentIdByMajor(id, major)) { System.out.println("Mã không phù hợp ngành."); return; }
            LocalDate dob;
            try { dob = Utils.parseDate(dobStr); } catch (DateTimeParseException ex) { System.out.println("Ngày không đúng định dạng."); return; }
            if (!Utils.validateAge(dob)) { System.out.println("Tuổi không hợp lệ."); return; }
            double gpa;
            try { gpa = Double.parseDouble(gpaStr); } catch (NumberFormatException ex) { System.out.println("GPA phải là số."); return; }
            if (!Utils.validateGpa(gpa)) { System.out.println("GPA không hợp lệ."); return; }

            String normName = Utils.normalizeName(name);
            SinhVien s = new SinhVien(id, normName, dob, major, gpa, cls);
            if (dao.exists(id)) { System.out.println("Mã đã tồn tại."); return; }
            if (dao.insertStudent(s)) System.out.println("Thêm thành công."); else System.out.println("Thêm thất bại.");

        } catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
    }

    private static void deleteStudent() {
        try {
            System.out.print("Nhập mã muốn xóa: ");
            String id = sc.nextLine().trim();
            if (dao.deleteStudent(id)) System.out.println("Xóa thành công."); else System.out.println("Xóa thất bại hoặc không tồn tại.");
        } catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
    }

    private static void editStudent() {
        try {
            System.out.print("Nhập mã muốn sửa: ");
            String id = sc.nextLine().trim();
            if (!dao.exists(id)) { System.out.println("Không tìm thấy mã."); return; }
            // lấy hiện tại
            List<SinhVien> all = dao.getAll();
            SinhVien cur = null;
            for (SinhVien s : all) if (s.getMaSinhVien().equals(id)) { cur = s; break; }
            if (cur == null) { System.out.println("Không tìm thấy dữ liệu hiện tại."); return; }

            System.out.println("Bấm Enter để giữ giá trị cũ.");
            System.out.print("Tên mới (cũ: " + cur.getHoTen() + "): ");
            String name = sc.nextLine().trim();
            System.out.print("Ngày sinh mới dd/MM/yyyy (cũ: " + cur.getNgaySinh().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "): ");
            String dobStr = sc.nextLine().trim();
            System.out.print("Ngành mới (cũ: " + cur.getNganhDaoTao() + "): ");
            String major = sc.nextLine().trim();
            System.out.print("GPA mới (cũ: " + cur.getDiemTrungBinh() + "): ");
            String gpaStr = sc.nextLine().trim();
            System.out.print("Lớp mới (cũ: " + cur.getLopSinhHoat() + "): ");
            String cls = sc.nextLine().trim();

            String newName = name.isEmpty() ? cur.getHoTen() : Utils.normalizeName(name);
            LocalDate newDob = dobStr.isEmpty() ? cur.getNgaySinh() : Utils.parseDate(dobStr);
            String newMajor = major.isEmpty() ? cur.getNganhDaoTao() : major.toUpperCase();
            double newGpa = gpaStr.isEmpty() ? cur.getDiemTrungBinh() : Double.parseDouble(gpaStr);
            String newClass = cls.isEmpty() ? cur.getLopSinhHoat() : cls;

            if (!Utils.validateMajor(newMajor)) { System.out.println("Ngành không hợp lệ."); return; }
            if (!Utils.validateStudentIdByMajor(id, newMajor)) { System.out.println("Mã không phù hợp ngành mới."); return; }
            if (!Utils.validateAge(newDob)) { System.out.println("Tuổi không hợp lệ."); return; }
            if (!Utils.validateGpa(newGpa)) { System.out.println("GPA không hợp lệ."); return; }

            SinhVien updated = new SinhVien(id, newName, newDob, newMajor, newGpa, newClass);
            if (dao.updateStudent(updated)) System.out.println("Cập nhật thành công."); else System.out.println("Cập nhật thất bại.");

        } catch (DateTimeParseException ex) { System.out.println("Ngày không đúng định dạng."); }
          catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
    }

    private static void printAll() {
        try { printList(dao.getAll()); } catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
    }

    private static void printByClass() {
        try { System.out.print("Nhập tên lớp: "); String cls = sc.nextLine().trim(); printList(dao.getAll(cls)); }
        catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
    }

    private static void printByMajor() {
        try { System.out.print("Ngành (CNTT/KTPM): "); String mj = sc.nextLine().trim().toUpperCase(); if (!Utils.validateMajor(mj)) { System.out.println("Ngành không hợp lệ."); return; } printList(dao.getByMajor(mj)); }
        catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
    }

    private static void printSortedByGpa() {
        try { printList(dao.getSortedByGpaDesc()); } catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
    }

    private static void printByBirthMonth() {
        try { System.out.print("Nhập tháng 1-12: "); int m = Integer.parseInt(sc.nextLine().trim()); printList(dao.getByBirthMonth(m)); }
        catch (NumberFormatException ex) { System.out.println("Tháng không hợp lệ."); }
        catch (Exception e) { System.out.println("Lỗi: " + e.getMessage()); }
    }

    private static void insertSamples() {
        try {
            SinhVien[] sample = new SinhVien[] {
                    new SinhVien("4551050024", Utils.normalizeName("nguyễn văn a"), LocalDate.of(2002,1,10), "CNTT", 8.5, "CNTT41A"),
                    new SinhVien("4551050034", Utils.normalizeName("trần thị b"), LocalDate.of(2001,2,5), "CNTT", 7.2, "CNTT41A"),
                    new SinhVien("4551090095", Utils.normalizeName("lê văn c"), LocalDate.of(2000,3,12), "KTPM", 6.9, "KTPM01"),
                    new SinhVien("4551090232", Utils.normalizeName("phạm thị d"), LocalDate.of(2003,4,9), "KTPM", 9.0, "KTPM02"),
                    new SinhVien("4551050666", Utils.normalizeName("hoàng anh e"), LocalDate.of(2002,5,22), "CNTT", 5.5, "CNTT42B"),
                    new SinhVien("4551090078", Utils.normalizeName("vũ thị f"), LocalDate.of(2001,6,30), "KTPM", 7.8, "KTPM01"),
                    new SinhVien("4551050625", Utils.normalizeName("đinh văn g"), LocalDate.of(2002,7,15), "CNTT", 8.1, "CNTT45B"),
                    new SinhVien("4551090123", Utils.normalizeName("ngô thị h"), LocalDate.of(2000,8,2), "KTPM", 6.0, "KTPM03"),
                    new SinhVien("4551050853", Utils.normalizeName("bùi văn i"), LocalDate.of(2003,9,18), "CNTT", 9.5, "CNTT47A"),
                    new SinhVien("4551090127", Utils.normalizeName("đoàn thị k"), LocalDate.of(2001,10,25), "KTPM", 4.9, "KTPM01")
            };
            int added = 0;
            for (SinhVien s : sample) {
                if (!dao.exists(s.getMaSinhVien())) { if (dao.insertStudent(s)) added++; }
            }
            System.out.println("Đã chèn " + added + " sinh viên mẫu.");
        } catch (Exception e) { System.out.println("Lỗi khi chèn mẫu: " + e.getMessage()); }
    }

    private static void printList(List<SinhVien> list) {
        if (list == null || list.isEmpty()) { 
        	System.out.println("Không có sinh viên."); 
        	return;
        }
        System.out.println("ID       | Họ tên                    | Ngày sinh       | Ngành đào tạo   | GPA  | Lớp");
        for (SinhVien s : list) 
        	System.out.println(s.toString());
    }
}
