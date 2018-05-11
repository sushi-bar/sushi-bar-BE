package org.enricogiurin.sushibar.dto;

import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

}
