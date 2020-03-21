package de.awacademy.ourblog.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PostDTO {

    @Size(min = 1, max = 9999, message = "The length must be between 1 and 9999 signs")
    @NotBlank (message = "The field cannot be empty")
    private String text;

    @NotBlank (message = "The field cannot be empty")
    private String title;

    private String imageUrl;

    private long postId;

    public PostDTO(){}

    public PostDTO(String title, String text) {
        this.title = title;
        this.text = text;
        this.imageUrl = null;
    }

    public PostDTO(String title, String text, String imageUrl) {
        this.title = title;
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

}