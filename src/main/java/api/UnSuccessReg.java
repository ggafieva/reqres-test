package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnSuccessReg extends UnSuccessAuthBase {

    @JsonCreator
    public UnSuccessReg(
            @JsonProperty("error") String error) {
        super(error);
    }
}