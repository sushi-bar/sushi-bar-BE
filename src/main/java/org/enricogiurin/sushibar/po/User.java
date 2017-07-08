package org.enricogiurin.sushibar.po;

/**
 * Created by enrico on 7/7/17.
 */
public class User {
    private String username;
    private String email;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


}
