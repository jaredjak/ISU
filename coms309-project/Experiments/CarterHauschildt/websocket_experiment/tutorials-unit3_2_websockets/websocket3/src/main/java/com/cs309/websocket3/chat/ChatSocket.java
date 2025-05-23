package com.cs309.websocket3.chat;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/chat/{username}")  // this is the Websocket url
public class ChatSocket {

  // cannot autowire static directly. instead, do it by the below method
	private static MessageRepository msgRepo; 

	/*
   * Grabs the MessageRepository singleton from the Spring Application
   * Context.  This works because of the @Controller annotation on this
   * class and because the variable is declared as static.
   * There are other ways to set this. However, this approach is
   * easiest.
	 */
	@Autowired
	public void setMessageRepository(MessageRepository repo) {
		msgRepo = repo;  // we are setting the static variable
	}

	// Store all socket session and their corresponding username.
	private static Map<Session, String> sessionUsernameMap = new Hashtable<>();
	private static Map<String, Session> usernameSessionMap = new Hashtable<>();

	private final Logger logger = LoggerFactory.getLogger(ChatSocket.class);
	private static Map<Session, Long> sessionStartTime = new Hashtable<>();

	//wscat -c ws://localhost:8080/chat/username
	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		sessionUsernameMap.put(session, username);
		usernameSessionMap.put(username, session);
		sessionStartTime.put(session, System.currentTimeMillis());

		sendMessageToPArticularUser(username, "Welcome " + username + "!");
		broadcast("User " + username + " has joined the chat");
	}



	private String formatMessage(String username, String message) {
		return "[" + new Date() + "] " + username + ": " + message;
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		logger.info("Received message: {} from session id {}", message, session.getId());
		String username = sessionUsernameMap.get(session);

		// Handle custom commands
		if(message.equalsIgnoreCase("/users")) {
			String users = "Connected users: " + usernameSessionMap.keySet();
			sendMessageToPArticularUser(username, users);
			return;
		} else if(message.equalsIgnoreCase("/time")) {
			sendMessageToPArticularUser(username, "Server time: " + new Date());
			return;
		}

		// Handle ping-pong
		if("ping".equalsIgnoreCase(message.trim())) {
			sendMessageToPArticularUser(username, "pong");
			return;
		}

		// Format the message (includes timestamp)
		String formattedMsg = formatMessage(username, message);

		// Direct message functionality (e.g., @bob message) or broadcast
		if (message.startsWith("@")) {
			String destUsername = message.split(" ")[0].substring(1);
			String dmMsg = "[DM] " + formattedMsg;
			sendMessageToPArticularUser(destUsername, dmMsg);
			sendMessageToPArticularUser(username, dmMsg);
		} else {
			broadcast(formattedMsg);
		}

		// Save the message to the repository
		msgRepo.save(new Message(username, message));
	}



	@OnClose
	public void onClose(Session session) throws IOException {
		String username = sessionUsernameMap.get(session);
		sessionUsernameMap.remove(session);
		usernameSessionMap.remove(username);

		// Calculate connection duration
		Long startTime = sessionStartTime.get(session);
		long duration = (startTime != null) ? System.currentTimeMillis() - startTime : 0;
		sessionStartTime.remove(session);

		broadcast("User " + username + " disconnected after " + duration / 1000 + " seconds");
	}


	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
		logger.info("Entered into Error");
		throwable.printStackTrace();
	}


	private void sendMessageToPArticularUser(String username, String message) {
		try {
			usernameSessionMap.get(username).getBasicRemote().sendText(message);
		} 
    catch (IOException e) {
			logger.info("Exception: " + e.getMessage().toString());
			e.printStackTrace();
		}
	}


	private void broadcast(String message) {
		sessionUsernameMap.forEach((session, username) -> {
			try {
				session.getBasicRemote().sendText(message);
			} 
      catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}

		});

	}
	

  // Gets the Chat history from the repository
	private String getChatHistory() {
		List<Message> messages = msgRepo.findAll();
    
    // convert the list to a string
		StringBuilder sb = new StringBuilder();
		if(messages != null && messages.size() != 0) {
			for (Message message : messages) {
				sb.append(message.getUserName() + ": " + message.getContent() + "\n");
			}
		}
		return sb.toString();
	}

} // end of Class
