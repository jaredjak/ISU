package org.example.streamingservice.shows;

import org.example.streamingservice.movies.Movie;
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
public class ShowController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<String, Show> showList = new  HashMap<>();

    // THIS IS THE LIST OPERATION
    // Note: To LIST, we use the GET method
    @GetMapping("/shows")
    public  HashMap<String,Show> getAllShows() {
        return showList;
    }

    // THIS IS THE CREATE OPERATION
    // Note: To CREATE we use POST method
    @PostMapping("/shows")
    public  String createShow(@RequestBody Show s) {
        System.out.println(s);
        showList.put(s.getTitle(), s);
        return "New show "+ s.getTitle() + " Added";
    }

    // THIS IS THE READ OPERATION
    // Note: To READ we use GET method
    @GetMapping("/shows/{title}")
    public Show getShow(@PathVariable String title) {
        Show s = showList.get(title);
        return s;
    }

    // THIS IS THE UPDATE OPERATION
    // Note: To UPDATE we use PUT method
    @PutMapping("/shows/{title}")
    public Show updateShow(@PathVariable String title, @RequestBody Show s) {
        showList.replace(title, s);
        return showList.get(title);
    }

    // THIS IS THE DELETE OPERATION
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/shows/{title}")
    public HashMap<String, Show> deleteShow(@PathVariable String title) {
        showList.remove(title);
        return showList;
    }
}

