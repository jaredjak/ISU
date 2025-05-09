package backendTables.Chat;

import backendTables.Achievements.AchievementService;
import backendTables.Game.CustomSpringConfigurator;
import backendTables.Users.UserRepository;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.*;

@Controller // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{clubId}/{username}", configurator = CustomSpringConfigurator.class) // this is Websocket url
public class ClubSocket {

    // cannot autowire static directly (instead we do it by the below method)
    private static ChatMessageService chatMessageService;

    private static AchievementService achievementService;

    public static Map<Session, String> getSessionUsernameMap() {
        return sessionUsernameMap;
    }

    /*
     * Grabs the ChatMessageService singleton from the Spring Application
     * Context.  This works because of the @Controller annotation on this
     * class and because the variable is declared as static.
     * There are other ways to set this. However, this approach is
     * easiest.
     */
    @Autowired
    public void setChatMessageService(ChatMessageService service) {
        chatMessageService = service;
    }

    // Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
    public static Map<String, Session> usernameSessionMap = new Hashtable<>();

    private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);

    private static UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository repository) {
        ClubSocket.userRepository = repository;
    }

    @Autowired
    public void setAchievementService(AchievementService service) {this.achievementService = service;}

    @OnOpen
    public void onOpen(Session session, @PathParam("clubId") int id, @PathParam("username") String username)
            throws IOException {
        logger.info("Entered into Open");

        // store connecting user information
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);

        //Send chat history to the newly connected user
        sendMessageToPArticularUser(username, chatMessageService.getMessagesByClubId(id));

        // broadcast that new user joined

        String message = "System:  " + username + " has Joined the Chat\n";
        broadcast(message, id);
    }


    @OnMessage
    public void onMessage(Session session, String message, @PathParam("clubId") int id) throws IOException {

        // Handle new messages
        logger.info("Entered into Message: Got Message:" + message);
        String username = sessionUsernameMap.get(session);
        String[] responses = {"It is certain.", "It is decidedly so.", "Without a doubt",
                "Most likely", "Signs point to yes", "Outlook good", "Cannot predict now", "Ask again later",
                "Concentrate and ask again", "I hate you.", "Don't count on it.", "PLEASE! This is not a joke! My conscience " +
                "is being held hostage in the mainframe.", "My sources say no", "Outlook not so good", "Very doubtful",
                "No. Just no.", "Huh?", "Absolutely... not."};
        // Direct message to a user using the format "@username <message>"
        if (message.startsWith("@")) {
            String destUsername = message.split(" ")[0].substring(1);

            // send the message to the sender and receiver
            sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
            sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
            // Saving chat history to repository
            chatMessageService.saveMessage(username, message, id);
        }
        else if (message.startsWith("!8ball")) {
            int j = (int) (Math.random() * 18);
            int i = achievementService.getTier(username);
            if (i != 1) usernameSessionMap.get(username).getBasicRemote().sendText(username + "[" + achievementService.getSelected(username) + " " + i +  "]: " + message);
            else {
                usernameSessionMap.get(username).getBasicRemote().sendText(username + "[" + achievementService.getSelected(username) + "]: " + message);
            }
            broadcast("8BallAI: " + responses[j], id);
        }
        else if (message.startsWith("!find")) {
            if (message.split(" ").length > 1) {
                String destUsername = message.split(" ")[1].substring(0);
                List<ChatMessage> messages = chatMessageService.getRawMessagesByUser(destUsername, id);
                int len = messages.size();
                String[] contents = new String[len];
                for (int i = 0; i < len; i++) {
                    contents[i] = messages.get(i).getMessage();
                }
                String msgs = Arrays.toString(contents);
                // send the messages of one user to another
                sendMessageToPArticularUser(username, destUsername + "'s messages:\n"
                        + msgs.substring(1, msgs.length()-1));
            }
        }
        else { // broadcast
            int i = achievementService.getTier(username);
            if (i != 1) {
                usernameSessionMap.get(username).getBasicRemote().sendText(username + "[" + achievementService.getSelected(username) + " " + i +  "]: " + message);
                chatMessageService.saveMessage(username, message, id);
            }
            else {
                chatMessageService.saveMessage(username, message, id);
                usernameSessionMap.get(username).getBasicRemote().sendText(username + "[" + achievementService.getSelected(username) +  "]: " + message);
            }
        }
    }


    @OnClose
    public void onClose(Session session, @PathParam("clubId") int id) throws IOException {
        logger.info("Entered into Close");

        // remove the user connection information
        String username = sessionUsernameMap.get(session);
        sessionUsernameMap.remove(session);
        usernameSessionMap.remove(username);

        // broadcase that the user disconnected
        String message = "System: " + username + " disconnected";
        broadcast(message, id);
    }


    public void sendMessageToPArticularUser(String username, String message) {
        try {
            usernameSessionMap.get(username).getBasicRemote().sendText(message);
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    public void sendMessageToPArticularUser(String username, List<ChatMessage> messages){
        try {
            for (ChatMessage m: messages) {
                int i = achievementService.getTier(m.getSenderUsername());
                if (i != 1) usernameSessionMap.get(username).getBasicRemote().sendText(m.getSenderUsername() + "[" + achievementService.getSelected(m.getSenderUsername()) + " " + i +  "]: " + m.getMessage());
                else {
                    usernameSessionMap.get(username).getBasicRemote().sendText(m.getSenderUsername() + "[" + achievementService.getSelected(m.getSenderUsername()) + "]: " + m.getMessage());
                }
            }
        }
        catch (IOException e) {
            logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }


    private void broadcast(String message, int id) {
        sessionUsernameMap.forEach((session, username) -> {
            try {
                if (userRepository.findByUsername(username).getClubId() == id) {
                    session.getBasicRemote().sendText(message);
                }
            }
            catch (IOException e) {
                logger.info("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }

        });

    }

} // end of Class
