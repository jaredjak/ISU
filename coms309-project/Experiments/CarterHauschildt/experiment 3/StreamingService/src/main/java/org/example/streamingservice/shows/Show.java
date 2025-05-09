package org.example.streamingservice.shows;


/**
 * Provides the Definition/Structure for the shows row
 *
 * @author Carter Hauschildt
 */

public class Show {

    private String Title;

    private String Rating;

    private String SeasonCount;

    private String EpisodesPerSeason;

    public Show(){
        
    }

    public Show(String title, String rating, String seasonCount, String episodesPerSeason){
        this.Title = title;
        this.Rating = rating;
        this.SeasonCount = seasonCount;
        this.EpisodesPerSeason = episodesPerSeason;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public String getRating() {
        return this.Rating;
    }

    public void setRating(String rating) {
        this.Rating = rating;
    }

    public String getSeasonCount() {
        return this.SeasonCount;
    }

    public void setSeasonCount(String seasonCount) {
        this.SeasonCount = seasonCount;
    }

    public String getEpisodesPerSeason() {
        return this.EpisodesPerSeason;
    }
    
    public void setEpisodesPerSeason(String episodesPerSeason) {
        this.EpisodesPerSeason = episodesPerSeason;
    }

    @Override
    public String toString() {
        return Title + " "
               + Rating + " "
               + SeasonCount + " "
               + EpisodesPerSeason + " ";
    }
}
