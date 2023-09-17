package de.base2code.scripzywaitlist.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecaptchaResponse {

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("action")
    private String action;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("score")
    private double score;

    @JsonProperty("error-codes")
    private List<String> errorCodes;

}
