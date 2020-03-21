//package de.awacademy.ourblog.comment;
//
//import de.awacademy.ourblog.post.Post;
//import de.awacademy.ourblog.user.User;
//import de.awacademy.ourblog.utils.DateTimeConverter;
//
//import javax.persistence.*;
//import java.time.Instant;
//import java.util.List;
//
//@Entity
//public class Comment {
//
//    @Id
//    @GeneratedValue
//    private long id;
//
//    @ManyToOne
//    private User user;
//
//    private String text;
//
//    private Instant postedAt;
//
//    @ManyToOne
//    private Post post;
//
//    public Comment() {
//    }
//
//    public Comment(User user, String text, Instant postedAt, Post post) {
//        this.user = user;
//        this.text = text;
//        this.postedAt = postedAt;
//        this.post = post;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public Instant getPostedAt() {
//        return postedAt;
//    }
//
//    public void setPostedAt(Instant postedAt) {
//        this.postedAt = postedAt;
//    }
//
//    public Post getPost() {
//        return post;
//    }
//
//    public void setPost(Post post) {
//        this.post = post;
//    }
//
//    public String getPostedAtFormatted() {
//        return DateTimeConverter.getConvertedDateTime(postedAt);
//    }
//}
