package com.company.cattos.model;

import com.company.cattos.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "cattos_user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    private UUID uuid;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private List<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Request> requests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Member> members;


    @PrePersist
    private void autoSetUuid() {
        this.uuid = UUID.randomUUID();
    }

    public User addMember(Member member) {
        if (members == null) {
            members = new ArrayList<>();
        }
        getMembers().add(member);
        member.setUser(this);
        return this;
    }

    public User addRequest(Request request) {
        if (requests == null) {
            requests = new ArrayList<>();
        }
        getRequests().add(request);
        request.setUser(this);
        return this;
    }

    public void removeMember(Member member) {
        getMembers().remove(member);
        member.setUser(null);
    }

    public void removeRequest(Request request) {
        getRequests().remove(request);
        request.setUser(null);


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
