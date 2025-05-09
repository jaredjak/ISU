//package myapp;
//
//
//import backendTables.Achievements.AchievementRepository;
//import backendTables.Achievements.Achievements;
//import backendTables.Achievements.AchievementsController;
//import backendTables.Club.Club;
//import backendTables.Club.ClubController;
//import backendTables.Club.ClubRepository;
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
//import java.util.ArrayList;
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
//@WebMvcTest(ClubController.class)
//public class ClubTest {
//    @Configuration
//    static class TestConfig {
//
//        @Bean
//        public CosmeticsRepository cosmeticsRepository() {
//            return mock(CosmeticsRepository.class);
//        }
//
//        @Bean
//        public ClubController clubController() {
//            return new ClubController();  // The controller bean
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
//
//        @Bean
//        public ClubRepository clubRepository() {
//            return mock(ClubRepository.class);
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
//    @MockBean
//    private ClubRepository clubRepository;
//
//    @Autowired
//    private MockMvc controller;
//
//    @Test
//    public void testPost() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//        Club c = new Club("Clubname", testUser);
//        Achievements a = new Achievements("testman");
//
//        when(achievementRepository.findByUsername("testman")).thenReturn(a);
//        when(clubRepository.findByName("Clubname")).thenReturn(c);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(testUser);
//
//        // Perform the POST request
//        controller.perform(post("/club/create/Clubname")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.clubName", is("Clubname")))
//                .andExpect(jsonPath("$.users", contains(testUser.getUsername())))
//                .andExpect(jsonPath("$.memberCount", is(1)));
//        // Check the id
//
//    }
//
//    @Test
//    public void getByID() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//        Club c = new Club("Clubname", testUser);
//        Achievements a = new Achievements("testman");
//
//        when(achievementRepository.findByUsername("testman")).thenReturn(a);
//        when(clubRepository.findById(1)).thenReturn(c);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(c);
//
//        // Perform the POST request
//        controller.perform(get("/club/get/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.clubName", is("Clubname")))
//                .andExpect(jsonPath("$.users", contains(testUser.getUsername())))
//                .andExpect(jsonPath("$.memberCount", is(1)));
//        // Check the id
//
//    }
//
//    @Test
//    public void getAllClubs() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//        Club c = new Club("Clubname", testUser);
//        Club c2 = new Club("Clubname2", testUser);
//        ArrayList<Club> clubs = new ArrayList<>();
//        clubs.add(c);
//        clubs.add(c2);
//        Achievements a = new Achievements("testman");
//
//        when(achievementRepository.findByUsername("testman")).thenReturn(a);
//        when(clubRepository.findById(1)).thenReturn(c);
//
//        when(clubRepository.findAll()).thenReturn(clubs);
//
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(any(User.class))).thenReturn(testUser);
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(c);
//        // Perform the POST request
//        controller.perform(get("/club/get/getAll")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].clubName").value("Clubname"))
//                .andExpect(jsonPath("$.length()", is(2)));
//        // Check the id
//
//    }
//
//    @Test
//    public void testUserAdd() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements("testman");
//
//        User testUser2 = new User("testerman", "jared11@iastate.edu", 233333333, "Password");
//
//        Club c = new Club();
//        c.addUser(testUser);
//        c.setClubName("Clubname");
//
//        when(achievementRepository.findByUsername(anyString())).thenReturn(a);
//        when(clubRepository.findById(1)).thenReturn(c);
//
//        when(userRepository.save(testUser)).thenReturn(testUser);
//
//        when(userRepository.findByUsername(anyString())).thenReturn(testUser2);
//        when(userRepository.save(testUser2)).thenReturn(testUser2);
//
//
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(c);
//
//        controller.perform(put("/club/addUser/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content("testerman"))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", is(true)));
//
//        testUser2.setClubId(1);
//        // Should make this return false V
//
//        controller.perform(put("/club/addUser/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content("testerman"))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", is(false)));
//
//        // Perform the POST request
//        controller.perform(get("/club/get/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content("testerman"))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.clubName", is("Clubname")))
//                .andExpect(jsonPath("$.memberCount", is(2)))
//                .andExpect(jsonPath("$.users", contains("testman", "testerman")));
//        // Check the id
//
//    }
//
//    @Test
//    public void testUserDelete() throws Exception {
//        // Set up MOCK methods for the REPO
//        User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//        Achievements a = new Achievements("testman");
//
//        User testUser2 = new User("testerman", "jared11@iastate.edu", 233333333, "Password");
//        Achievements a2 = new Achievements("testerman");
//
//        Club c = new Club("Clubname", testUser);
//        c.addUser(testUser2);
//
//        when(achievementRepository.findByUsername(anyString())).thenReturn(a);
//        when(clubRepository.findById(1)).thenReturn(c);
//
//        when(userRepository.save(testUser)).thenReturn(testUser);
//        when(userRepository.findByUsername("testerman")).thenReturn(testUser2);
//        when(userRepository.findByUsername("testman")).thenReturn(testUser);
//        when(userRepository.save(testUser2)).thenReturn(testUser2);
//
//
//
//
//        String jsonContent = new ObjectMapper().writeValueAsString(c);
//
//        controller.perform(put("/club/deleteUser/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content("testerman"))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", is(true)));
//
//        controller.perform(put("/club/deleteUser/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content("testerman"))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", is(false)));
//
//        // Perform the POST request
//        controller.perform(get("/club/get/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.clubName", is("Clubname")))
//                .andExpect(jsonPath("$.memberCount", is(1)))
//                .andExpect(jsonPath("$.users", contains("testman")));
//
//        controller.perform(put("/club/deleteUser/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content("testman"))  // Add the serialized object in the request body
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", is(true)));
//        // Check the id
//
//    }
//
//}
