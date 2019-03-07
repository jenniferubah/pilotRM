package com.Jennifer.mst.resources;

/**
 * This displays the login page that redirects
 * to a spotify's accounts page for the user to log in.
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private final String client_id = "";

    @GET
    public Response onLoginClick() {

        try {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("client_id", client_id);
            queryParams.put("response_type", "code");
            queryParams.put("redirect_uri", "http://localhost:8080/redirect_page");
            queryParams.put("scope", "user-read-private user-top-read playlist-modify-private playlist-modify-public");

            String queries = MyStringBuilder.getParamsString(queryParams);

            return Response.seeOther(URI.create("https://accounts.spotify.com/authorize?" + queries)).build();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }

}
