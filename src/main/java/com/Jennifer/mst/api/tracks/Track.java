package com.Jennifer.mst.api.tracks;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private Item[] items;
    private String next;
    private String previous;
    private int total;
    private String limit;
    private String href;


    public String getSeedTrack(){
        StringBuffer buffer = new StringBuffer();
        String seedTrack = items[0].getId();

        for(int i = 0; i < items.length; i++){
            String trackID = items[i].getId();
            buffer.append(trackID + ",");
        }
        String trackID = buffer.toString().replaceAll(",$", "");
        return seedTrack;
    }

    public List<String> getTrackIDs(){
        List<String> trackId = new ArrayList<>();

        for(int i = 0; i < items.length; i++){
            trackId.add(items[i].getId());
        }
        return trackId;
    }
}
