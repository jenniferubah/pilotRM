package com.Jennifer.mst.api.tracks;

import com.Jennifer.mst.api.ExternalUrl;

public class TrackLink {
    private ExternalUrl external_urls;
    private String href;
    private String id;
    private String type;
    private String uri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
