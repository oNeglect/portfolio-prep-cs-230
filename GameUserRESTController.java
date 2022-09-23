package com.dropwizard.GameAuth.resources.controller;

import io.dropwizard.auth.Auth;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.dropwizard.GameAuth.resources.auth.GameUser;
import com.dropwizard.GameAuth.resources.representations.GameUserInfo;
import com.dropwizard.GameAuth.resources.dao.GameUserDB;

//Adds path annotation for gameusers 
@Path("/gameusers")
@Produces(MediaType.APPLICATION_JSON)
public class GameUserRESTController {
	// declaring validator
	private final Validator validator;

	// sets the validator object to the passed variable
	public GameUserRESTController(Validator validator) {
		this.validator = validator;
	}

	// permits all users to be passed into server and gets the response from the
	// authenticated address
	@PermitAll
	@GET
	public Response getGameUsers(@Auth GameUser user) {
		return Response.ok(GameUserDB.getGameUsers()).build();
	}

	// Adds RolesAllowed annotation for USER based on BasicAuth Security Example
	// gets the required information and follows the path specified
	@RolesAllowed({ "USER" })
	@GET
	@Path("/{id}")
	public Response getGameUserById(@PathParam("id") Integer id, @Auth GameUser user) {
		// You can validate here if user is watching his record
		/*
		 * if(id != user.getId()){ //Not allowed }
		 */
		GameUserInfo gameUserInfo = GameUserDB.getGameUser(id);
		if (gameUserInfo != null)
			return Response.ok(gameUserInfo).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}

	// Adds RolesAllowed annotation for ADMIN based on BasicAuth Security Example
	// then accepts the data included within the response by the user
	@RolesAllowed({ "ADMIN" })
	@POST
	public Response createGameUser(GameUserInfo gameUserInfo, @Auth GameUser user) throws URISyntaxException {
		// validation
		Set<ConstraintViolation<GameUserInfo>> violations = validator.validate(gameUserInfo);
		GameUserInfo e = GameUserDB.getGameUser(gameUserInfo.getId());
		if (violations.size() > 0) {
			ArrayList<String> validationMessages = new ArrayList<String>();
			for (ConstraintViolation<GameUserInfo> violation : violations) {
				validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
			}
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		if (e != null) {
			GameUserDB.updateGameUser(gameUserInfo.getId(), gameUserInfo);
			return Response.created(new URI("/gameusers/" + gameUserInfo.getId())).build();
		} else
			return Response.status(Status.NOT_FOUND).build();
	}

	@PUT
	@Path("/{id}")
	public Response updateGameUserById(@PathParam("id") Integer id, GameUserInfo gameUserInfo) {
		// validation
		Set<ConstraintViolation<GameUserInfo>> violations = validator.validate(gameUserInfo);
		GameUserInfo e = GameUserDB.getGameUser(gameUserInfo.getId());
		if (violations.size() > 0) {
			ArrayList<String> validationMessages = new ArrayList<String>();
			for (ConstraintViolation<GameUserInfo> violation : violations) {
				validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
			}
			return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
		}
		if (e != null) {
			gameUserInfo.setId(id);
			GameUserDB.updateGameUser(id, gameUserInfo);
			return Response.ok(gameUserInfo).build();
		} else
			return Response.status(Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("/{id}")
	public Response removeGameUserById(@PathParam("id") Integer id) {
		GameUserInfo gameUserInfo = GameUserDB.getGameUser(id);
		if (gameUserInfo != null) {
			GameUserDB.removeGameUser(id);
			return Response.ok().build();
		} else
			return Response.status(Status.NOT_FOUND).build();
	}
}
