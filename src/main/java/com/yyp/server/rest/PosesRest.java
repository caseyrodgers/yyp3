package com.yyp.server.rest;


import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cedarsoftware.util.io.JsonWriter;
import com.yyp.helper.client.data.CustomClass;
import com.yyp.helper.client.data.YogaMasterData;
import com.yyp.helper.server.YogaServerDao;
import com.yyp.server.YypSingleton;

@Produces(MediaType.APPLICATION_JSON)
@Path("/")
public class PosesRest extends YypSingleton {
    private static final Logger LOGGER = Logger.getLogger(PosesRest.class.getName());

    @POST
    @GET
    @Path("/poses_get")
    public String getUserStatus(String jsonUserInfo) throws Exception {
    	return getUserStatusAux();
//        final JSONObject dataIn = new JSONObject(jsonUserInfo);
//        long uid = dataIn.getLong("uid");
//        Connection connection = null;
//        try {
//            connection = ConnectionPool.getConnection();
//            return "";
//            // return JsonWriter.objectToJson(stats);
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Exception getting user home status",  e);
//            throw new YypException("Error getting user's home status", e);
//        } finally {
//        	SqlUtilities.releaseResources(null, null, connection);
//        }
    }
    
    
    private String  getUserStatusAux() throws Exception {
    	List<CustomClass> data = YogaServerDao.getInstance().getCustomClassDefinitions(1);
    	String json = JsonWriter.objectToJson(data);
    	return json;
    }
    
    @POST
    @GET
    @Path("/poses_get_db")
    public String  getYypDatabase() throws Exception {
    	YogaMasterData data = YogaServerDao.getInstance().getPostureDatabase(1);
    	String json = JsonWriter.objectToJson(data);
    	return json;
    }
}
