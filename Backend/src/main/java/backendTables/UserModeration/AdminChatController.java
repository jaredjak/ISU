package backendTables.UserModeration;

import backendTables.Chat.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Admin Chat Management", description = "Endpoints for retrieving user chat history for moderation purposes.")
@RestController
@RequestMapping("/admin/chat")
public class AdminChatController {

    @Autowired
    private ChatMessageService chatMessageService;

    @Operation(summary = "Get user chat history", description = "Retrieves all chat messages sent by a specific user.")
    @ApiResponse(
            responseCode = "200",
            description = "User chat history retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChatMessage.class))
    )
    @GetMapping("/history/{username}")
    public ResponseEntity<?> getUserChatHistory(@PathVariable String username) {
        List<ChatMessage> history = chatMessageService.getMessagesByUser(username);
        return ResponseEntity.ok(history);
    }
}
