package com.Jennifer.mst;

import com.Jennifer.mst.health.TemplateHealthCheck;
import com.Jennifer.mst.resources.HelloWorldResource;
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

        final Client client = new JerseyClientBuilder(environment).using(pilotConfig.getJerseyClientConfiguration())
                .build(getName());

        final LoginResource loginResource = new LoginResource();
        final RedirectResource redirectResource = new RedirectResource();

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(pilotConfig.getTemplate());

        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(loginResource);
        environment.jersey().register(redirectResource);
        environment.jersey().register((client));

        LOGGER.info("Template: {}", pilotConfig.getTemplate());
    }
}
