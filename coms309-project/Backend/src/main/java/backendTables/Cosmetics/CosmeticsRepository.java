package backendTables.Cosmetics;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CosmeticsRepository extends JpaRepository<Cosmetics, Integer> {
    Cosmetics findById(int id);
    void deleteById(int id);
    Cosmetics findByUsername(String username);
    void deleteByUsername(String username);
}

