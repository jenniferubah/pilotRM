package com.Jennifer.mst;

/**
 *
 * The Pilot application is a program that suggests
 * musical playlist better suited for a user.
 *
 * @author Jennifer Ubah
 * @version 1.0
 * @since 2018-12-09
 */

import com.Jennifer.mst.health.TemplateHealthCheck;
import com.Jennifer.mst.resources.LoginResource;
import com.Jennifer.mst.resources.RedirectResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;

public class PilotApplication extends Application<PilotConfiguration> {

    public static final Logger LOGGER = LoggerFactory.getLogger(PilotApplication.class);

    public static void main(final String[] args) throws Exception {
        new PilotApplication().run(args);
    }

    public String getName(){
        return "Hello World";
    }

    @Override
    public void initialize(Bootstrap<PilotConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/static", "/login", "index.html"));
    }

    @Override
    public void run(PilotConfiguration pilotConfig, Environment environment) throws Exception {

        //creating a client with Dropwizard
        final Client client = new JerseyClientBuilder(environment).using(pilotConfig.getJerseyClientConfiguration())
                .build("Pilot");

        //adding health checks
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(pilotConfig.getTemplate());

        //registering resources
        final LoginResource loginResource = new LoginResource();
        final RedirectResource redirectResource = new RedirectResource();
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(loginResource);
        environment.jersey().register(redirectResource);
        environment.jersey().register((client));

        LOGGER.info("Template: {}", pilotConfig.getTemplate());
    }
}
