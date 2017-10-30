package com.ppfly.demo.dailyreports.repository;

import com.ppfly.demo.dailyreports.entites.DailyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 日报Repository
 * Created by ppfly on 2017/10/9.
 */
@Repository
public interface DailyReportsRepository extends JpaRepository<DailyReports, Long> {
}
