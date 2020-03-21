package de.awacademy.ourblog.session;

import de.awacademy.ourblog.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Session {

    @Id
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    private User user;

    private Instant expiresAt;

    public Session() {
    }

    public Session(User user, Instant expiresAt) {
        this.user = user;
        this.expiresAt = expiresAt;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}