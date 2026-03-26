package com.portfolio.repository;

import com.portfolio.entity.ProjectChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectChallengeRepository extends JpaRepository<ProjectChallenge, Long> {

    List<ProjectChallenge> findAllByOrderBySortOrderAscIdAsc();
}
