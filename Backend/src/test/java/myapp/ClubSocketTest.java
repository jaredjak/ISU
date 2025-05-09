//package myapp;
//
//import backendTables.Achievements.AchievementRepository;
//import backendTables.Achievements.AchievementService;
//import backendTables.Chat.*;
//import backendTables.Users.User;
//import backendTables.Users.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.websocket.RemoteEndpoint;
//import javax.websocket.Session;
//import java.io.IOException;
//import java.util.*;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = ChatSocket.class,
//        useDefaultFilters = false,
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ChatSocket.class)
//        }
//)
//public class ClubSocketTest {
//
//    @Configuration
//    public static class ChatSocketTestConfig {
//
//        @Bean
//        public ClubSocket clubSocket() {
//            return new ClubSocket();
//        }
//
//        @Bean
//        public ChatMessageService chatMessageService() {
//            return mock(ChatMessageService.class);
//        }
//
//        @Bean
//        public AchievementService achievementService() {
//            return mock(AchievementService.class);
//        }
//
//        @Bean
//        public UserRepository userRepository() {
//            return mock(UserRepository.class);
//        }
//
//        @Bean
//        public ChatMessageRepository chatMessageRepository() {
//            return mock(ChatMessageRepository.class);
//        }
//
//        @Bean
//        public AchievementRepository achievementRepository() {
//            return mock(AchievementRepository.class);
//        }
//    }
//
//    @Autowired
//    private ClubSocket clubSocket;
//
//    @Autowired
//    private ChatMessageService chatMessageService;
//
//    @Autowired
//    private AchievementService achievementService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private ChatMessageRepository chatMessageRepository;
//
//    @Autowired
//    private AchievementRepository achievementRepository;
//
//    private Session session;
//    private RemoteEndpoint.Basic remote;
//
//    @Before
//    public void setup() {
//        session = mock(Session.class);
//        remote = mock(RemoteEndpoint.Basic.class);
//        when(session.getBasicRemote()).thenReturn(remote);
//
//        // Reset static maps before each test
//        ChatSocket.getSessionUsernameMap().clear();
//    }
//
//    @Test
//    public void testOnOpen() throws IOException {
//        String username = "testman";
//        User u = new User();
//        u.setUsername(username);
//        u.setClubId(2);
//
//        List<ChatMessage> history = List.of(new ChatMessage("testman", "Hello!", null, 0));
//
//        when(userRepository.findByUsername(any(String.class))).thenReturn(u);
//        when(chatMessageService.getMessagesByClubId(0)).thenReturn(history);
//        when(achievementService.getSelected("testman")).thenReturn("Grub");
//        when(achievementService.getTier("testman")).thenReturn(1);
//
//        clubSocket.onOpen(session, 2, username);
//
//
//        verify(remote).sendText("System: testman has Joined the Chat\n");
//
//        assertTrue(ClubSocket.getSessionUsernameMap().containsValue("testman"));
//    }
//
//    @Test
//    public void testOnMessage() throws IOException {
//        String username = "testman";
//        User u = new User();
//        u.setUsername(username);
//        ClubSocket.getSessionUsernameMap().put(session, username);
//        ClubSocket.usernameSessionMap.put(username, session);
//
//        when(achievementService.getSelected(username)).thenReturn("Grub");
//        when(achievementService.getTier(username)).thenReturn(1);
//        when(userRepository.findByUsername(any(String.class))).thenReturn(u);
//
//        clubSocket.onMessage(session, "Hello everyone!", 2);
//
//        verify(remote).sendText("testman[Grub]: Hello everyone!");
//
//        clubSocket.onMessage(session, "!8ball test", 2);
//
//        verify(remote).sendText("testman[Grub]: !8ball test");
//
//
//        clubSocket.onMessage(session, "@testman test dm", 2);
//
//        verify(remote, times(2)).sendText("[DM] testman: @testman test dm");
//
//        clubSocket.onMessage(session, "!find testman", 2);
//
//        verify(remote).sendText("testman's messages:\n");
//    }
//
//    @Test
//    public void testOnClose() throws IOException {
//        String username = "testman";
//        ClubSocket.getSessionUsernameMap().put(session, username);
//
//        clubSocket.onClose(session, 2);
//
//        assertTrue(!ClubSocket.getSessionUsernameMap().containsKey(session));
//    }
//
//    @Test
//    public void testSendMessageToParticularUser() throws Exception {
//        String username = "testUser";
//        String message = "Hello User!";
//
//        // Mock the session to return the remote endpoint
//        when(session.getBasicRemote()).thenReturn(remote);
//
//        // Mock the usernameSessionMap
//        ClubSocket.getSessionUsernameMap().put(session, username);
//        ClubSocket.usernameSessionMap.put(username, session);
//
//        clubSocket.sendMessageToPArticularUser(username, message);
//
//        // Verify that the sendText method was called on the remote endpoint
//        verify(remote).sendText(message);
//    }
//
//    @Test
//    public void testSendMessagesToUser() throws Exception {
//        // Prepare test data
//        String username = "testUser";
//        String senderUsername = "senderUser";
//
//        // Create some mock chat messages
//        List<ChatMessage> messages = new ArrayList<>();
//        messages.add(new ChatMessage(senderUsername, "Hello!", null, 0));
//
//        // Mock the session to return the remote endpoint
//        when(session.getBasicRemote()).thenReturn(remote);
//
//        // Mock the usernameSessionMap and session retrieval for the user
//        ClubSocket.getSessionUsernameMap().put(session, username);
//        ClubSocket.usernameSessionMap.put(username, session);
//
//        // Mock the achievement service behavior
//        when(achievementService.getSelected(senderUsername)).thenReturn("Grub");
//        when(achievementService.getTier(senderUsername)).thenReturn(1);  // For the first test, the tier will be 1
//
//        // Call the method to test
//        clubSocket.sendMessageToPArticularUser(username, messages);
//
//        // Verify that the sendText method was called with the correct message format
//        verify(remote).sendText(senderUsername + "[Grub]: Hello!");
//
//        // Now, test when the tier is not 1 (for example, tier 2)
//        when(achievementService.getTier(senderUsername)).thenReturn(2); // Now, tier is 2
//
//        // Call the method again
//        clubSocket.sendMessageToPArticularUser(username, messages);
//
//        // Verify that the sendText method was called with the correct message format including the tier
//        verify(remote).sendText(senderUsername + "[Grub 2]: Hello!");
//    }
//}
