package com.company.cattos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.io.*;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Entity(name = "message")
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "uuid",columnDefinition = "VARCHAR(36)")
    private UUID uuid;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_date")
    private Date sentDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @PrePersist
    private void autoSetUuid(){
        this.uuid = UUID.randomUUID();
    }

    public void setMappedConversation(Conversation conversation){
        conversation.getMessages().add(this);
        this.setConversation(conversation);
    }

    public void setMappedMember(Member member){
        member.getMessages().add(this);
        this.setMember(member);
    }

    public void removeMappedConversation(Conversation conversation){
        conversation.getMessages().remove(this);
        this.setConversation(null);
    }

    public void removeMappedMember(Member member){
        member.getMessages().remove(this);
        this.setMember(null);
    }

    @Serial
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }
}
