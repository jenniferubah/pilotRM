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
        String[] seedArtist = new String[items.length];

        for (int i = 0; i < items.length; i++){
            String artistId = items[i].getId();
            seedArtist[i] = artistId;
        }

        return seedArtist;
    }

    public String[] getSeedGenres(){
        Set<String> mergedGenreSet = new HashSet<>();

        for(int i = 0; i < items.length; i++){
            mergedGenreSet.addAll(items[i].getGenres());
        }

        Iterator<String> genreListIterator = mergedGenreSet.iterator();

        int count = 0;
        String[] seedGenres = new String[5];
        while(genreListIterator.hasNext() & count < 5){
            seedGenres[count] = genreListIterator.next();
            count++;
        }
        return seedGenres;
    }

}
