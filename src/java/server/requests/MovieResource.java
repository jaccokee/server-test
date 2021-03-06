package server.requests;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;
import java.util.concurrent.atomic.AtomicLong;
import java.util.ArrayList;
import java.util.Date;
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
import server.db.MovieDAO;
import server.exceptions.MovieNotFoundException;

@Path("/api")
@Produces(APPLICATION_JSON)
public class MovieResource {
	final static Logger logger = LoggerFactory.getLogger(MovieResource.class);

    private final MovieDAO dao;
    private final String defaultGenre;
    private final String defaultRating;
    private final AtomicLong counter;

    public MovieResource(MovieDAO dao, String defaultGenre, String defaultRating) {
        this.dao = dao;
        this.defaultGenre = defaultGenre;
        this.defaultRating = defaultRating;
        this.counter = new AtomicLong();
    }

    @GET
    @Path("/timeOfDay")
    @Timed
    public Response getTimeOfDay() {
        Date dt = new Date();
        String tod = String.format("%02d:%02d:%02d", dt.getHours(), dt.getMinutes(), dt.getSeconds());
        return Response.ok().entity(tod).build();
    }

    @GET
    @Path("/{id}")
    @Timed
    public Response getMovie(@PathParam("id") Optional<Long> id) { // TODO: use Optional, or not?
        MovieEntry mov = dao.findMovieById(id.get());
        if (mov == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found with id: " + id.get()).build();
        }
        return Response.ok(mov).build();
    }

    @GET
    @Path("/list")
    @Timed
    public Response getMovieList() {
        List<MovieEntry> movies = dao.getMovies();
        return Response.ok(movies).build();
    }

    @POST
    @Consumes({APPLICATION_FORM_URLENCODED, APPLICATION_JSON})
    @Timed
    public Response addMovie(@FormParam("name") String name,
                             @FormParam("genre") String genre,
                             @FormParam("yearReleased") Integer yearReleased,
                             @FormParam("rating") String rating) {
        MovieEntry mov = new MovieEntry(counter.incrementAndGet(), name, genre, yearReleased, rating);
        Long rtnId = dao.insert(mov.getId(), mov.getName(), mov.getGenre(), mov.getYearReleased(), mov.getRating());
        logger.info("movie insert returned value: " + rtnId);
        return Response.ok("Movie " + mov.getId() + " created").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_FORM_URLENCODED)
    @Timed
    public Response updateMovie(@PathParam("id") Optional<Long> id,
                            @FormParam("name") String name,
                            @FormParam("genre") String genre,
                            @FormParam("yearReleased") Integer yearReleased,
                            @FormParam("rating") String rating) {
        Integer rtn = dao.update(id.get(), name, genre, yearReleased, rating);
        if (rtn != null) {
            return Response.ok("Movie " + id.get() + " updated").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found with id: " + id.get()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Timed
    public Response deleteMovie(@PathParam("id") Optional<Long> id) {
        Integer rtn = dao.delete(id.get());  // 1=deleted, 0=not found
        logger.info("movie delete returned value: " + rtn);
        if (rtn > 0) {
            return Response.ok("Movie " + id.get() + " deleted").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found with id: " + id.get()).build();
        }
    }
}
