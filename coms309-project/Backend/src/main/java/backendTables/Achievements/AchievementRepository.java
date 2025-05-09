package backendTables.Achievements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievements, Integer>{
    Achievements findById(int id);

    Achievements findByUsername(String username);


}
