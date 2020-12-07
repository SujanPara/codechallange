package com.sample.codechallange.controller;

import com.sample.codechallange.entity.Enrollee;
import com.sample.codechallange.service.EnrolleeService;
import com.sample.codechallange.service.SaveDependentsRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EnrolleeRestController.BASE_PATH)
public class EnrolleeRestController {

    static final String BASE_PATH = "/v1/enrollee";

    private EnrolleeService enrolleeService;

    @Autowired
    public EnrolleeRestController(EnrolleeService enrolleeService) {
        this.enrolleeService = enrolleeService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Enrollee> getEnrollee(@RequestParam("id") Long id) {
        final Optional<Enrollee> enrollee = enrolleeService.getEnrollee(id);
        if (enrollee.isPresent()) {
            return ResponseEntity.ok(enrollee.get());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value="/subscribers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Enrollee>> getAllSubscriber() {
        return ResponseEntity.ok(enrolleeService.getAllPrimaryEnrollee());
    }

    @GetMapping(value = "/dependents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Enrollee>> getDependents(@RequestParam("subscriberId") Long subscriberId) {
        return ResponseEntity.ok(enrolleeService.getDependents(subscriberId));
    }

    @PostMapping(value = "/saveEnrollee")
    @ResponseBody
    public ResponseEntity<Enrollee> saveEnrollee(@RequestBody Enrollee enrollee) {
        Enrollee result = enrolleeService.saveOrUpdate(enrollee);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/saveDependents")
    @ResponseBody
    public ResponseEntity<List<Enrollee>> saveDependents(@RequestBody SaveDependentsRequest request) {
        final Optional<Enrollee> subscriber = enrolleeService.getEnrollee(request.getSubscriberId());
        List<Enrollee> responses = new ArrayList<>();
        if (subscriber.isPresent()) {
            for (final Enrollee dependent : request.getDependents()) {
                dependent.setSubscriber(subscriber.get());
                Enrollee result = enrolleeService.saveOrUpdate(dependent);
                if (result != null) {
                    responses.add(result);
                }
            }
            return ResponseEntity.ok(responses);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/delete")
    public ResponseEntity<Enrollee> deleteEnrollee(@RequestParam("id") Long id) {
        try {
            enrolleeService.delete(id);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
