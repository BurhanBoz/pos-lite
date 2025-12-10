package com.example.sales.repository;

import com.example.sales.entity.SaleEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleEntryRepository extends JpaRepository<SaleEntryEntity, Long> {
    @Query("""
    SELECT se FROM SaleEntryEntity se
    WHERE DATE(se.saleDatetime) = CURRENT_DATE
    ORDER BY se.saleDatetime DESC
  """)
    List<SaleEntryEntity> findToday();

    @Query("""
    SELECT COALESCE(SUM(se.unitPriceSnapshot*se.quantity),0),
           COALESCE(SUM(se.unitCostSnapshot*se.quantity),0)
    FROM SaleEntryEntity se
    WHERE DATE(se.saleDatetime) = :date
  """)
    Object[] sumForDate(@Param("date") LocalDate date);

    @Query("""
  SELECT COALESCE(SUM(se.unitPriceSnapshot * se.quantity), 0) as revenue,
         COALESCE(SUM(se.unitCostSnapshot  * se.quantity), 0) as cost
  FROM SaleEntryEntity se
  WHERE se.saleDatetime >= :start AND se.saleDatetime < :end
""")
    DaySummaryView sumForRange(@Param("start") LocalDateTime start,
                             @Param("end")   LocalDateTime end);

    // com.example.sales.repository.SaleEntryRepository.java
    @Query("""
  SELECT se FROM SaleEntryEntity se
  WHERE se.saleDatetime >= :start AND se.saleDatetime < :end
  ORDER BY se.saleDatetime DESC
""")
    List<SaleEntryEntity> findForRange(@Param("start") LocalDateTime start,
                                 @Param("end")   LocalDateTime end);

}
