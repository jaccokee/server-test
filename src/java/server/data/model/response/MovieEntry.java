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
        System.out.println("passed in: [" + id + ", " + name + ", " + genre + ", " + yearReleased + ", " + rating + "]");
        System.out.println("movie: " + this.toString());
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @JsonProperty
    public Integer getYearReleased() {
        return yearReleased;
    }

    public void setYearReleased(Integer yearReleased) {
        this.yearReleased = yearReleased;
    }

    @JsonProperty
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String toString() {
        return String.format("movie: %d, %s, %s, %s, %s", id, name, genre, yearReleased, rating);
    }
}
