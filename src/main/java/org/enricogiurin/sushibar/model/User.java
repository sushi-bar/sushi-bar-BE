package org.enricogiurin.sushibar.model;

import javax.persistence.*;


/**
 * Created by enrico on 2/27/17.
 */
@Entity
@Table(name = "SB_User")
@NamedQueries(value = {@NamedQuery(name = "User.activeUsers", query =
        "from User u where u.enabled is true order by u.username asc")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String email;

    private Boolean enabled;

    private String confirmationCode;

    private Boolean confirmed;

    private String password;

    //JPA constructor
    public User(){}

    public User(String username, String email, boolean enabled, String password) {
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
