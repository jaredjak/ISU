package backendTables.Events;
import backendTables.Users.User;

/**
 *
 * @author Carter Hauschildt
 *
 */

public class UserCreatedEvent {

    private final User user;

    public UserCreatedEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
