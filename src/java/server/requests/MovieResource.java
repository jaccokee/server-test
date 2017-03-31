package server.requests;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.data.model.response.MovieEntry;

@Path("/movie")
@Produces(APPLICATION_JSON)
public class MovieResource {
	final static Logger logger = LoggerFactory.getLogger(MovieResource.class);

    private final String defaultGenre;
    private final AtomicLong counter;
    // temporary in-memory data store
    HashMap<Integer, MovieEntry> movies = new HashMap<>();

    public MovieResource(String defaultGenre) {
        this.defaultGenre = defaultGenre;
        this.counter = new AtomicLong();
    }

    @GET
    @Path("/{id}")
    @Timed
    public MovieEntry getMovie(@PathParam("id") Optional<Integer> id) { // TODO: use Optional, or not?
        // TODO: store mov in H2
        MovieEntry mov = movies.get(id.get());
        logger.info("retrieving movie with id " + id.get() + ", movie name: " + mov.getName());
        return mov;
    }

    @GET
    @Path("/list")
    @Timed
    public List<MovieEntry> getMovieList() {
        List<MovieEntry> rtnList = new ArrayList<>();
        for (MovieEntry mov : movies.values()) {
            rtnList.add(mov);
        }
        return rtnList;
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Timed
    public Response addMovie(@FormParam("name") String name,
                             @FormParam("genre") String genre,
                             @FormParam("yearReleased") Integer yearReleased,
                             @FormParam("rating") String rating) {
        logger.info("name passed in: " + name + ", genre passed in: " + genre);
        MovieEntry mov = new MovieEntry(counter.incrementAndGet(), name, genre, yearReleased, rating);
        logger.info("added movie w/ id: " + counter.get());
        // TODO: store mov in H2
        movies.put(counter.intValue(), mov);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Timed
    public Response updateMovie(@PathParam("id") Integer id,
                            @FormParam("name") String name,
                            @FormParam("genre") String genre,
                            @FormParam("yearReleased") Integer yearReleased,
                            @FormParam("rating") String rating) {
        MovieEntry mov = movies.get(id);
        if (mov != null) {
            mov.setName(name);
            mov.setGenre(genre);
            mov.setYearReleased(yearReleased);
            mov.setRating(rating);
            movies.replace(id, mov);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Entity not found for id: " + id).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public void deleteMovie(@PathParam("id") Integer id) {
        if (movies.get(id) != null) {
            movies.remove(id);
            // TODO: return appropriate JSON success message - Response.ok.build();
        } else {
            // TODO: return appropriate JSON error - Response.status(Response.Status.NOT_FOUND).entity("Entity not found for id: " + id).build();
        }
    }
}
