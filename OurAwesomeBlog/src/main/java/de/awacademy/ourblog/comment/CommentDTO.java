package de.awacademy.ourblog.comment;

import javax.validation.constraints.Size;

public class CommentDTO {

    private long commentId;

    @Size(min = 1, max = 99)
    private String text;

    public CommentDTO(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getCommentId() {
        return commentId;
    }
}