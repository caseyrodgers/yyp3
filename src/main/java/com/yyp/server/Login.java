package com.yyp.server;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class Login extends YypSingleton {
	@GET
	@Path("/login")
	public String registerForNotification(@QueryParam("key") String key, @QueryParam("client_id") long clientId)
	        throws Exception {
		JSONObject results = new JSONObject();
		results.put(STATUS, STATUS_OK);
		return results.toString();
	}
}
