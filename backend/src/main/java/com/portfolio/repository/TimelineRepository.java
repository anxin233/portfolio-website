package com.portfolio.repository;

import com.portfolio.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findAllByOrderBySortOrderAscIdAsc();
}
