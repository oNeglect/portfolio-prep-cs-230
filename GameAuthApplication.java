/*
 * @author: Lawrence Arundel
 * @date: July 2022
 * @Assingment: 4-2 Game Player Management Application
 * 
 * Resources:
 * Geraldo, how to do in java. (2022, January 24). Dropwizard - BasicAuth security example. HowToDoInJava. 
 * Retrieved July 14, 2022,from https://howtodoinjava.com/dropwizard/dropwizard-basic-auth-security-example/
 * 
 * Gupta, L. (2022, January 27). Consume dropwizard rest apis with Jersey/HTTP client. HowToDoInJava. 
 * Retrieved July 14, 2022,from https://howtodoinjava.com/dropwizard/client-configuration-and-examples/ 
 *  
 * Geeksforgeeks, G. (2022, January 11). Logger info(string) method in java with examples. GeeksforGeeks. 
 * Retrieved July 14, 2022, from https://www.geeksforgeeks.org/logger-infostring-method-in-java-with-examples/#:~
 * :text=The%20info()%20method%20of,the%20registered%20output%20Handler%20objects. 
 *  
 *  
 */

package com.dropwizard.GameAuth;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dropwizard.GameAuth.resources.auth.GameAuthenticator;
import com.dropwizard.GameAuth.resources.auth.GameAuthorizer;
import com.dropwizard.GameAuth.resources.auth.GameUser;

import com.dropwizard.GameAuth.resources.controller.GameUserRESTController;

import com.dropwizard.GameAuth.resources.healthcheck.HealthCheckController;

//Class which extends the application cass which configures the REST api to communicate between clients
//and performs various server calls and functions
public class GameAuthApplication extends Application<Configuration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameAuthApplication.class);

	@Override
	public void initialize(Bootstrap<Configuration> b) {
	}

	@Override
	public void run(Configuration c, Environment e) throws Exception {

		// The info() method of a Logger class is used to Log an INFO message.
		// This method is used to forward logs to all the registered output Handler
		// object
		LOGGER.info("Registering REST resources");

		// Registers GameUserRESTController to the environment
		e.jersey().register(new GameUserRESTController(e.getValidator()));

		// Creates io.dropwizard.client.JerseyClientBuilder instance while casting
		// client
		// onto the client variable to compile this line of code properly
		final javax.ws.rs.client.Client client = new JerseyClientBuilder(e).build("DemoRESTClient");

		// Run multiple health checks into the environment which validate the
		// status of a microservice and its dependencies.
		e.jersey().register(new HealthCheckController(e.healthChecks()));

		// Setup Basic Security which registers multiple environments to be run during
		// the server configuration process. This sets up the credential and authenticator, the authorizer
		// and various roles and priviledge accesses for the server
		
		e.jersey()
				.register(new AuthDynamicFeature(
						new BasicCredentialAuthFilter.Builder<GameUser>().setAuthenticator(new GameAuthenticator())
								.setAuthorizer(new GameAuthorizer()).setRealm("App Security").buildAuthFilter()));
		e.jersey().register(new AuthValueFactoryProvider.Binder<>(GameUser.class));
		e.jersey().register(RolesAllowedDynamicFeature.class);
	}

	//main which runs the application and configures the server	
	public static void main(String[] args) throws Exception {
		new GameAuthApplication().run(args);
	}
}
