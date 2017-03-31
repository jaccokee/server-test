package server.db;

import server.data.model.response.MovieEntry;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class MovieDAO extends AbstractDAO<MovieEntry> {
    public MovieDAO(SessionFactory factory) {
        super(factory);
    }

    public Optional<MovieEntry> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public MovieEntry create(MovieEntry movie) {
        return persist(movie);
    }
}
