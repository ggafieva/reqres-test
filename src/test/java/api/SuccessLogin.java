package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessLogin {
    private final String token;

    @JsonCreator
    public SuccessLogin(
            @JsonProperty("token") String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}