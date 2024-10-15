package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessLogin extends SuccessAuthBase {

    @JsonCreator
    public SuccessLogin(
            @JsonProperty("token") String token) {
        super(token);
    }
}