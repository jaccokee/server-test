package server.requests;

import server.data.model.response.MovieEntry;
import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/movie")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {
    private final String defaultGenre;
    private final AtomicLong counter;

    public MovieResource(String defaultGenre) {
        this.defaultGenre = defaultGenre;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public MovieEntry getMovie(@QueryParam("name") Optional<String> name) {
        System.out.println("/movie?name=[xxx] was called.  Name passed: " + name);
        return new MovieEntry(counter.incrementAndGet(), name.get(), "test", 1980, "PG-13");
    }
}
