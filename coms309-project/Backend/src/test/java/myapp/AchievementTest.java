//package myapp;
//
//
//import backendTables.Achievements.AchievementRepository;
//import backendTables.Achievements.Achievements;
//import backendTables.Achievements.AchievementsController;
//import backendTables.Cosmetics.*;
//import backendTables.Quests.QuestRepository;
//import backendTables.Users.User;
//import backendTables.Users.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Test;
//import org.junit.runner.RunWith;
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
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(AchievementsController.class)
//public class AchievementTest {
//    @Configuration
//    static class TestConfig {
//
//        @Bean
//        public CosmeticsRepository cosmeticsRepository() {
//            return mock(CosmeticsRepository.class);
//        }
//
//        @Bean
//        public AchievementsController achievementsController() {
//            return new AchievementsController();  // The controller bean
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
//    private AchievementRepository achievementRepository;
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
//    public void testPost() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman2", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements(testUser.getUsername());
//        testUser.setUsername("testman");
//        a.setUsername("testman");
//
//        when(achievementRepository.findByUsername(testUser.getUsername())).thenReturn(a);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(a);
//
//        // Perform the POST request
//        controller.perform(post("/achievements/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk());
//                // Check the id
//
//    }
//
//
//    @Test
//    public void testGet() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman2", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements(testUser.getUsername());
//
//        testUser.setUsername("testman");
//        a.setUsername("testman");
//
//        a.increaseCount(4, 100);
//        a.increaseCount(0, 5);
//        a.increaseCount(1, 4);
//
//        when(achievementRepository.findByUsername(testUser.getUsername())).thenReturn(a);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(a);
//
//        // Perform the POST request
//        controller.perform(get("/achievements/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$.selected", is("Grub")))
//                .andExpect(jsonPath("$.tier", is(1)))
//                .andExpect(jsonPath("$.achievementStates", contains(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)));
//    }
//
//
//    @Test
//    public void testProgressGet() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman2", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements(testUser.getUsername());
//
//        testUser.setUsername("testman");
//        a.setUsername("testman");
//
//        a.increaseCount(4, 100);
//        a.increaseCount(0, 5);
//        a.increaseCount(1, 4);
//
//        when(achievementRepository.findByUsername(testUser.getUsername())).thenReturn(a);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(a);
//
//        // Perform the POST request
//        controller.perform(get("/achievements/getNextTiers/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$", contains(0.2, 0.8, 0.0, 0.0, 0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0)));
//    }
//
//
//    @Test
//    public void testGetSelected() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman2", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements(testUser.getUsername());
//
//        testUser.setUsername("testman");
//        a.setUsername("testman");
//
//        a.increaseCount(4, 100);
//        a.increaseCount(0, 5);
//        a.increaseCount(1, 4);
//
//        when(achievementRepository.findByUsername(testUser.getUsername())).thenReturn(a);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(a);
//
//        // Perform the POST request
//        controller.perform(get("/achievements/selected/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$", is("Grub")));
//    }
//
//    @Test
//    public void testSetSelected() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman2", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements(testUser.getUsername());
//
//        testUser.setUsername("testman");
//        a.setUsername("testman");
//
//        a.increaseCount(4, 100);
//        a.increaseCount(0, 5);
//        a.increaseCount(1, 4);
//
//        when(achievementRepository.findByUsername(testUser.getUsername())).thenReturn(a);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(a);
//
//        // Perform the POST request
//        controller.perform(put("/achievements/selected/testman/0")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$", is(true)));
//
//        controller.perform(put("/achievements/selected/testman/7")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$", is(false)));
//    }
//
//    @Test
//    public void nameGet() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman2", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements(testUser.getUsername());
//
//        testUser.setUsername("testman");
//        a.setUsername("testman");
//
//        a.increaseCount(4, 100);
//        a.increaseCount(0, 5);
//        a.increaseCount(1, 4);
//
//        when(achievementRepository.findByUsername(testUser.getUsername())).thenReturn(a);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(a);
//
//        // Perform the POST request
//        controller.perform(get("/achievements/getNames")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$", contains("Wormling", "Stupid Loser", "Fashion Forward", "Streaker",
//                        "Las Vegas Local", "Milked", "Accidental Genius", "Hated", "Dedicated",
//                        "Clubber", "Say My Name!", "Mouse Bites", "Alaskan Bull Worm",
//                        "GOD", "Oops! All In", "Grub")));
//    }
//
//    @Test
//    public void descGet() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman2", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements(testUser.getUsername());
//
//        testUser.setUsername("testman");
//        a.setUsername("testman");
//
//        a.increaseCount(4, 100);
//        a.increaseCount(0, 5);
//        a.increaseCount(1, 4);
//
//        when(achievementRepository.findByUsername(testUser.getUsername())).thenReturn(a);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(a);
//
//        // Perform the POST request
//        controller.perform(get("/achievements/getDescription")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$", contains("Win games",
//                        "Lose games",
//                        "Own Skins",
//                        "Gain consecutive win streak",
//                        "Win wormbucks from bets",
//                        "Lose wormbucks from bets",
//                        "Gain consecutive correct bet streak",
//                        "Get reported by other players",
//                        "Complete Daily Quests",
//                        "Join a club",
//                        "Buy the Walter skin",
//                        "Buy the House skin",
//                        "Buy the Bull worm skin",
//                        "Become an Admin",
//                        "Lose all of your wormbucks in a single bet",
//                        "Join Insatiable Insatiable Inchworms!")));
//    }
//
//    @Test
//    public void testSetProgress() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman2", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements(testUser.getUsername());
//
//        testUser.setUsername("testman");
//        a.setUsername("testman");
//
//        when(achievementRepository.findByUsername(testUser.getUsername())).thenReturn(a);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(a);
//
//        // Perform the POST request
//        controller.perform(put("/achievements/4/100/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//        ;
//        controller.perform(put("/achievements/0/5/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//        // Check the id
//        ;
//
//        controller.perform(put("/achievements/1/4/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//        // Check the id
//        ;
//
//        controller.perform(put("/achievements/selected/testman/7")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$", is(false)));
//
//        // Perform the POST request
//        controller.perform(get("/achievements/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                // Check the id
//                .andExpect(jsonPath("$.selected", is("Grub")))
//                .andExpect(jsonPath("$.tier", is(1)))
//                .andExpect(jsonPath("$.achievementStates", contains(1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1)));
//    }
//
//}
