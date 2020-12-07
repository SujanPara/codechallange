package com.sample.codechallange.service;

import com.sample.codechallange.entity.Enrollee;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface EnrolleeService extends Serializable {

    Optional<Enrollee> getEnrollee(Long id);

    List<Enrollee> getDependents(Long subscriberId);

    Enrollee saveOrUpdate(Enrollee enrollee);

    void delete(Long id);

    List<Enrollee> getAllPrimaryEnrollee();
}
