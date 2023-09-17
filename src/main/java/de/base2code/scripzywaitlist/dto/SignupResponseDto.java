package de.base2code.scripzywaitlist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
@lombok.Builder
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class SignupResponseDto {
    @JsonProperty("data")
    private Object data;

    @JsonProperty("message")
    private String message;
}
