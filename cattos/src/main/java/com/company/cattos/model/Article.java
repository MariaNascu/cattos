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
@Entity(name = "article")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    private UUID uuid;

    @Column(name = "date")
    private Date date;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private Club club;

    @ManyToOne
    private Member writer;

    @PrePersist
    private void autoSetUuid() {
        this.uuid = UUID.randomUUID();
    }

    public void setMappedClub(Club club){
        club.getArticles().add(this);
        this.setClub(club);
    }

    public void removeMappedClub(Club club){
        club.getArticles().remove(this);
        this.setClub(null);
    }

    public void setMappedMember(Member member){
        member.getArticles().add(this);
        this.setWriter(member);
    }

    public void removeMappedMember(Member member){
        member.getArticles().remove(this);
        this.setWriter(null);
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
