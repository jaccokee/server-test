package server.data.model.response.mapper;

import java.sql.ResultSet; 
import java.sql.SQLException; 
import org.skife.jdbi.v2.StatementContext; 
import org.skife.jdbi.v2.tweak.ResultSetMapper; 
import server.data.model.response.MovieEntry;

public class MovieEntryMapper implements ResultSetMapper<MovieEntry> {
    public MovieEntry map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new MovieEntry(r.getLong("id"), r.getString("name"), r.getString("genre"), r.getInt("year_released"), r.getString("rating"));
    }
}