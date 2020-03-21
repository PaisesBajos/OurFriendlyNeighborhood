package de.awacademy.ourblog.post;

import de.awacademy.ourblog.comment.Comment;
import de.awacademy.ourblog.user.User;
import de.awacademy.ourblog.utils.DateTimeConverter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

import static org.hibernate.annotations.CascadeType.DELETE;

@Entity
public class Post {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User user;

    private String title;

    @Column(length = 9999)
    private String text;

    private Instant postedAt;

    private String urlToImage;

    @OneToMany(mappedBy = "post")
    @Cascade(DELETE)
    private List<Comment> commentList;

    public Post() {
    }

    public Post(User user, String title, String text, Instant postedAt) {
        this.user = user;
        this.text = text;
        this.title = title;
        this.postedAt = postedAt;
        this.urlToImage = null;
    }

    @Transient
    public List<Comment> getCommentlistAsc() {
        List<Comment> returnList = commentList;
        returnList.sort((comment, t1) -> {
            if (comment.getPostedAt().isBefore(t1.getPostedAt())) {
                return -1;
            } else if (comment.getPostedAt().isAfter(t1.getPostedAt())) {
                return 1;
            }
            return 0;
        });
        return returnList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Instant postedAt) {
        this.postedAt = postedAt;
    }

    public List<Comment> getCommentList() {
        return getCommentlistAsc();
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getPostedAtFormatted() {
        return DateTimeConverter.getConvertedDateTime(postedAt);
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}