package org.example.streamingservice.movies;


/**
 * Provides the Definition/Structure for the movies row
 *
 * @author Carter Hauschildt
 */

public class Movie {

    private String Title;

    private String Rating;

    private String Runtime;

    private String Director;

    public Movie(){
        
    }

    public Movie(String title, String rating, String runtime){
        this.Title = title;
        this.Rating = rating;
        this.Runtime = runtime;
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

    public String getRuntime() {
        return this.Runtime;
    }

    public void setRuntime(String runtime) {
        this.Runtime = runtime;
    }

    public String getDirector() {
        return this.Director;
    }

    public void setDirector(String director) {
        this.Director = director;
    }

    @Override
    public String toString() {
        return Title + " "
               + Rating + " "
               + Runtime + " "
                + Director;
    }
}
