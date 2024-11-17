package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessReg extends SuccessAuthBase {
    private final Integer id;

    @JsonCreator
    public SuccessReg(
            @JsonProperty("id") Integer id,
            @JsonProperty("token") String token) {
        super(token);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}