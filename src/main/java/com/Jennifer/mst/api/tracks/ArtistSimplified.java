package com.Jennifer.mst.api.tracks;

import com.Jennifer.mst.api.ExternalUrl;

public class ArtistSimplified {

    private ExternalUrl external_urls;
    private String href;
    private String id;
    private String name;
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
