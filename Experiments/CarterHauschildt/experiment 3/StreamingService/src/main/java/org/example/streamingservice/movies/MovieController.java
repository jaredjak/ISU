package org.example.streamingservice.movies;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author carter hauschildt
 */

@RestController
public class MovieController {

    // Note that there is only ONE instance of MovieController in
    // Springboot system.
    HashMap<String, Movie> movieList = new  HashMap<>();



    // THIS IS THE LIST OPERATION
    // Note: To LIST, we use the GET method
    @GetMapping("/movies")
    public  HashMap<String,Movie> getAllMovies() {
        return movieList;
    }

    // THIS IS THE CREATE OPERATION
    // Note: To CREATE we use POST method
    @PostMapping("/movies")
    public  String createMovie(@RequestBody Movie movie) {
        System.out.println(movie);
        movieList.put(movie.getTitle(), movie);
        return "New movie "+ movie.getTitle() + " Added";
    }

    // THIS IS THE READ OPERATION
    // Note: To READ we use GET method
    @GetMapping("/movies/{Title}")
    public Movie getMovie(@PathVariable String title) {
        Movie m = movieList.get(title);
        return m;
    }

    // THIS IS THE UPDATE OPERATION
    // Note: To UPDATE we use PUT method
    @PutMapping("/movies/{title}")
    public Movie updateMovie(@PathVariable String title, @RequestBody Movie m) {
        movieList.replace(title, m);
        return movieList.get(title);
    }

    // THIS IS THE DELETE OPERATION
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/movies/{title}")
    public HashMap<String, Movie> deleteMovie(@PathVariable String title) {
        movieList.remove(title);
        return movieList;
    }
}

