package third.experiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExperimentApplication
{
  private static final Logger log = LoggerFactory.getLogger(ExperimentApplication.class);

  public static void main(String[] args)
  {
    SpringApplication.run(ExperimentApplication.class, args);
  }

  @Bean
  public CommandLineRunner display(UserRepository userRepository)
  {
    return (args) -> {
      userRepository.save(new User("Carter", "Hauschildt"));
      userRepository.save(new User("James", "Daniels"));
      userRepository.save(new User("Snoop", "Dogg"));
      userRepository.save(new User("Avery", "Daniels"));
      userRepository.save(new User("Carter", "Smith"));
      userRepository.save(new User("Brent", "christensen"));
      userRepository.save(new User("Joe", "Jorgensen"));
      userRepository.save(new User("Brenda", "Kensworth"));

      //display all users
      log.info("All Users in repository");
      log.info("---------------------------");
      for (User cus : userRepository.findAll())
      {
        log.info(cus.toString());
      }
      log.info("\n");

      //display all users with last name "Daniels"
      log.info("All Users in repository with last name Daniels");
      log.info("-------------------------------------------------");
      for (User cus : userRepository.findByLastName("Daniels"))
      {
        log.info(cus.toString());
      }
      log.info("\n");

      //display all users with first name "Carter"
      log.info("All Users in repository with first name Carter");
      log.info("------------------------------------------------");
      for (User cus : userRepository.findByFirstName("Carter"))
      {
        log.info(cus.toString());
      }
      log.info("\n");

    };
  }
}
