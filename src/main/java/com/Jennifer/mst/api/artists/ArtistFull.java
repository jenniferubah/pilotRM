package com.Jennifer.mst.api.artists;

import java.util.*;

public class ArtistFull {

    private Item[] items;
    private String next;
    private String previous;
    private int total;
    private String limit;
    private String href;


    public String[] getSeedAritst() {
        StringBuilder stringBuilder = new StringBuilder();
        String[] artistArr = new String[items.length];

        for (int i = 0; i < items.length; i++){
            String artistId = items[i].getId();
            artistArr[i] = artistId;
        }

        //String artistID = stringBuilder.toString().replaceAll(",$", "");
        return artistArr;
    }

    public Set<String> getAllGenres(){
        Set<String> mergedGenreSet = new HashSet<>();

        for(int i = 0; i < items.length; i++){
            mergedGenreSet.addAll(items[i].getGenres());
        }
        return mergedGenreSet;
    }

}
