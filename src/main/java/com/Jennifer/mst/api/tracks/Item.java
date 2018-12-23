package com.Jennifer.mst.api.tracks;

import com.Jennifer.mst.api.ExternalUrl;

public class Item {

    private Album album;
    private ArtistSimplified[] artists;
    private String[] available_market;
    private int disc_number;
    private int duration_ms;
    private boolean explicit;
    private ExternalID external_id;
    private ExternalUrl external_urls;
    private String href;
    private String id;
    private boolean is_playable;
    private TrackLink linked_from;
    private String name;
    private int popularity;
    private String preview_url;
    private int track_number;
    private String type;
    private String uri;

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrack_number() {
        return track_number;
    }

    public void setTrack_number(int track_number) {
        this.track_number = track_number;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getId() {
        return id;
    }
}
