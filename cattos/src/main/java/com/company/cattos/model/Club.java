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
@Entity(name = "club")
public class Club implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "avatar")
    private byte[] avatar;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Conversation> conversations;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> requests;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;

    @PrePersist
    private void autoSetUuid() {
        this.uuid = UUID.randomUUID();
    }

    public Club addMember(Member member) {
        if (members == null) {
            members = new ArrayList<>();
        }

        members.add(member);
        member.setClub(this);
        return this;
    }

    public Club addRequest(Request request) {
        if (requests == null) {
            requests = new ArrayList<>();
        }
        requests.add(request);
        request.setClub(this);
        return this;
    }

    public Club addEvents(Event event) {
        if (events == null) {
            events = new ArrayList<>();
        }

        events.add(event);
        event.setClub(this);
        return this;
    }

    public Club addConversation(Conversation conversation) {
        if (conversations == null) {
            conversations = new ArrayList<>();
        }
        conversations.add(conversation);
        conversation.setClub(this);
        return this;
    }

    public Club addArticle(Article article) {
        if (articles == null) {
            articles = new ArrayList<>();
        }
        articles.add(article);
        article.setClub(this);
        return this;
    }

    public void removeMember(Member member) {
        members.remove(member);
        member.setClub(null);
    }

    public void removeRequest(Request request) {
        requests.remove(request);
        request.setClub(null);
    }

    public void removeEvent(Event event) {
        events.remove(event);
        event.setClub(null);
    }

    public void removeConversation(Conversation conversation) {
        conversations.remove(conversation);
        conversation.setClub(null);
    }

    public void removeArticle(Article article) {
        articles.remove(article);
        article.setClub(null);
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
