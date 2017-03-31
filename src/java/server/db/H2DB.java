import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.h2.jdbcx.JdbcDataSource;

class H2DB {
  final static Logger logger = LoggerFactory.getLogger(H2DB.class);

  JdbcDataSource ds;
  Connection conn;

  public H2DB() {
    ds = new JdbcDataSource();
    ds.setUrl("jdbc:h2:~/test");
    ds.setUser("sa");
    ds.setPassword("sa");
    try {
      this.conn = ds.getConnection();
    } catch(SQLException e) {
        logger.error("Unable to create H2 database connection");
        throw new RuntimeException("Can't create DB connection");
    }
  }

}