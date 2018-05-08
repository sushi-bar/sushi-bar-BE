package org.enricogiurin.sushibar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;


@Getter
@Setter
@Builder
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

    @JsonIgnore
    private String confirmationCode;

    private Boolean confirmed;

    @JsonIgnore
    private String password;

    private String role;

    @Tolerate
    public User() {}

}
