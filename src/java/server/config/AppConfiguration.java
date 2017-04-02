package server.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Application Configuration
 */
public class AppConfiguration extends Configuration {

    @NotEmpty
    private String defaultGenre;  // read in from config.yml
    @NotEmpty
    private String defaultRating;  // read in from config.yml

    @JsonProperty
    public String getDefaultGenre() {
        return defaultGenre;
    }

    @JsonProperty
    public void setDefaultGenre(String defaultGenre) {
        this.defaultGenre = defaultGenre;
    }

    @JsonProperty
    public String getDefaultRating() {
        return defaultRating;
    }

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory dataSourceFactory = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }
}