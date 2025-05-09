package backendTables.Bets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Integer> {
    Bet findById(int id);
    List<Bet> findByLobbyId(int id);
    void deleteById(int id);
    List<Bet> findAll();
}