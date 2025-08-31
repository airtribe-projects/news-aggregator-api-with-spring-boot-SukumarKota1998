package com.airtribe.news_aggregator.repository;

import com.airtribe.news_aggregator.entity.UserReads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReadsRepository extends JpaRepository<UserReads, Long> {
}
