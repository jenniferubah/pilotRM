package com.Jennifer.mst.resources;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.*;
import java.net.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Path("/redirect")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private final String client_id = "";
    private final String client_secret = "";

    private String encodedData = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());

    private String authCode = "";

    @GET
    public Response onLoginClick() {

        try {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("client_id", client_id);
            queryParams.put("response_type", "code");
            queryParams.put("redirect_uri", "http://localhost:8080/redirect_page");
            queryParams.put("scope", "user-read-private user-top-read");

            String queries = MyStringBuilder.getParamsString(queryParams);

            return Response.seeOther(URI.create("https://accounts.spotify.com/authorize?" + queries)).build();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }

}
