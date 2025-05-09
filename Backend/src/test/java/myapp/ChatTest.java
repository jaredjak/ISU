//package myapp;
//
//import backendTables.Chat.ChatMessage;
//import backendTables.Chat.ChatMessageController;
//import backendTables.Chat.ChatMessageRepository;
//import backendTables.Chat.ChatMessageService;
//import backendTables.Users.User;
//import backendTables.Users.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import static org.hamcrest.Matchers.is;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(controllers = ChatMessageController.class,
//        useDefaultFilters = false,
//        includeFilters = {
//                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ChatMessageController.class)
//        }
//)
//public class ChatTest {
//    @Configuration
//    public static class TestConfig {
//        @Bean
//        public ChatMessageController chatMessageController() {
//            return new ChatMessageController();
//        }
//
//        @Bean
//        public ChatMessageService chatMessageService() {
//            return mock(ChatMessageService.class);
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
//    }
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private ChatMessageService chatMessageService;
//
//    @Autowired
//    private ChatMessageRepository chatMessageRepository;
//
//    @Autowired
//    private UserRepository userRepository;
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
//    public void testProfileGet() throws Exception {
//        // Set up test data
//        ChatMessage c1 = new ChatMessage("testman", "Hey it's a test", null, 0);
//        ChatMessage c2 = new ChatMessage();
//        c2.setSenderUsername("testman");
//        c2.setMessage("Another test message");
//
//        ArrayList<ChatMessage> arrayList = new ArrayList<>(Arrays.asList(c1, c2));
//
//        // Mock the service method
//        when(chatMessageService.getRawMessagesByUser("testman")).thenReturn(arrayList);
//
//        // Perform the test
//        mockMvc.perform(get("/chat/user/testman"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].senderUsername", is("testman")))
//                .andExpect(jsonPath("$[0].message", is("Hey it's a test")))
//                .andExpect(jsonPath("$[1].senderUsername", is("testman")))
//                .andExpect(jsonPath("$[1].message", is("Another test message")));
//
//        // Verify the service was called
//        verify(chatMessageService, times(1)).getRawMessagesByUser("testman");
//    }
//
//    @Test
//    public void testClubGet() throws Exception {
//        // Set up test data
//        ChatMessage c1 = new ChatMessage("testman", "Hey it's a test", null, 0);
//        ChatMessage c2 = new ChatMessage();
//        ChatMessage c3 = new ChatMessage("testman", "Message three", null);
//
//        c3.setClubId(13);
//        c3.setServerId(13);
//        c2.setTimestamp(null);
//
//        c2.setSenderUsername("testman");
//        c2.setMessage("Another test message");
//        c2.setClubId(13);
//        c2.setServerId(13);
//        ArrayList<ChatMessage> arrayList = new ArrayList<>(Arrays.asList(c1, c2, c3));
//        ArrayList<ChatMessage> clubList = new ArrayList<>(Arrays.asList(c2, c3));
//        // Mock the service method
//        when(chatMessageService.getRawMessagesByUser("testman")).thenReturn(arrayList);
//        when(chatMessageService.getMessagesByClubId(c2.getClubId())).thenReturn(clubList);
//
//        // Perform the test
//        mockMvc.perform(get("/chat/club/13"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[1].senderUsername", is("testman")))
//                .andExpect(jsonPath("$[1].message", is("Message three")))
//                .andExpect(jsonPath("$[1].clubId", is(13)))
//                .andExpect(jsonPath("$[0].clubId", is(13)))
//                .andExpect(jsonPath("$[0].senderUsername", is("testman")))
//                .andExpect(jsonPath("$[0].message", is("Another test message")));
//
//        // Verify the service was called
//        verify(chatMessageService, times(1)).getRawMessagesByUser("testman");
//    }
//}