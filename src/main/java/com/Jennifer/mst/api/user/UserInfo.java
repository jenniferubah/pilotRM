package com.Jennifer.mst.api.user;

import com.Jennifer.mst.api.ExternalUrl;
import com.Jennifer.mst.api.artists.Follower;
import com.Jennifer.mst.api.artists.Image;

public class UserInfo {
    private String birthday;
    private String country;
    private String display_name;
    private String email;
    private ExternalUrl external_url;
    private Follower followers;
    private String href;
    private String id;
    private Image[] images;
    private String product;
    private String type;
    private String uri;

    public String getId() {
        return id;
    }
}
