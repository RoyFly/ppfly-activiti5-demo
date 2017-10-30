package com.ppfly.demo.common.repository;

import com.ppfly.demo.common.entities.BusinessOper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 业务操作表Repository
 */
@Repository
public interface BusinessOperRepository extends JpaRepository<BusinessOper, Long> {

    List<BusinessOper> getAllByBusinessId(Long businessId);
}
