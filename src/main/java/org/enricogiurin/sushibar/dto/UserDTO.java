package org.enricogiurin.sushibar.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by enrico on 7/7/17.
 */
public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String email;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
