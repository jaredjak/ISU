package backendTables.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);
    User findByUsername(String username);
    void deleteByUsername(String username);
    void deleteById(int id);
    User findByemailId(String email);
    User findByssn(int ssn);
}
