package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnSuccessLogin {
    private final String error;

    @JsonCreator
    public UnSuccessLogin(
            @JsonProperty("error") String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}