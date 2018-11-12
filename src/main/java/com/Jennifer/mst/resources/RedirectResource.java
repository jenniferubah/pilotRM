package com.Jennifer.mst.resources;

import com.Jennifer.mst.api.Authorization;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Path("/redirect_page")
@Produces(MediaType.APPLICATION_JSON)
public class RedirectResource {

    private final String client_id = "2ac0c18c9dcb48c394bc4c2aef7d048e";
    private final String client_secret = "8defc20e1f8e44859657cabfd7ca85ac";
    private String encodedData = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());
    private String authCode = "";

    @GET
    public String getRedirectPage(@QueryParam("code") @NotEmpty String code){
        System.out.println("my authcode is: " + code);
        this.authCode = code;

        Client client = Client.create();
        WebResource spotifyResoursce = client.resource("https://accounts.spotify.com/api/token");

        MultivaluedMap queries = new MultivaluedMapImpl();
        queries.putSingle("grant_type", "authorization_code");
        queries.putSingle("code", this.authCode);
        queries.putSingle("redirect_uri", "http://localhost:8080/redirect_page");

        ClientResponse response = spotifyResoursce.queryParams(queries).
                header("Content-Type", "application/x-www-form-urlencoded" ).
                header("Authorization", "Basic " + encodedData).post(ClientResponse.class);

        String jsonStr = response.getEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        try{

            Authorization myAuth = objectMapper.readValue(jsonStr, Authorization.class);
            System.out.println("access token " + myAuth.getAccess_token());

        } catch (JsonParseException e){
            e.printStackTrace();
        } catch (JsonMappingException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        return "redirectPage.html";

    }


    public String getUserInfo(String token){

        System.out.println("token is " + token);

        Client client = Client.create();
        WebResource spotifyRes = client.resource("https://api.spotify.com/v1/me");
        ClientResponse response = spotifyRes.header("Authorization", "Bearer " + token).get(ClientResponse.class);

        String jsonStr = response.getEntity(String.class);
        return jsonStr;

    }

}
