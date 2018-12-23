package com.Jennifer.mst.api.recommendation;

public class RecommendationSeed {

    private int afterFilteringSize;
    private int afterRelinkingSize;
    private String href;
    private String id;
    private int initialPoolSize;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
