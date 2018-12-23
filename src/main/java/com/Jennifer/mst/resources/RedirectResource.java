package com.Jennifer.mst.resources;

/**
 * This class authenticates the user, gets the user data,
 * tracks the user's music and recommends a playlist based
 * on top artists, and redirects the user to another page.
 */

import com.Jennifer.mst.api.OAuth.Authorization;
import com.Jennifer.mst.api.artists.ArtistFull;
import com.Jennifer.mst.api.playlist.Playlist;
import com.Jennifer.mst.api.recommendation.Recommendations;
import com.Jennifer.mst.api.tracks.Track;
import com.Jennifer.mst.api.user.UserInfo;
import com.google.gson.Gson;
import org.hibernate.validator.constraints.NotEmpty;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/redirect_page")
@Produces(MediaType.APPLICATION_JSON)
public class RedirectResource {

    private final String client_id = "";
    private final String client_secret = "";
    private String encodedData = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());
    private String authCode = "";
    private String seedTrack;
    private String seedGenres;
    private String seedArtists;
    private String spotifyUserID;

    @GET
    public String getRedirectPage(@QueryParam("code") @NotEmpty String code){
        //System.out.println("my authcode is: " + code);
        this.authCode = code;

        //*************************

        Client client = ClientBuilder.newClient();
        WebTarget spotifyWebTarget = client.target("https://accounts.spotify.com/api/token");

        Invocation.Builder invocationBuilder = spotifyWebTarget
                .queryParam("grant_type", "authorization_code")
                .queryParam("code", this.authCode)
                .queryParam("redirect_uri","http://localhost:8080/redirect_page" )
                .request();

        String response = invocationBuilder
                .header("Authorization", "Basic " + encodedData)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(null, String.class);

        System.out.println("response" + response);

        Authorization authorizationObj = new Gson().fromJson(response, Authorization.class);
        String token = authorizationObj.getAccess_token();

        System.out.println("Token " + authorizationObj.getAccess_token());


        //========================

        createPlaylistBasedOnRecom(token);

        //*************************

        return "redirectPage.html";

    }

    public String createPlaylistBasedOnRecom(String token){

        String userInfo = getUserInfo(token);
        System.out.println("user information " + userInfo);

        List<String> topTrackIDs = getTopTracks(token);
        System.out.println("top track ids " + topTrackIDs);

        String artistAndGenres = getTopArtistAndGenres(token);
        System.out.println("artist and genres " + artistAndGenres);

        String trackUris = getRecommendationList(token, artistAndGenres, topTrackIDs);
        System.out.println("track uris " + trackUris);

        String response = addRecommendedTracksToPlaylist(token, trackUris);
        System.out.println("RESPONSE " + response);
        return  response;

    }

    /**
     * This method uses the token to make a call to the /me endpoint
     * @param token - access token
     * @return the user's spotify information
     */
    public String getUserInfo(String token){

        Client client = ClientBuilder.newClient();
        WebTarget spotifyRes = client.target("https://api.spotify.com/v1/me");
        String response = spotifyRes.request()
                .header("Authorization", "Bearer " + token)
                .get(String.class);

        UserInfo userID = new Gson().fromJson(response, UserInfo.class);
        spotifyUserID = userID.getId();

        return response;

    }

    /**
     * This method make a call to the top artists endpoint
     * @param token - access token
     * @return comma separated list of top artist ids
     */

    public String getTopArtistAndGenres(String token){

        Client client = ClientBuilder.newClient();
        WebTarget topArtistEndpoint = client.target("https://api.spotify.com/v1/me/top/artists");
        String response = topArtistEndpoint.queryParam("limit", "7")
                .queryParam("time_range", "medium_term")
                .request()
                .header("Authorization", "Bearer " + token)
                .get(String.class);

        ArtistFull artistObject = new Gson().fromJson(response, ArtistFull.class);

        StringBuffer artistsAndGenresBuffer = new StringBuffer();
        String[] seedArtists = artistObject.getSeedAritst();
        for(int i = 0; i < 5; i++){
            artistsAndGenresBuffer.append(seedArtists[i] + ",");
        }


/*        Set<String> genreSet = artistObject.getAllGenres();
        int count = 0;
        StringBuffer genresBuffer = new StringBuffer();
        Iterator<String> genreListIterator = genreSet.iterator();

        while(genreListIterator.hasNext() & count <= 5){
            genresBuffer.append(genreListIterator.next() + ",");
            count++;
        }*/

        String artistAndGenre = artistsAndGenresBuffer.toString().replaceAll(",$", "");

        return artistAndGenre;
    }

    /**
     * This method makes a call to the top tracks endpoint
     * @param token - access token
     * @return a list of top track ids
     */

    public List<String> getTopTracks(String token){

        Client client = ClientBuilder.newClient();
        WebTarget topArtistEndpoint = client.target("https://api.spotify.com/v1/me/top/tracks");
        String response = topArtistEndpoint.queryParam("limit", "4")
                .queryParam("time_range", "medium_term")
                .request()
                .header("Authorization", "Bearer " + token)
                .get(String.class);

        Track trackObject = new Gson().fromJson(response, Track.class);

        List<String> topTrackIdList = trackObject.getTrackIDs();
        //this.seedTrack = trackObject.getSeedTrack();

        return topTrackIdList;

    }

    /**
     * This method makes a call the recommendation endpoint
     * @param token - access token
     * @param artist - top artist ids
     * @param topTrackIdList - top track ids
     * @return a comma separated list of track uris for recommended tracks
     */

    public String getRecommendationList(String token, String artist, List<String> topTrackIdList){

        Client client = ClientBuilder.newClient();
        WebTarget topArtistEndpoint = client.target("https://api.spotify.com/v1/recommendations");
        String response = topArtistEndpoint.queryParam("limit", "5")
                .queryParam("seed_artists", artist)
                .request()
                .header("Authorization", "Bearer " + token)
                .get(String.class);

        Recommendations recommendations = new Gson().fromJson(response, Recommendations.class);
        String trackUris = recommendations.getRecomTrackList(topTrackIdList);

        return trackUris;

    }

    /**
     * This method makes a call to the playlist endpoint
     * and creates a playlist on spotify
     * @param token - access token
     */
    public void createPlaylistOnSpotify(String token){

        Client client = ClientBuilder.newClient();
        WebTarget createPlaylistEndpoint = client.target("https://api.spotify.com/v1/users/jenniferubah/playlists");

        Playlist playlist = new Playlist();
        playlist.setName("demo");
        String json = new Gson().toJson(playlist);

        String response = createPlaylistEndpoint
                .request()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type","application/json")
                .post(Entity.entity(json, MediaType.APPLICATION_JSON_TYPE), String.class);


    }

    /**
     * This method makes call to add a list of tracks to the created playlist
     * @param token - access token
     * @param tracks - a comma separated list of recommended track.
     * @return returns a snapshot of the playlist id
     */

    public String addRecommendedTracksToPlaylist(String token, String tracks){

        Client client = ClientBuilder.newClient();
        WebTarget addTrackToPlaylistEndpoint = client.
                target("https://api.spotify.com/v1/playlists/{playlist_id}/tracks");

        String response = addTrackToPlaylistEndpoint.queryParam("uris", tracks).request()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type","application/json")
                .post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE), String.class);

        return response;

    }

}
