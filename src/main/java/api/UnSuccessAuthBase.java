package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnSuccessAuthBase {
    private final String error;

    @JsonCreator
    public UnSuccessAuthBase(
            @JsonProperty("error") String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}