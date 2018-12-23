package com.Jennifer.mst.api.tracks;

import com.Jennifer.mst.api.ExternalUrl;
import com.Jennifer.mst.api.artists.Image;

public class Album {
    private String album_group;
    private String album_type;
    private ArtistSimplified[] artists;
    private String[] available_market;
    private ExternalUrl external_urls;
    private String href;
    private String id;
    private Image[] images;
    private String name;
    private String release_date;
    private String release_date_precision;
    private Restriction restrictions;
    private String type;
    private String uri;

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
}
