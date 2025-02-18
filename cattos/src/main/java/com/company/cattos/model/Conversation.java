package com.company.cattos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Entity(name = "conversation")
public class Conversation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @ManyToOne
    private Club club;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    @ManyToMany
    @JoinTable(name = "conversation_member",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<Member> members;

    @PrePersist
    private void autoSetUuid() {
        this.uuid = UUID.randomUUID();
    }

    public Conversation addMessage(Message message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }

        getMessages().add(message);
        message.setConversation(this);
        return this;
    }

    public void removeMessage(Message message) {
        getMessages().remove(message);
        message.setConversation(null);
    }

    public Conversation addMember(Member member){
        this.members.add(member);
        member.getConversations().add(this);
        return this;
    }
    public void removeMember(Member member){
        this.members.remove(member);
        member.getConversations().remove(this);
    }

    public void setMappedClub(Club club){
        club.getConversations().add(this);
        this.setClub(club);
    }

    public void removeMappedClub(Club club){
        club.getConversations().remove(this);
        this.setClub(null);
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



