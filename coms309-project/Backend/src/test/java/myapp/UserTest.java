//package myapp;
//
//import backendTables.Achievements.AchievementRepository;
//import backendTables.Club.ClubController;
//import backendTables.Club.ClubRepository;
//import backendTables.Cosmetics.CosmeticsRepository;
//import backendTables.Quests.QuestRepository;
//import backendTables.Users.User;
//import backendTables.Users.UserController;
//import backendTables.Users.UserRepository;
//import com.fasterxml.jackson.core.JsonProcessingException;
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
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//import static org.hamcrest.Matchers.contains;
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(UserController.class)
//public class UserTest {
//    @Configuration
//    static class TestConfig {
//        @Bean
//        public UserRepository userRepository() {
//            return mock(UserRepository.class);
//        }
//
//        @Bean
//        public UserController userController() {
//            return new UserController();
//        }
//    }
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @Autowired
//    private MockMvc controller;
//
//    @Test
//    public void testPost() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        when(userRepository.save(any(User.class))).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(post("/users")
//                .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is("testman")))
//                .andExpect(jsonPath("$.email", is("email")))
//                .andExpect(jsonPath("$.ssn", is(123456789)));
//    }
//
//
//    @Test
//    public void testDuplicatePostUsername() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        // If we try to make a new users with these stats we'll see a 400 error.
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findByUsername("testman")).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().is4xxClientError());
//    }
//
//    @Test
//    public void testDuplicatePostEmail() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        // If we try to make a new users with these stats we'll see a 400 error.
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findByemailId("email")).thenReturn(u);
//        when(userRepository.findByssn(123456789)).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().is4xxClientError());
//    }
//
//    @Test
//    public void testDuplicatePostSSN() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        // If we try to make a new users with these stats we'll see a 400 error.
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findByssn(123456789)).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(post("/users")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().is4xxClientError());
//    }
//
//    @Test
//    public void testGetAll() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//        User u2 = new User("testman2", "email2", 123456780, "password");
//
//        List<User> users = new ArrayList<>();
//        users.add(u);
//        users.add(u2);
//
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findAll()).thenReturn(users);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(users);
//        controller.perform(get("/users")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].username", is("testman")))
//                .andExpect(jsonPath("$[0].email", is("email")))
//                .andExpect(jsonPath("$[0].ssn", is(123456789)))
//
//                .andExpect(jsonPath("$[1].username", is("testman2")))
//                .andExpect(jsonPath("$[1].email", is("email2")))
//                .andExpect(jsonPath("$[1].ssn", is(123456780)));
//    }
//
//    @Test
//    public void testGetOneId() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//        User u2 = new User("testman2", "email", 123456789, "password");
//
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findById(1)).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(get("/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is("testman")))
//                .andExpect(jsonPath("$.email", is("email")))
//                .andExpect(jsonPath("$.ssn", is(123456789)));
//
//    }
//
//    @Test
//    public void testGetOneUsername() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findByUsername("testman")).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(get("/users/username/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is("testman")))
//                .andExpect(jsonPath("$.email", is("email")))
//                .andExpect(jsonPath("$.ssn", is(123456789)));
//    }
//
//    @Test
//    public void testPasswordPut() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findByUsername("testman")).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(put("/users/passwordReset/testman/newPassword")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is("testman")))
//                .andExpect(jsonPath("$.email", is("email")))
//                .andExpect(jsonPath("$.password", is("newPassword")));
//    }
//
//    @Test
//    public void testUsernameResetPut() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findByUsername("testman")).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(put("/users/usernameReset/testman/newName")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is("newName")))
//                .andExpect(jsonPath("$.email", is("email")))
//                .andExpect(jsonPath("$.ssn", is(123456789)));
//    }
//
//    @Test
//    public void testEmailPut() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findByUsername("testman")).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(put("/users/emailReset/testman/newEmail")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.username", is("testman")))
//                .andExpect(jsonPath("$.email", is("newEmail")))
//                .andExpect(jsonPath("$.ssn", is(123456789)));
//    }
//
//    @Test
//    public void testDeleteId() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findByUsername("testman")).thenReturn(u);
//        when(userRepository.findById(1)).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(delete("/users/1")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(content().string("{\"message\":\"success\"}"));
//
//        controller.perform(delete("/users/11")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(content().string("{\"message\":\"failure\"}"));
//    }
//
//    @Test
//    public void testDeleteUsername() throws Exception {
//        User u = new User("testman", "email", 123456789, "password");
//
//        when(userRepository.save(any(User.class))).thenReturn(u);
//        when(userRepository.findByUsername("testman")).thenReturn(u);
//
//        String jsonContent = new ObjectMapper().writeValueAsString(u);
//
//        controller.perform(delete("/users/username/testman")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(content().string("{\"message\":\"success\"}"));
//
//        controller.perform(delete("/users/username/test")
//                        .contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//                        .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(content().string("{\"message\":\"failure\"}"));
//    }
//
//}
