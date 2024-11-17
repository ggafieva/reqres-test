package api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ResourceData {
    private final Integer id;
    private final String name;
    private final Integer year;
    private final String color;
    private final String pantone_value;

    @JsonCreator
    public ResourceData(
            @JsonProperty("id") Integer id,
            @JsonProperty("email") String name,
            @JsonProperty("first_name") Integer year,
            @JsonProperty("last_name") String color,
            @JsonProperty("avatar") String pantone_value) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.color = color;
        this.pantone_value =pantone_value;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getPantone_value() {
        return pantone_value;
    }
}