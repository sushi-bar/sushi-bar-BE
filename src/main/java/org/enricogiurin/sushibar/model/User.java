package org.enricogiurin.sushibar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Collection;


@Getter
@Setter
@Builder
@Entity
@Table(name = "sb_user")
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

    @Tolerate
    public User() {}

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles = Lists.newArrayList();

}
