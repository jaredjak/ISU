package backendTables.Users;

import java.util.List;

import backendTables.Club.Club;
import backendTables.Events.UserCreatedEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="User Activity", description="Store and Get User information")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // Create a new user with uniqueness checks
    @Operation(
            summary = "Create user",
            description = "Create a user within given info",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User created",
                            content = @Content(schema = @Schema(type="ResponseEntity", example = "{\"message\":\"success\"}"))
                    ),
            }
    )
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@Parameter(description = "User account and info to be added to databse. Contains username, password, SSN, email.", required = true)
                                            @RequestBody User newUser) {
        // Check if username exists
        if (userRepository.findByUsername(newUser.getUsername()) != null) {
            return ResponseEntity.badRequest().body("{\"error\":\"username already taken\"}");
        }
        // Check if email exists
        if (userRepository.findByemailId(newUser.getEmail()) != null) {
            return ResponseEntity.badRequest().body("{\"error\":\"email already in use\"}");
        }
        // Check if ssn exists
        if (userRepository.findByssn(newUser.getSSN()) != null) {
            return ResponseEntity.badRequest().body("{\"error\":\"identity fraud detected\"}");
        }

        User createdUser = userRepository.save(newUser);
        // Publish the event after creating the user.
        eventPublisher.publishEvent(new UserCreatedEvent(createdUser));
        return ResponseEntity.ok(createdUser);
    }

    @Operation(
            summary = "Get users",
            description = "Gets all users in the database.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved. Example of a user given, which will be typically given in a list format.",
                            content = @Content(schema = @Schema(implementation = User.class))
                    ),
            }
    )
    @GetMapping(path = "/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @Operation(
            summary = "Get user",
            description = "Gets the user of the given ID and its values.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved",
                            content = @Content(schema = @Schema(implementation = User.class))
                    ),
            }
    )
    @GetMapping(path = "/users/{id}")
    public User getUserById(@Parameter(description = "The ID of the user to be found", required = true)@PathVariable int id) {
        return userRepository.findById(id);
    }

    @Operation(
            summary = "Get user",
            description = "Gets the user of the given username and its values.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved",
                            content = @Content(schema = @Schema(implementation = User.class))
                    ),
            }
    )
    @GetMapping(path = "/users/username/{username}")
    public User getUserByUsername(@Parameter(description = "The username of the user to be found", required = true)@PathVariable String username) {
        if (username != null) {
            return userRepository.findByUsername(username);
        }
        return null;
    }

    @Operation(
            summary = "Password Update",
            description = "Updates the user's password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved",
                            content = @Content(schema = @Schema(type="ResponseEntity", example = "{\"message\":\"success\"}"))
                    ),
            }
    )
    @PutMapping(path = "/users/passwordReset/{username}/{newPassword}")
    public ResponseEntity<?> resetPassword(@Parameter(description = "username of the account to find",required = true) @PathVariable String username,
                                           @Parameter(description = "The new value to update the password to", required = true) @PathVariable String newPassword) {
        if (username != null) {
            User u = userRepository.findByUsername(username);
            if(u == null)
                return ResponseEntity.badRequest().body("{\"error\":\"user not found\"}");
            u.setPassword(newPassword);
            userRepository.save(u);
            return ResponseEntity.ok(u);
        }
        return ResponseEntity.badRequest().body("{\"error\":\"username not provided\"}");
    }

    @Operation(
            summary = "Username Update",
            description = "Updates the user's username",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved",
                            content = @Content(schema = @Schema(type="ResponseEntity", example = "{\"message\":\"success\"}"))
                    ),
            }
    )
    @PutMapping(path = "/users/usernameReset/{username}/{newUsername}")
    public ResponseEntity<?> resetUsername(@Parameter(description = "The username of the account to update username.",required = true)@PathVariable String username,
                                           @Parameter(description = "The new username to give the user")@PathVariable String newUsername) {
        if (username != null) {
            User u = userRepository.findByUsername(username);
            if (u == null) {
                return ResponseEntity.badRequest().body("{\"error\":\"user not found\"}");
            }
            // Check if new username already exists
            if (userRepository.findByUsername(newUsername) != null) {
                return ResponseEntity.badRequest().body("{\"error\":\"username already taken\"}");
            }
            u.setUsername(newUsername);
            userRepository.save(u);
            return ResponseEntity.ok(u);
        }
        return ResponseEntity.badRequest().body("{\"error\":\"username not provided\"}");
    }

    @Operation(
            summary = "Email Update",
            description = "Updates the user's email",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved",
                            content = @Content(schema = @Schema(type="ResponseEntity", example = "{\"message\":\"success\"}"))
                    ),
            }
    )
    @PutMapping(path = "/users/emailReset/{username}/{newEmail}")
    public ResponseEntity<?> resetEmail(@Parameter(description = "The username of the account to change the email of.",required = true)@PathVariable String username,
                                        @Parameter(description = "The new email to give the user", required = true) @PathVariable String newEmail) {
        if (username != null) {
            User u = userRepository.findByUsername(username);
            if(u == null) {
                return ResponseEntity.badRequest().body("{\"error\":\"user not found\"}");
            }
            // Check if new email already exists
            if (userRepository.findByemailId(newEmail) != null) {
                return ResponseEntity.badRequest().body("{\"error\":\"email already in use\"}");
            }
            u.setEmail(newEmail);
            userRepository.save(u);
            return ResponseEntity.ok(u);
        }
        return ResponseEntity.badRequest().body("{\"error\":\"username not provided\"}");
    }

    // Delete a user by id
    @Operation(
            summary = "Delete User",
            description = "Delete User of the given ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted",
                            content = @Content(schema = @Schema(type="String", example = "{\"message\":\"success\"}"))
                    ),
            }
    )
    @DeleteMapping("/users/{id}")
    public String deleteUser(@Parameter(description = "The id of the account to be deleted", required = true)@PathVariable int id) {
        if (userRepository.findById(id) != null) {
            userRepository.deleteById(id);
            return "{\"message\":\"success\"}";
        }
        return "{\"message\":\"failure\"}";
    }

    @Operation(
            summary = "Delete User",
            description = "Delete User of the given username",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted",
                            content = @Content(schema = @Schema(type="String", example = "{\"message\":\"success\"}"))
                    ),
            }
    )
    @DeleteMapping("users/username/{username}")
    public String deleteUser(@Parameter(description = "The username of the account to be deleted", required = true) @PathVariable String username) {
        if (userRepository.findByUsername(username) != null) {
            userRepository.deleteByUsername(username);
            return "{\"message\":\"success\"}";
        }
        return "{\"message\":\"failure\"}";
    }
}
