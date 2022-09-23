package com.dropwizard.GameAuth.resources.healthcheck;

//Resources:
//IBM, I. B. M. (2020, January 1). IBM Health Check. IBM. Retrieved July 14, 2022, 
//from https://www.ibm.com/garage/method/practices/manage/health-check-apis/ 

import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.health.HealthCheck.Result;
import com.codahale.metrics.health.HealthCheckRegistry;

@Produces(MediaType.APPLICATION_JSON)
@Path("/status")

//At minimum, a health check API is a separate REST 
//service that is implemented within a microservice component 
//that quickly returns the operational status of the service and an indication of its 
//ability to connect to downstream dependent services.
public class HealthCheckController {
	private HealthCheckRegistry registry;

	public HealthCheckController(HealthCheckRegistry registry) {
		this.registry = registry;
	}

	@GET
	public Set<Entry<String, Result>> getStatus() {
		return registry.runHealthChecks().entrySet();
	}
}
