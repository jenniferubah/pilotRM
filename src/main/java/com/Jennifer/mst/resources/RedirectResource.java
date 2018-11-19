package com.Jennifer.mst.resources;

import com.Jennifer.mst.api.Artists;
import com.Jennifer.mst.api.Authorization;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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

import java.io.IOException;
import java.util.Base64;
import java.util.Set;

@Path("/redirect_page")
@Produces(MediaType.APPLICATION_JSON)
public class RedirectResource {

    private final String client_id = "2ac0c18c9dcb48c394bc4c2aef7d048e";
    private final String client_secret = "8defc20e1f8e44859657cabfd7ca85ac";
    private String encodedData = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());
    private String authCode = "";

    @GET
    public String getRedirectPage(@QueryParam("code") @NotEmpty String code){
        //System.out.println("my authcode is: " + code);
        this.authCode = code;

        Client client = Client.create();
        WebResource spotifyResource = client.resource("https://accounts.spotify.com/api/token");

        MultivaluedMap queries = new MultivaluedMapImpl();
        queries.putSingle("grant_type", "authorization_code");
        queries.putSingle("code", this.authCode);
        queries.putSingle("redirect_uri", "http://localhost:8080/redirect_page");

        ClientResponse response = spotifyResource.queryParams(queries).
                header("Content-Type", "application/x-www-form-urlencoded" ).
                header("Authorization", "Basic " + encodedData).post(ClientResponse.class);

        String jsonStr = response.getEntity(String.class);
        ObjectMapper objectMapper = new ObjectMapper();

        try{

            Authorization myAuth = objectMapper.readValue(jsonStr, Authorization.class);
            String token = myAuth.getAccess_token();


            String jsonResponse = getTopArtist(token);

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

    public String getTopArtist(String token){

        MultivaluedMap queries = new MultivaluedMapImpl();
        queries.putSingle("limit", "2");
        queries.putSingle("time_range", "medium_term");


        Client client = Client.create();
        WebResource topArtistEndpoint = client.resource("https://api.spotify.com/v1/me/top/artists");
        ClientResponse response = topArtistEndpoint.queryParams(queries).header("Authorization", "Bearer " + token).get(ClientResponse.class);

        String jsonSource = response.getEntity(String.class);
        System.out.println(jsonSource);

        //=============
        Artists targetObject = new Gson().fromJson(jsonSource, Artists.class);
        System.out.println(targetObject.getAllGenres());

        return jsonSource;
    }


}
