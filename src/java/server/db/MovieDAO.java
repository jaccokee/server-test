package server.db;

import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys; 
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import server.data.model.response.MovieEntry;
import server.data.model.response.mapper.MovieEntryMapper;

public interface MovieDAO {

  @SqlQuery("select * from movie")
  @Mapper(MovieEntryMapper.class)
  List<MovieEntry> getMovies();

  @SqlQuery("select * from movie where id = :id")
  @Mapper(MovieEntryMapper.class)
  MovieEntry findMovieById(@Bind("id") Long id);

  @SqlUpdate("insert into movie (id, name, genre, year_released, rating) values (:id, :name, :genre, :year_released, :rating)")
  @GetGeneratedKeys
  Long insert(@Bind("id") Long id, @Bind("name") String name, @Bind("genre") String genre, @Bind("year_released") Integer year_released, @Bind("rating") String rating);

  @SqlUpdate("update movie set name = :name, genre = :genre, year_released = :year_released, rating = :rating where  id = :id")
  Integer update(@Bind("id") Long id, @Bind("name") String name, @Bind("genre") String genre, @Bind("year_released") Integer year_released, @Bind("rating") String rating);

  @SqlUpdate("create table movie (id long primary key, name varchar(64), genre varchar(64), year_released int, rating varchar(10))")
  void createMovieTable();

  @SqlUpdate("delete from movie where id = :id")
  Integer delete(@Bind("id") Long id);

  void close();
}