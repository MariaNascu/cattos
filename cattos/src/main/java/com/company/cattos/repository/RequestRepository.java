package com.company.cattos.repository;

import com.company.cattos.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    Request findRequestByUuid(UUID uuid);

}
