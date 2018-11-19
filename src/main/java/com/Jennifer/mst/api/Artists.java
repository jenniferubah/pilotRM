package com.Jennifer.mst.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Artists {

    private Item[] items;
    private String next;
    private String previous;
    private int total;
    private String limit;
    private String href;


    public String getAllArtistIDs() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < items.length; i++){
            String artistId = items[i].getId();
            stringBuilder.append(artistId + ",");
        }

        return stringBuilder.toString();

    }

    public Set<String> getAllGenres(){
        Set<String> mergedGenreSet = new HashSet<>();

        for(int i = 0; i < items.length; i++){
            mergedGenreSet.addAll(items[i].getGenres());
        }
        return mergedGenreSet;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
