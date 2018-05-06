package org.enricogiurin.sushibar.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * Created by enrico on 7/7/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    @NotNull
    private String username;
    @NotNull
    private String email;

}
