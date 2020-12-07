package com.sample.codechallange.service;

import com.sample.codechallange.entity.Enrollee;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveDependentsRequest {

    @JsonProperty(required = true)
    private List<Enrollee> dependents;

    @JsonProperty(required = true)
    private Long subscriberId;

}
