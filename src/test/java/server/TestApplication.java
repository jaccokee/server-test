package server;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
//import io.dropwizard.db.ManagedDataSource;
//import io.dropwizard.lifecycle.Managed;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.setup.Environment;
import java.util.concurrent.Executors;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import server.MainApplication;
import server.config.AppConfiguration;
import server.requests.MovieResource;

public class TestApplication {
    private final HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
    private final LifecycleEnvironment lifecycleEnvironment = mock(LifecycleEnvironment.class);
    private final Environment environment = mock(Environment.class);
    private final JerseyEnvironment jersey = mock(JerseyEnvironment.class);
    private final MetricRegistry metricRegistry = new MetricRegistry();
    private final MainApplication application = new MainApplication();
    private final AppConfiguration config = new AppConfiguration();

    @Before
    public void setup() throws Exception {
        config.setDefaultGenre("testdfltgenre");
        config.setDefaultRating("testdfltrating");
        DataSourceFactory dsFac = new DataSourceFactory();
        dsFac.setDriverClass("org.h2.Driver");
        dsFac.setUrl("jdbc:h2:mem:JDBITest-" + System.currentTimeMillis());
        dsFac.setUser("sa");
        dsFac.setPassword("sa");
        dsFac.setValidationQuery("SELECT 1");
        config.setDataSourceFactory(dsFac);
        when(environment.healthChecks()).thenReturn(healthChecks);
        when(environment.lifecycle()).thenReturn(lifecycleEnvironment);
        when(environment.metrics()).thenReturn(metricRegistry);
        when(environment.getHealthCheckExecutorService()).thenReturn(Executors.newSingleThreadExecutor());
        when(environment.jersey()).thenReturn(jersey);
    }

    @Test
    public void buildsAMovieResource() throws Exception {
        application.run(config, environment);

        verify(jersey).register(isA(MovieResource.class));
    }
}