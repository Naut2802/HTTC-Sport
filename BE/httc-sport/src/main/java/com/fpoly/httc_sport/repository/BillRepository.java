package com.fpoly.httc_sport.repository;

import com.fpoly.httc_sport.entity.Bill;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BillRepository extends JpaRepository<Bill, Long> {
	List<Bill> findByUserId(String userId);
	Page<Bill> findByUserId(String userId, Pageable pageable);
	Page<Bill> findByPitchId(Long pitchId, Pageable pageable);
	Page<Bill> findByRentedAtBetween(LocalDate date1, LocalDate date2, Pageable pageable);
	Page<Bill> findByUserIdAndRentedAtBetween(String userId, LocalDate date1, LocalDate date2, Pageable pageable);
	Page<Bill> findByIsRateTrue(Pageable pageable);
	Page<Bill> findByUserIdAndIsRateTrue(String userId, Pageable pageable);
	
//	@Query("SELECT new Report(o.pitch, SUM(o.total), MONTH(o.createdAt)) FROM Bill o GROUP BY o.pitch")
//    List<Report> revenueReport();
//
//	@Query("SELECT new ReportForYear(SUM(o.total), YEAR(o.createdAt)) "
//			+ "FROM Bill o ")
//    List<ReportForYear> revenueReportYear();
//
//	@Query("SELECT DISTINCT YEAR(o.createdAt) FROM Bill o WHERE YEAR(o.createdAt) = YEAR(CURRENT_DATE)")
//	List<Integer> findDistinctCurrentYear();
//
//	@Query("SELECT DISTINCT YEAR(o.createdAt) FROM Bill o")
//	List<Integer> findAllYear();
//
//	@Query("SELECT o FROM Bill o WHERE YEAR(o.createdAt) = ?1")
//	Page<Bill> findAllByYearLike(Integer yr, Pageable pageable);
//
//	@Query("SELECT o FROM Bill o WHERE o.createdAt BETWEEN ?1 AND ?2")
//	List<Bill> findBetweenByDate(LocalDate startDate, LocalDate endDate);
//
//	@Query("SELECT o FROM Bill o WHERE MONTH(o.createdAt) = MONTH(CURRENT_DATE) AND YEAR(o.createdAt) = YEAR(CURRENT_DATE)")
//	List<Bill> findByMonthNow();
	
//	@Query("SELECT SUM(o.tongTien) AS tongTien, MONTH(o.ngayXuat) AS thang FROM HoaDon o GROUP BY MONTH(o.ngayXuat)")
//	List<HoaDon> getTongTienByMonth();
}
