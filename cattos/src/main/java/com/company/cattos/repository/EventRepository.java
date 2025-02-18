package com.company.cattos.repository;

import com.company.cattos.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    void deleteByUuid(UUID uuid);
    Event findByUuid(UUID uuid);
}
