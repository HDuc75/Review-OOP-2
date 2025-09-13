package model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SinhVien {
	    private String MaSinhVien;
	    private String HoTen;
	    private LocalDate NgaySinh;
	    private String NganhDaoTao; 
	    private double DiemTrungBinh;
	    private String LopSinhHoat;
	    
	    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		public SinhVien(String maSinhVien, String hoTen, LocalDate ngaySinh, String nganhDaoTao, double diemTrungBinh,
				String lopSinhHoat) {
			
			this.MaSinhVien = maSinhVien;
			this.HoTen = hoTen;
			this.NgaySinh = ngaySinh;
			this.NganhDaoTao = nganhDaoTao;
			this.DiemTrungBinh = diemTrungBinh;
			this.LopSinhHoat = lopSinhHoat;
		}

		public String getMaSinhVien() {
			return MaSinhVien;
		}

		public void setMaSinhVien(String maSinhVien) {
			this.MaSinhVien = maSinhVien;
		}

		public String getHoTen() {
			return HoTen;
		}

		public void setHoTen(String hoTen) {
			this.HoTen = hoTen;
		}

		public LocalDate getNgaySinh() {
			return NgaySinh;
		}

		public void setNgaySinh(LocalDate ngaySinh) {
			this.NgaySinh = ngaySinh;
		}

		public String getNganhDaoTao() {
			return NganhDaoTao;
		}

		public void setNganhDaoTao(String nganhDaoTao) {
			this.NganhDaoTao = nganhDaoTao;
		}

		public double getDiemTrungBinh() {
			return DiemTrungBinh;
		}

		public void setDiemTrungBinh(double diemTrungBinh) {
			this.DiemTrungBinh = diemTrungBinh;
		}

		public String getLopSinhHoat() {
			return LopSinhHoat;
		}

		public void setLopSinhHoat(String lopSinhHoat) {
			this.LopSinhHoat = lopSinhHoat;
		}
		
		@Override
	    public String toString() {
	        return String.format("%s --- %s --- %s --- %s --- %.2f --- %s", MaSinhVien, HoTen, NgaySinh.format(DATE_FORMAT), NganhDaoTao, DiemTrungBinh, LopSinhHoat);
	    }
		
       
}
