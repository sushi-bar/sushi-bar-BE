package org.enricogiurin.sushibar.model;

import javax.persistence.*;
import java.util.Collection;


/**
 * Created by enrico on 2/27/17.
 */
@Entity
@Table(name = "SBUser")
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    //JPA constructor
    public User(){}

    public User(String username, String email, String password, String confirmationCode, Boolean enabled, Boolean confirmed) {
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.confirmationCode = confirmationCode;
        this.confirmed = confirmed;
        this.password = password;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean isEnabled() {
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


    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }



}
