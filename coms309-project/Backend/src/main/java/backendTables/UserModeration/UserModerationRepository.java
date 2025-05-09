package backendTables.UserModeration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserModerationRepository extends JpaRepository<UserModeration, Long> {
    UserModeration findByUserUsername(String username);
    UserModeration findByUserId(int userId);
    List<UserModeration> findByReportStatus(ModerationStatus status);
}
