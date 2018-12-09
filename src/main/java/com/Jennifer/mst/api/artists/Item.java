package com.Jennifer.mst.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Item {

    private ExternalUri external_urls = new ExternalUri();
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
}
