package server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

/**
 * Application Configuration
 */
public class AppConfiguration extends Configuration {

    @NotEmpty
    private String defaultGenre;  // read in from config.yml

    @JsonProperty
    public String getDefaultGenre() {
        return defaultGenre;
    }

    @JsonProperty
    public void setDefaultGenre(String defaultGenre) {
        this.defaultGenre = defaultGenre;
    }

}