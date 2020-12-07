package com.sample.codechallange.service;

import java.util.List;
import java.util.Optional;

import com.sample.codechallange.entity.Enrollee;
import com.sample.codechallange.repository.EnrolleeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnrolleeServiceImpl implements EnrolleeService {

    @Autowired
    EnrolleeRepository enrolleeRepository;

    @Override
    public Optional<Enrollee> getEnrollee(Long id) {
        return enrolleeRepository.findById(id);
    }

    @Override
    public List<Enrollee> getDependents(Long subscriberId) {
        return enrolleeRepository.findBySubscriber(subscriberId);
    }

    @Override
    @Transactional
    public Enrollee saveOrUpdate(Enrollee enrollee) {
        final Enrollee savedEnrollee = enrolleeRepository.saveAndFlush(enrollee);
        for (final Enrollee dependent : enrollee.getDependents()) {
            dependent.setSubscriber(savedEnrollee);
            enrolleeRepository.saveAndFlush(dependent);
        }
        return savedEnrollee;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Enrollee> enrollee = enrolleeRepository.findById(id);
        if (enrollee.isPresent()) {
            for (final Enrollee dependent : enrollee.get().getDependents()) {
                enrolleeRepository.delete(dependent);
            }
            enrolleeRepository.delete(enrollee.get());
        }
    }

    @Override
    public List<Enrollee> getAllPrimaryEnrollee() {
        return enrolleeRepository.findBySubscriber(null);
    }

}