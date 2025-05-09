//package myapp;
//
//import backendTables.Achievements.AchievementRepository;
//import backendTables.Achievements.AchievementService;
//import backendTables.Achievements.Achievements;
//import backendTables.Bets.Bet;
//import backendTables.Bets.BetRepository;
//import backendTables.Bets.BetService;
//import backendTables.Chat.ChatMessage;
//import backendTables.Chat.ChatMessageController;
//import backendTables.Chat.ChatMessageRepository;
//import backendTables.Chat.ChatMessageService;
//import backendTables.Cosmetics.Cosmetics;
//import backendTables.Cosmetics.CosmeticsRepository;
//import backendTables.Game.GamePlayer;
//import backendTables.Quests.QuestProfile;
//import backendTables.Quests.QuestRepository;
//import backendTables.Users.User;
//import backendTables.Users.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.websocket.RemoteEndpoint;
//import javax.websocket.Session;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.Map;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = ChatMessageController.class,
//        useDefaultFilters = false,
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ChatMessageController.class)
//        }
//)
//public class ServiceTests {
//    @Configuration
//    public static class TestConfig {
//        @Bean
//        public ChatMessageController chatMessageController() {
//            return new ChatMessageController();
//        }
//
//        @Bean
//        public QuestRepository questRepository() {
//            return mock(QuestRepository.class);
//        }
//        @Bean
//        public ChatMessageService chatMessageService() {
//            return new ChatMessageService();
//        }
//
//        @Bean
//        public BetService betService() {
//            return new BetService();
//        }
//
//        @Bean
//        public AchievementService achievementService() {
//            return new AchievementService();
//        }
//
//        @Bean
//        public ChatMessageRepository chatMessageRepository() {
//            return mock(ChatMessageRepository.class);
//        }
//
//        @Bean
//        public UserRepository userRepository() {
//            return mock(UserRepository.class);
//        }
//
//        @Bean
//        public BetRepository betRepository() {
//            return mock(BetRepository.class);
//        }
//
//        @Bean
//        public AchievementRepository achievementRepository() {
//            return mock(AchievementRepository.class);
//        }
//
//        @Bean
//        public CosmeticsRepository cosmeticsRepository() {
//            return mock(CosmeticsRepository.class);
//        }
//    }
//
//    @Autowired
//    private AchievementRepository achievementRepository;
//
//    @Autowired
//    private CosmeticsRepository cosmeticsRepository;
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private ChatMessageService chatMessageService;
//
//    @Autowired
//    private QuestRepository questRepository;
//
//    @Autowired
//    private BetService betService;
//
//    @Autowired
//    private AchievementService achievementService;
//
//    @Autowired
//    private ChatMessageRepository chatMessageRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BetRepository betRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        System.out.println("ChatMessageService: " + chatMessageService);
//        System.out.println("ChatMessageController in context: " +
//                (webApplicationContext.containsBean("chatMessageController") ? "YES" : "NO"));
//    }
//
//    @Test
//    public void chatServiceTests() {
//        String sender = "testman";
//        String message = "Test message";
//        ChatMessage c = new ChatMessage(sender, message, null, 12);
//        ChatMessage c2 = new ChatMessage(sender, message, null, 10);
//        List<ChatMessage> list = new ArrayList<>();
//        List<ChatMessage> list2 = new ArrayList<>();
//
//        list.add(c);
//        list2.add(c);
//        list2.add(c2);
//
//        when(chatMessageRepository.save(any(ChatMessage.class))).thenReturn(c);
//        when(chatMessageRepository.findByClubId(12)).thenReturn(list);
//        when(chatMessageRepository.findBySenderUsername(sender)).thenReturn(list2);
//        when(chatMessageRepository.findBySenderUsername(sender)).thenReturn(list2);
//        when(chatMessageRepository.findBySenderUsernameOrderByTimestampDesc(sender)).thenReturn(list2);
//
//        chatMessageService.saveMessage(sender, message, 12);
//
//        ArgumentCaptor<ChatMessage> messageCaptor = ArgumentCaptor.forClass(ChatMessage.class);
//        verify(chatMessageRepository).save(messageCaptor.capture());
//
//        ChatMessage saved = messageCaptor.getValue();
//        assertEquals("testman", saved.getSenderUsername());
//        assertEquals("Test message", saved.getMessage());
//        assertEquals(12, saved.getClubId());
//
//        List<ChatMessage> res = chatMessageService.getMessagesByClubId(12);
//        assertEquals(12, res.get(0).getClubId());
//
//        res = chatMessageService.getMessagesByClubId(2);
//        assertEquals(0, res.size());
//
//        res = chatMessageService.getMessagesByUser(sender);
//        assertEquals(2, res.size());
//        assertEquals(10, res.get(1).getClubId());
//        assertEquals("Test message", res.get(1).getMessage());
//
//        res = chatMessageService.getRawMessagesByUser(sender);
//        assertEquals(2, res.size());
//        assertEquals(10, res.get(1).getClubId());
//        assertEquals("Test message", res.get(1).getMessage());
//
//        res = chatMessageService.getRawMessagesByUser(sender, 12);
//        assertEquals(1, res.size());
//        assertEquals(12, res.get(0).getClubId());
//        assertEquals("Test message", res.get(0).getMessage());
//    }
//
//    @Test
//    public void broadcastTest() throws Exception {
//        // Mock user with correct club ID
//        User user = new User("testman", "email", 123456789, "password");
//        user.setClubId(42);
//
//        // Mock WebSocket session
//        Session session = mock(Session.class);
//        RemoteEndpoint.Basic remote = mock(RemoteEndpoint.Basic.class);
//
//        when(session.getBasicRemote()).thenReturn(remote);
//        when(userRepository.findByUsername("testman")).thenReturn(user);
//
//        // Create session map
//        Map<Session, String> sessionUsernameMap = new Hashtable<>();
//        sessionUsernameMap.put(session, "testman");
//
//        // Call the broadcast method
//        chatMessageService.broadcast("Hello testman!", 42, sessionUsernameMap);
//
//        // Verify message sent
//        verify(remote).sendText("Hello testman!");
//    }
//
//
//    @Test
//    public void betServiceTest() {
//        User user = new User("testman", "email", 123456789, "password");
//        Cosmetics c = new Cosmetics(user);
//        QuestProfile q = new QuestProfile("testman");
//        Achievements a = new Achievements("testman");
//
//        when(userRepository.findByUsername("testman")).thenReturn(user);
//        when(cosmeticsRepository.findByUsername("testman")).thenReturn(c);
//        when(questRepository.findByUsername("testman")).thenReturn(q);
//        when(achievementRepository.findByUsername("testman")).thenReturn(a);
//
//        when(userRepository.save(any(User.class))).thenReturn(user);
//        when(cosmeticsRepository.save(any(Cosmetics.class))).thenReturn(c);
//        when(questRepository.save(any(QuestProfile.class))).thenReturn(q);
//        when(achievementRepository.save(any(Achievements.class))).thenReturn(a);
//
//        Bet b = new Bet(100.0, "player2", 1.5, "testman", 2);
//        Bet b2 = new Bet(100.0, "player3", 2.0, "testman", 3);
//
//        List<Bet> postBets = new ArrayList<>();
//        postBets.add(b2);
//
//        List<Bet> bets = new ArrayList<>();
//        bets.add(b);
//        bets.add(b2);
//        when(betRepository.findAll()).thenReturn(bets);
//
//
//        List<GamePlayer> winners = new ArrayList<>();
//        GamePlayer winner = new GamePlayer(12, "Reginald");
//        winner.setPlayerIdentifier("player3");
//        winners.add(winner);
//
//        betService.payoutAllBets(winners, "2");
//        // Should only pay out one.
//        ArgumentCaptor<Cosmetics> cosmeticsCaptor = ArgumentCaptor.forClass(Cosmetics.class);
//        verify(cosmeticsRepository).save(cosmeticsCaptor.capture());
//
//        Cosmetics saved = cosmeticsCaptor.getValue();
//        // Make sure no money was won.
//        assertTrue(saved.getBalance() <= 1000);
//
//        c = new Cosmetics(user);
//        betService.payoutAllBets(winners, "3");
//
//        cosmeticsCaptor = ArgumentCaptor.forClass(Cosmetics.class);
//        verify(cosmeticsRepository, times(2)).save(cosmeticsCaptor.capture());
//
//        saved = cosmeticsCaptor.getValue();
//        // Make sure money was paid out.
//        assertTrue(saved.getBalance() > 1000);
//    }
//
//    @Test
//    public void setAchievementServiceTest() {
//        User u = new User();
//        u.setUsername("testman");
//        Achievements a = new Achievements("testman");
//        when(achievementRepository.findByUsername("testman")).thenReturn(a);
//
//        assertEquals(1, achievementService.getTier("testman"));
//        assertEquals("Grub", achievementService.getSelected("testman"));
//    }
//}