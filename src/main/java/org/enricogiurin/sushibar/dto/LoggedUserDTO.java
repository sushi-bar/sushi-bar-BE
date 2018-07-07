package org.enricogiurin.sushibar.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class LoggedUserDTO extends User {


    public LoggedUserDTO(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public LoggedUserDTO(UserDetails userDetails) {
        this(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }

    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }
}
