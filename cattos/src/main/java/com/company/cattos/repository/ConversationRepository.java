package com.company.cattos.repository;

import com.company.cattos.model.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    Conversation findConversationByUuid(UUID conversationUuid);

    @Query(value = "SELECT DISTINCT c FROM conversation c " +
            "JOIN c.club clb " +
            "JOIN c.members m " +
            "WHERE clb.uuid = :clubUuid " +
            "AND m.uuid = :memberUuid")
    List<Conversation> findAllConversationsByClubUuidAndMemberUuid(@Param("memberUuid") UUID memberUuid, @Param("clubUuid") UUID clubUuid);
}
