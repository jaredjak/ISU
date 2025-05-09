package backendTables.Club;

import backendTables.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Integer> {
    Club findById(int id);
    Club findByName(String clubname);
    List<Club> findAll();
}