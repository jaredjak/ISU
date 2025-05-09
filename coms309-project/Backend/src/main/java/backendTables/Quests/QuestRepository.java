package backendTables.Quests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestRepository extends JpaRepository<QuestProfile, Integer>{
    QuestProfile findById(int id);

    QuestProfile findByUsername(String username);
}
