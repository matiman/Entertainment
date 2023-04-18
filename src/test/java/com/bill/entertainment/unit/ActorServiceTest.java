package com.bill.entertainment.unit;

import com.bill.entertainment.dao.ActorRepository;
import com.bill.entertainment.dao.MovieRepository;
import com.bill.entertainment.entity.Actor;
import com.bill.entertainment.entity.Movie;
import com.bill.entertainment.exception.CreativesDeletionException;
import com.bill.entertainment.exception.CreativesValidationException;
import com.bill.entertainment.service.ActorService;
import com.bill.entertainment.service.MovieService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ActorServiceTest {

    @InjectMocks
    private ActorService actorService;

    @Mock
    private ActorRepository repository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateActorWithExistingName() {
        Actor existingActor = new Actor();
        existingActor.setId(1L);
        existingActor.setName("Emma Lincoln");

        when(repository.findByName("Emma Lincoln")).thenReturn(existingActor);

        Actor newActor = new Actor();
        newActor.setName("Emma Lincoln");

        assertThrows(CreativesValidationException.class, () -> actorService.create(newActor),
                "Should throw CreativesValidationException.");
    }
}
