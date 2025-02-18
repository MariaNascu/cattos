package com.company.cattos.repository;

import com.company.cattos.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ClubRepository extends JpaRepository<Club, Integer> {

    Club findClubByUuid(UUID uuid);


}
