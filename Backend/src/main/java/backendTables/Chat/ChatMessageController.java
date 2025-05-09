package backendTables.Chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/user/{username}")
    public List<ChatMessage> getMessagesByUser(@PathVariable String username) {
        return chatMessageService.getRawMessagesByUser(username);
    }

    @GetMapping("/club/{id}")
    public List<ChatMessage> getMessagesByClub(@PathVariable int id) {
        return chatMessageService.getMessagesByClubId(id);
    }
}

