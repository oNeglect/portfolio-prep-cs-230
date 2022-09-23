package com.dropwizard.GameAuth.resources.healthcheck;

//Resources:
//IBM, I. B. M. (2020, January 1). IBM Health Check. IBM. Retrieved July 14, 2022, 
//from https://www.ibm.com/garage/method/practices/manage/health-check-apis/ 

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.health.HealthCheck;

//At minimum, a health check API is a separate REST 
//service that is implemented within a microservice component 
//that quickly returns the operational status of the service and an indication of its 
//ability to connect to downstream dependent services.
public class AppHealthCheck extends HealthCheck {
	private final Client client;

	public AppHealthCheck(Client client) {
		super();
		this.client = client;
	}

	@Override
	protected Result check() throws Exception {
		WebTarget webTarget = client.target("http://localhost:8080/gameusers");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		@SuppressWarnings("rawtypes")
		ArrayList gameusers = response.readEntity(ArrayList.class);
		if (gameusers != null && gameusers.size() > 0) {
			return Result.healthy();
		}
		return Result.unhealthy("API Failed");
	}
}
