package com.utsav.cardservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.utsav.cardservice.model.CreditCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class AddCardDto {

    @JsonProperty(required = true)
    @NotEmpty
    @NotBlank
    private String name;

    @JsonProperty(required = true)
    @NotBlank
    @NotEmpty
    @Size(max = 19)
    private String cardNumber;

    @JsonProperty(required = true)
    @Positive
    private Integer limit;
}
