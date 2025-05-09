package backendTables;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan(basePackages = "backendTables")
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

