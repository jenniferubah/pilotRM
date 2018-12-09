package com.Jennifer.mst.api.Tracks;

import com.Jennifer.mst.api.Item;

public class Track {
    private Item[] items;
    private String next;
    private String previous;
    private int total;
    private String limit;
    private String href;

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
