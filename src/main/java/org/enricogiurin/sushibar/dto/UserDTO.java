package org.enricogiurin.sushibar.dto;

import lombok.*;

import javax.validation.constraints.NotNull;


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
