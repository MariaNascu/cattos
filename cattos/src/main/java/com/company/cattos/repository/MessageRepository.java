package com.company.cattos.repository;

import com.company.cattos.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, Integer> {
Message findMessageByUuid(UUID messageUuid);
}
