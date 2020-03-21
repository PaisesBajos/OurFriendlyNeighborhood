package de.awacademy.ourblog.task;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TaskDTO {

    @Size(min = 1, max = 9999, message = "The length must be between 1 and 9999 signs")
    @NotBlank (message = "The field cannot be empty")
    private String text;

    @NotBlank (message = "The field cannot be empty")
    private String title;

    private long taskId;

    public TaskDTO(){}

    public TaskDTO(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public TaskDTO(String title, String text, String imageUrl) {
        this.title = title;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
