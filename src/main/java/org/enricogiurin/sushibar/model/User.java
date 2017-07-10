package org.enricogiurin.sushibar.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


/**
 * Created by enrico on 2/27/17.
 */
@Entity
@Table(name = "SB_User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String email;

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




}
