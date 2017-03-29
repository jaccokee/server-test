package server.data.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

public class MovieEntry {
    private long id;

    @Length(max = 128)
    private String name;
    @Length(max = 128)
    private String genre;
    private Integer yearReleased;
    @Length(max = 5)
    private String rating;

    public MovieEntry() {
        // Jackson deserialization
    }

    public MovieEntry(long id, String name, String genre, Integer yearReleased, String rating) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.yearReleased = yearReleased;
        this.rating = rating;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Integer getYearReleased() {
        return yearReleased;
    }

    @JsonProperty
    public String getRating() {
        return rating;
    }
}
