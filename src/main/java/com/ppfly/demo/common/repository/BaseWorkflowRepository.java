package com.ppfly.demo.common.repository;

import com.ppfly.demo.common.entities.WorkflowInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 业务操作表Repository
 */
@Repository
public interface BaseWorkflowRepository extends JpaRepository<WorkflowInfo, Long> {

}
