package com.Jennifer.mst.api.recommendation;

import java.util.*;


public class Recommendations {

    private RecommendationSeed[] seeds;
    private TrackSimplified[] tracks;


    /**
     * This method extracts the uris of recommended track list(filtered list)
     * @param trackIds
     * @return a list of track uris
     */
    public String getRecomTrackList(List<String> trackIds){
        Map<String, TrackSimplified> tracks = recommendedTrackMap(trackIds);
        StringBuffer trackUris = new StringBuffer();

        for(Map.Entry<String, TrackSimplified> entry : tracks.entrySet()){
            trackUris.append(entry.getValue().getUri() + ",");
        }
        String trackUrisList = trackUris.toString().replaceAll(",$", "");

        return trackUrisList;
    }


    /**
     *
     * This method compares between the list of top track ids and recommended track ids
     * @param topTracksIDs
     * @return a filtered map of recommend track ids
     *
     */
    public Map<String, TrackSimplified> recommendedTrackMap(List<String> topTracksIDs ){

        List<String> filteredRecommIDs = getFilteredRecommendedIDs(topTracksIDs);

        Map<String, TrackSimplified> recommMap = new HashMap<>();
        for(int i = 0; i < tracks.length; i++){
            recommMap.put(tracks[i].getId(), new TrackSimplified (tracks[i].getName(), tracks[i].getUri()));
        }

        for(int i = 0; i < filteredRecommIDs.size(); i++){
            if(!(recommMap.containsKey(filteredRecommIDs.get(i)))){
                recommMap.remove(filteredRecommIDs.get(i));
                continue;
            }
        }

        return recommMap;
    }


    //returns a set of the recommended track ids (originally from spotify)
    public Set<String> getRecommendedTrackIDs(){

        Set<String> recommendedIdSet = new HashSet<>();
        for(int i = 0; i < tracks.length; i++){
            recommendedIdSet.add(tracks[i].getId());
        }

        return recommendedIdSet;
    }

    /**
     * This method removed redundant track ids, using the recommended track ids
     * and top track list
     * @param topTrackList
     * @return a list of filtered top tracks
     */
    public List<String> getFilteredRecommendedIDs(List<String> topTrackList){

        Set<String> recommendedList = getRecommendedTrackIDs();

        for(String trackId : topTrackList){
            if(recommendedList.contains(trackId));
            recommendedList.remove(trackId);
        }

        List<String> recommendedTrackList = new ArrayList<>(recommendedList);
        //System.out.println("filtered recom track id List" + recommendedTrackList);
        return recommendedTrackList;

    }

}
