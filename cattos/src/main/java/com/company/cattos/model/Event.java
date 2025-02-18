package com.company.cattos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Entity(name = "event")
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    private UUID uuid;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "location")
    private String location;

    @Column(name = "creator_id")
    private UUID creatorId;

    @ManyToMany(mappedBy = "events")
    private List<Member> participants;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @PrePersist
    private void autoSetUuid() {
        this.uuid = UUID.randomUUID();
    }

    public Event addMember(Member member) {
        this.participants.add(member);
        member.getEvents().add(this);
        return this;
    }
    public void removeMember(Member member){
        this.participants.remove(member);
        member.getEvents().remove(this);
    }

    public void setMappedClub(Club club){
        club.getEvents().add(this);
        this.setClub(club);
    }

    public void removeMappedClub(Club club){
        club.getEvents().remove(this);
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


