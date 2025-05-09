package org.example.streamingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.example.streamingservice.movies.MovieController;
import org.example.streamingservice.shows.ShowController;
@SpringBootApplication
public class StreamingServiceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(StreamingServiceApplication.class, args);

        ShowController controller = new ShowController();
        MovieController mController = new MovieController();


    }
}
