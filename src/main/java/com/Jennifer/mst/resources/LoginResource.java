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

    private final String client_id = "2ac0c18c9dcb48c394bc4c2aef7d048e";
    private final String client_secret = "8defc20e1f8e44859657cabfd7ca85ac";

    private String encodedData = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());

    private String authCode = "";

    @GET
    public Response onLoginClick() {

        try {
            Map<String, String> queryParams = new HashMap<>();
            queryParams.put("client_id", client_id);
            queryParams.put("response_type", "code");
            queryParams.put("redirect_uri", "http://localhost:8080/redirect_page");
            queryParams.put("scope", "user-read-private");

            String queries = MyStringBuilder.getParamsString(queryParams);

            return Response.seeOther(URI.create("https://accounts.spotify.com/authorize?" + queries)).build();
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }

}
