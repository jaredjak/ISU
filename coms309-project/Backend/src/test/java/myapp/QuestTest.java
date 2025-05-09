//package myapp;
//
//
//import backendTables.Achievements.AchievementRepository;
//import backendTables.Bets.Bet;
//import backendTables.Bets.BetRepository;
//import backendTables.Cosmetics.*;
//import backendTables.Quests.QuestController;
//import backendTables.Quests.QuestProfile;
//import backendTables.Quests.QuestRepository;
//import backendTables.Users.User;
//import backendTables.Users.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.contains;
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.times;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(QuestController.class)
//public class QuestTest {
//    @Configuration
//    static class TestConfig {
//
//        @Bean
//        public CosmeticsRepository cosmeticsRepository() {
//            return mock(CosmeticsRepository.class);
//        }
//
//        @Bean
//        public QuestController questControllerController() {
//            return new QuestController();  // The controller bean
//        }
//
//        @Bean
//        public AchievementRepository achievementRepository() {
//            return mock(AchievementRepository.class);
//        }
//
//        @Bean
//        public UserRepository userRepository() {
//            return mock(UserRepository.class);
//        }
//
//        @Bean
//        public QuestRepository questRepository() {
//            return mock(QuestRepository.class);
//        }
//    }
//
//    @MockBean
//    private CosmeticsRepository cosmeticsRepository;  // Automatically creates a mock CosmeticsRepository bean
//
//    @MockBean
//    private AchievementRepository achievementRepositoryrepo;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private QuestRepository questRepository;
//
//    @Autowired
//    private MockMvc controller;
//
//    @Test
//    public void testProfileGet() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman2", "jared10@iastate.edu", 238383881, "password");
//        QuestProfile q = new QuestProfile(testUser.getUsername());
//
//        q.setUsername("testman");
//        testUser.setUsername("testman");
//
//        q.increaseCount(0, 5);
//        q.increaseCount(1, 10);
//        q.increaseCount(2, 25);
//        // mock the findById method
//        when(questRepository.findByUsername(testUser.getUsername())).thenReturn(q);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(q);
//
//        // Perform the POST request
//        controller.perform(get("/quests/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$.username", is("testman")))
//                .andExpect(jsonPath("$.questStates", contains(true, false, false)))
//                .andExpect(jsonPath("$.questCounts", contains(5, 10, 25)));
//    }
//
//    @Test
//    public void testQuestProgress() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//        QuestProfile q = new QuestProfile(testUser.getUsername());
//        q.increaseCount(0, 4);
//        q.increaseCount(1, 250);
//        // mock the findByUsername method
//        when(questRepository.findByUsername(testUser.getUsername())).thenReturn(q);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(q);
//
//        // Perform the request
//        controller.perform(get("/quests/toNextTier/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$", contains(0.8, 1.0, 0.0)));
//
//    }
//
//
//    @Test
//    public void timeCheck() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//        QuestProfile q = new QuestProfile();
//        q.setUsername(testUser.getUsername());
//        q.increaseCount(0, 4);
//        q.increaseCount(1, 250);
//        // mock the findByUsername method
//        when(questRepository.findByUsername(testUser.getUsername())).thenReturn(q);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(q);
//        // Perform the request
//        controller.perform(put("/quests/resetTime/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk());
//
//        q.revertDay();
//
//        controller.perform(put("/quests/resetTime/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk());
//
//    }
//
//
//    @Test
//    public void testIncreaseCountController() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//        QuestProfile q = new QuestProfile(testUser.getUsername());
//
//        // mock the findByUsername method
//        when(questRepository.findByUsername(testUser.getUsername())).thenReturn(q);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(q);
//
//        // Perform the request
//        controller.perform(put("/quests/0/3/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk());
//                // Check the id
//
//        controller.perform(put("/quests/2/500/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk());
//                // Check the id
//
//        controller.perform(get("/quests/testman")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.questCounts", contains(3, 0, 500)))
//                .andExpect(jsonPath("$.questStates", contains(false, false, true)));
//    }
//}
