package com.dropwizard.GameAuth.resources.auth;

import io.dropwizard.auth.Authorizer;

//class which implements authorizer which checks to see which roles have priviledge on the server
public class GameAuthorizer implements Authorizer<GameUser> 
{
    @Override
    public boolean authorize(GameUser user, String role) {
    	
        //Authorizes method based on BasicAuth Security Example and returns user role
    	 return user.getRoles() != null && user.getRoles().contains(role);
    }
}
