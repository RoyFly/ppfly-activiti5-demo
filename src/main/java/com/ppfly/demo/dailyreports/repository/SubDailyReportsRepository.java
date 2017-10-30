package com.ppfly.demo.dailyreports.repository;

import com.ppfly.demo.dailyreports.entites.DailyReports;
import com.ppfly.demo.dailyreports.entites.SubDailyReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 日报子流程Repository
 * Created by ppfly on 2017/10/9.
 */
@Repository
public interface SubDailyReportsRepository extends JpaRepository<SubDailyReports, Long> {
}
