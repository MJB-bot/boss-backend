package com.bosszhipin.repository;

import com.bosszhipin.entity.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {

    List<WorkExperience> findByUserIdOrderBySortOrderAscStartDateDesc(Long userId);
}
