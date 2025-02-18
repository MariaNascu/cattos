package com.company.cattos.repository;

import com.company.cattos.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer> {
    Member findByUuid(UUID uuid);
    Member findByUsername(String username);
}
