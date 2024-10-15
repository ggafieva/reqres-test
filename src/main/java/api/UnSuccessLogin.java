package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnSuccessLogin extends UnSuccessAuthBase {

    @JsonCreator
    public UnSuccessLogin(
            @JsonProperty("error") String error) {
        super(error);
    }
}