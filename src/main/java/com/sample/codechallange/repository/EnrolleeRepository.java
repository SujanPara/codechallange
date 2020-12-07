package com.sample.codechallange.repository;

import com.sample.codechallange.entity.Enrollee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrolleeRepository extends JpaRepository<Enrollee, Long> {

    @Query(value = "SELECT * FROM enrollee u WHERE u.subscriber_id<=>:subscriberId ", nativeQuery = true)
    List<Enrollee> findBySubscriber(Long subscriberId);

}
