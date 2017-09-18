package org.enricogiurin.sushibar.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by enrico on 7/24/17.
 */
public class RequestUserDTO extends UserDTO {
    @NotNull
    private String password = null;

    public RequestUserDTO(String username, String email, String password) {
        super(username, email);
        this.password = password;
    }

    public RequestUserDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
