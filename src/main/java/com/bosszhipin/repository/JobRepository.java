package com.bosszhipin.repository;

import com.bosszhipin.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    Page<Job> findByIsActiveOrderByCreatedAtDesc(Integer isActive, Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.isActive = 1 AND j.isHot = 1 ORDER BY j.viewCount DESC")
    List<Job> findHotJobs(Pageable pageable);
}
