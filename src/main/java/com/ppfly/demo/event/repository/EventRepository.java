package com.ppfly.demo.event.repository;

import com.ppfly.demo.event.entities.EventPO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ppfly on 2017/9/22.
 */
public interface EventRepository extends JpaRepository<EventPO, Long> {
}
