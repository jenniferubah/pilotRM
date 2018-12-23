package com.Jennifer.mst.api.artists;

import com.Jennifer.mst.api.ExternalUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Item {

    private ExternalUrl external_urls = new ExternalUrl();
    private Follower followers = new Follower();
    private Set<String> genres;
    private String href;
    private String id;
    private List<Image> images = new ArrayList<>();
    private String name;
    private int popularity;
    private String type;
    private String uri;


    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
