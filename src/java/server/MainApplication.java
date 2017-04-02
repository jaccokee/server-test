package server;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.config.AppConfiguration;
import server.db.MovieDAO;
import server.healthcheck.AppHealthCheck;
import server.requests.MovieResource;

/**
 * Main application
 */
public class MainApplication extends Application<AppConfiguration>
{
	final static Logger logger = LoggerFactory.getLogger(MainApplication.class);


	public static void main(String[] args) throws Exception
	{
        new MainApplication().run(args);
    }

    @Override
    public String getName()
	{
        return "Test Server";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap)
	{
        // framework bootstrap initialization
    }

    @Override
    public void run(AppConfiguration config, Environment environment) throws Exception
	{
		final DBIFactory factory;
		final DBI jdbi;
		MovieDAO dao;

		try
		{
			logger.info("Starting...");

			// application initialization goes here

			factory = new DBIFactory();
			jdbi = factory.build(environment, config.getDataSourceFactory(), "test");
			dao = jdbi.onDemand(MovieDAO.class);
			dao.createMovieTable();

		} catch (Exception exc) {
			// log failure to set up app
			logger.error("Failed to initialize application, exiting...", exc);
			throw new RuntimeException();
		}

		environment.healthChecks().register("app", new AppHealthCheck());

        // register servlet route handlers
		final MovieResource resource = new MovieResource(dao, config.getDefaultGenre(), config.getDefaultRating());
		environment.jersey().register(resource);

    }
}
