package de.awacademy.ourblog.user;

import de.awacademy.ourblog.session.Session;
import de.awacademy.ourblog.task.Task;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    private String username;
    private String password;

    private String firstName;

    private String lastName;

    private String location;

    private boolean isHelper;

    private boolean isAdmin;

    @OneToMany(mappedBy = "user")
    private List<Session> sessionList;

    @OneToMany(mappedBy = "requestUser")
    private List<Task> requestedTaskList;

    @OneToMany(mappedBy = "helpUser")
    private List<Task> helperTaskList;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAdmin = false;
        this.isHelper = true;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }



    public boolean isHelper() {
        return isHelper;
    }

    public void setHelper(boolean helper) {
        isHelper = helper;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public List<Task> getRequestedTaskList() {
        return requestedTaskList;
    }

    public void setRequestedTaskList(List<Task> requestedTaskList) {
        this.requestedTaskList = requestedTaskList;
    }

    public List<Task> getHelperTaskList() {
        return helperTaskList;
    }

    public void setHelperTaskList(List<Task> helperTaskList) {
        this.helperTaskList = helperTaskList;
    }
}
