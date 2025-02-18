package com.company.cattos.model;

import com.company.cattos.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.io.*;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Entity(name = "request")
public class Request implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    private UUID uuid;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    private Club club;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @PrePersist
    private void autoSetUuid() {
        this.uuid = UUID.randomUUID();
    }

    public void setMappedClub(Club club) {
        club.getRequests().add(this);
        this.setClub(club);
    }
    public void setMappedUser(User user) {
        user.getRequests().add(this);
        this.setUser(user);
    }

    public void removeMappedClub(Club club) {
        club.getRequests().remove(this);
        this.setClub(null);
    }
    public void removeMappedUser(User user) {
        user.getRequests().remove(this);
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
