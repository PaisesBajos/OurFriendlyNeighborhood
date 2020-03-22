package de.awacademy.ourblog.task;


import de.awacademy.ourblog.user.User;
import de.awacademy.ourblog.utils.AddressForGPS;
import de.awacademy.ourblog.utils.Adress;
import de.awacademy.ourblog.utils.DateTimeConverter;
import de.awacademy.ourblog.utils.GetGPSfromApi;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.hibernate.annotations.CascadeType.DELETE;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User requestUser;

    @ManyToOne
    private User helpUser;

    private String title;

    @Column(length = 9999)
    private String text;

    private boolean isAccepted;

    private Instant createdAt;

    private Instant dueDate;

    public Task() {
    }

    public Task(User requestUser, User helpUser, String title, String text, Instant createdAt, boolean isAccepted, Instant dueDate) {
        this.requestUser = requestUser;
        this.helpUser = helpUser;
        this.text = text;
        this.title = title;
        this.isAccepted = isAccepted;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
    }

    public double getDistance(User sessionuser){
        return Math.round(GetGPSfromApi.distanceBetweenAddresses(sessionuser.getAdressUser(),requestUser.getAdressUser())*10)/10.0;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(User requestUser) {
        this.requestUser = requestUser;
    }

    public User getHelpUser() {
        return helpUser;
    }

    public void setHelpUser(User helpUser) {
        this.helpUser = helpUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public String getPostedAtFormatted() {
        return DateTimeConverter.getConvertedDateTime(createdAt);
    }

    public String getDueDateFormatted() {
        return DateTimeConverter.getConvertedDateTime(dueDate);
    }

}
