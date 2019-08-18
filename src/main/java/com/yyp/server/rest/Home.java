package com.yyp.server.rest;


import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.yyp.server.ConnectionPool;
import com.yyp.server.YypSingleton;
import com.yyp.server.exception.YypException;
import com.yyp.server.util.SqlUtilities;

@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class Home extends YypSingleton {
    private static final Logger LOGGER = Logger.getLogger(Home.class.getName());

    @POST
    @Path("/home")
    public String getUserStatus(String jsonUserInfo) throws YypException {
        final JSONObject dataIn = new JSONObject(jsonUserInfo);
        long uid = dataIn.getLong("uid");
        Connection connection = null;
        try {
            connection = ConnectionPool.getConnection();
            return "";
            // return JsonWriter.objectToJson(stats);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception getting user home status",  e);
            throw new YypException("Error getting user's home status", e);
        } finally {
        	SqlUtilities.releaseResources(null, null, connection);
        }

    }
}
