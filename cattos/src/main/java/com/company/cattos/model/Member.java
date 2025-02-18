package com.company.cattos.model;

import com.company.cattos.enums.Role;
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
@Entity(name = "member")
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    private UUID uuid;

    @Column(name = "member_username")
    private String username;


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "member")
    private List<Message> messages;

    @OneToMany(mappedBy = "writer")
    private List<Article> articles;

    @ManyToMany(mappedBy = "members")
    private List<Conversation> conversations;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToMany
    @JoinTable(name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    private List<Event> events;

    @ManyToOne
    @JoinTable(name = "user_members")
    private User user;


    @PrePersist
    private void autoSetUuid() {
        this.uuid = UUID.randomUUID();
    }

    public Member addMessage(Message message) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        message.setMember(this);
        return this;
    }

    public Member addArticle(Article article) {
        if (articles == null) {
            articles = new ArrayList<>();
        }
        articles.add(article);
        article.setWriter(this);
        return this;
    }

    public void removeMessage(Message message) {
        getMessages().remove(message);
        message.setMember(null);
    }

    public void removeArticle(Article article) {
        getArticles().remove(article);
        article.setWriter(null);
    }

    public Member addConversation(Conversation conversation) {
        this.conversations.add(conversation);
        conversation.getMembers().add(this);
        return this;
    }

    public void removeConversation(Conversation conversation) {
        this.conversations.remove(conversation);
        conversation.getMembers().remove(this);
    }

    public Member addEvent(Event event) {
        this.events.add(event);
        event.getParticipants().add(this);
        return this;
    }

    public void removeEvent(Event event) {
        this.events.remove(event);
        event.getParticipants().remove(this);
    }

    public void setMappedClub(Club club) {
        club.getMembers().add(this);
        this.setClub(club);
    }

    public void setMappedUser(User user) {
        user.getMembers().add(this);
        this.setUser(user);
    }

    public void removeMappedClub(Club club) {
        club.getMembers().remove(this);
        this.setClub(null);
    }

    public void removeMappedUser(User user) {
        user.getMembers().remove(this);
        this.setUser(null);
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
