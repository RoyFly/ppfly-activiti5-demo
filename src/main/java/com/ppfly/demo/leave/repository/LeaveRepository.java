package com.ppfly.demo.leave.repository;

import com.ppfly.demo.leave.entities.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 请假流程Repository
 */
@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

}
