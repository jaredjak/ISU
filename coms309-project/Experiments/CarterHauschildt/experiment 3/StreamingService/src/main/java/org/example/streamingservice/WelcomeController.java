package org.example.streamingservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Hello World Controller to display the string returned
 *
 * @author Vivek Bengre
 */

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "welcome to my streaming service \n"+
                "visit /shows and use the format {\"Title\" : \" \", \"Rating\" : \" \", \"SeasonCount\" : \" \", \"EpisodesPerSeason\" : \" \"} to add shows \n" +
                "or visit /movies and use the format{\"title\" : \" \", \"rating\" : \" \", \"runtime\" : \" \", \"director\" : \" \"} to add movies";
    }
}
