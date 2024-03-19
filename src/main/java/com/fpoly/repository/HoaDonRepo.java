package com.fpoly.repository;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpoly.model.HoaDon;
import com.fpoly.model.Report;
import com.fpoly.model.ReportForYear;


public interface HoaDonRepo extends JpaRepository<HoaDon, Long> {
	List<HoaDon> findByUserUsername(String username);
	
	@Query("SELECT new Report(o.san, SUM(o.tongTien), MONTH(o.ngayXuat)) FROM HoaDon o GROUP BY o.san")
    List<Report> revenueReport();
	
	@Query("SELECT new ReportForYear(SUM(o.tongTien), YEAR(o.ngayXuat)) "
			+ "FROM HoaDon o ")
    List<ReportForYear> revenueReportYear();
	
	@Query("SELECT DISTINCT YEAR(o.ngayXuat) FROM HoaDon o WHERE YEAR(o.ngayXuat) = YEAR(CURRENT_DATE)")
	List<Integer> findDistinctCurrentYear();
	
	@Query("SELECT DISTINCT YEAR(o.ngayXuat) FROM HoaDon o")
	List<Integer> findAllYear();
	
	@Query("SELECT o FROM HoaDon o WHERE YEAR(o.ngayXuat) = ?1")
	Page<HoaDon> findAllByYearLike(Integer yr, Pageable pageable);
	
	@Query("SELECT o FROM HoaDon o WHERE o.ngayXuat BETWEEN ?1 AND ?2")
	List<HoaDon> findBetweenByDate(LocalDate startDate, LocalDate endDate);
	
	@Query("SELECT o FROM HoaDon o WHERE MONTH(o.ngayXuat) = MONTH(CURRENT_DATE) AND YEAR(o.ngayXuat) = YEAR(CURRENT_DATE)")
	List<HoaDon> findByMonthNow();
	
//	@Query("SELECT SUM(o.tongTien) AS tongTien, MONTH(o.ngayXuat) AS thang FROM HoaDon o GROUP BY MONTH(o.ngayXuat)")
//	List<HoaDon> getTongTienByMonth();
}
