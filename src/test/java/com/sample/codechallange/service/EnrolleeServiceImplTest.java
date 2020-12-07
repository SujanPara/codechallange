package com.sample.codechallange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.sample.codechallange.entity.Enrollee;
import com.sample.codechallange.repository.EnrolleeRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = {EnrolleeServiceImpl.class,
        JacksonAutoConfiguration.class})
public class EnrolleeServiceImplTest {
    @InjectMocks
    EnrolleeServiceImpl enrolleeService;

    @Mock
    private EnrolleeRepository enrolleeRepository;

    @Test
    public void testGetEnrollee() {

        final Enrollee enrollee = buildEnrollee(1L, "Sujan Parajuli");
        when(enrolleeRepository.findById(1L)).thenReturn(Optional.of(enrollee));

        Optional<Enrollee> result = enrolleeService.getEnrollee(1L);
        assertEquals(result.get().getName(), "Sujan Parajuli");
    }

    @Test
    public void testGetDependents() {
        final List<Enrollee> enrollees = Arrays.asList(buildEnrollee(2L, "Sujan Parajuli"));

        when(enrolleeRepository.findBySubscriber(1L)).thenReturn(enrollees);

        List<Enrollee> results = enrolleeRepository.findBySubscriber(1L);
        assertEquals(results.size(), 1);

    }

    private Enrollee buildEnrollee(Long id, String name) {
        return Enrollee.builder()
                .id(id)
                .name(name)
                .build();
    }


}

