package com.Jennifer.mst.api.recommendation;

import com.Jennifer.mst.api.ExternalUrl;
import com.Jennifer.mst.api.tracks.ArtistSimplified;
import com.Jennifer.mst.api.tracks.Restriction;
import com.Jennifer.mst.api.tracks.TrackLink;

public class TrackSimplified {

    private ArtistSimplified[] artists;
    private String[] available_market;
    private int disc_number;
    private int duration_ms;
    private boolean explicit;
    private ExternalUrl external_url;
    private String href;
    private String id;
    private boolean is_playable;
    private TrackLink linked_from;
    private Restriction restrictions;
    private String name;
    private String preview_url;
    private int track_number;
    private String type;
    private String uri;
    private boolean is_local;


    public TrackSimplified(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }
}