package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessAuthBase {
    private final String token;

    @JsonCreator
    public SuccessAuthBase(
            @JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}