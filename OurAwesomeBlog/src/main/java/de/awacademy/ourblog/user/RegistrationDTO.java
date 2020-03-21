package de.awacademy.ourblog.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationDTO {

    @NotEmpty(message = "The field cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "The Password can only contain letters, numbers and the underscore")
    private String username;

    @Size(min = 5, message = "The field must contain at least 5 characters")
    private String password1;
    private String password2;

    public RegistrationDTO(String username, String password1, String password2) {
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }
}