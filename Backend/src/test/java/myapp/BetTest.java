//package myapp;
//
//// Import Java libraries
//import java.lang.reflect.Array;
//import java.util.List;
//import java.util.ArrayList;
//
//// import junit/spring tests
//import backendTables.Achievements.AchievementRepository;
//import backendTables.Achievements.Achievements;
//import backendTables.Bets.Bet;
//import backendTables.Bets.BetController;
//import backendTables.Bets.BetRepository;
//import backendTables.Cosmetics.*;
//import backendTables.Users.User;
//import backendTables.Users.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.jboss.jandex.Main;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//
//
//import static org.hamcrest.Matchers.contains;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//import org.springframework.http.MediaType;
//import org.yaml.snakeyaml.error.Mark;
//
//// import mockito related
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(BetController.class)
//public class BetTest {
//
//	@Configuration
//	static class TestConfig {
//
//		@Bean
//		public CosmeticsRepository cosmeticsRepository() {
//			return mock(CosmeticsRepository.class);
//		}
//
//		@Bean
//		public BetController cosmeticsController() {
//			return new BetController();  // The controller bean
//		}
//
//		@Bean
//		public MarketplaceRepository marketplaceRepository() {
//            return mock(MarketplaceRepository.class);
//		}
//
//		@Bean
//		public AchievementRepository achievementRepository() {
//			return mock(AchievementRepository.class);
//		}
//
//		@Bean
//		public BetRepository betRepository() {
//			return mock(BetRepository.class);
//		}
//
//		@Bean
//		public UserRepository userRepository() {
//			return mock(UserRepository.class);
//		}
//
//	}
//
//	@MockBean
//	private CosmeticsRepository repo;  // Automatically creates a mock CosmeticsRepository bean
//
//	@MockBean
//	private MarketplaceRepository mrepo;
//
//	@MockBean
//	private AchievementRepository arepo;
//
//	@MockBean
//	private BetRepository brepo;
//
//	@MockBean
//	private UserRepository userRepository;
//
//	@MockBean
//	private Wormbox wormbox;  // Mock the Wormbox class
//
//	@Autowired
//	private MockMvc controller;
//
//	@Test
//	public void testPost() throws Exception {
//		// Set up MOCK methods for the REPO
//		User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//		Cosmetics c = new Cosmetics(testUser);
//		Bet b = new Bet(100, "player1", 1.25, "testman", 0);
//
//		// mock the save() method to save argument to the list
//		when(brepo.save(any(Bet.class)))
//				.thenReturn(b);
//
//		// mock the findById method
//		when(repo.findById(testUser.getId())).thenReturn(c);
//
//		when(userRepository.findByUsername("testman")).thenReturn(testUser);
//		when(userRepository.save(any(User.class)))
//				.thenReturn(testUser);
//		when(repo.findByUsername("testman")).thenReturn(c);
//
//
//		String jsonContent = new ObjectMapper().writeValueAsString(b);
//
//		// Perform the POST request
//		controller.perform(post("/placeBet")
//						.contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//						.content(jsonContent))  // Add the serialized object in the request body
//				.andExpect(status().isOk())
//				// Check the id
//				.andExpect(jsonPath("$.amount", is(100.0)))
//				.andExpect(jsonPath("$.positionBet", is("player1")))
//				.andExpect(jsonPath("$.mult", is(1.25)))
//				.andExpect(jsonPath("$.username", is("testman")))
//				.andExpect(jsonPath("$.lobbyId", is(0)));
//
//	}
//
//	@Test
//	public void betPayout() throws Exception {
//		// Set up MOCK methods for the REPO
//		User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//		Cosmetics c = new Cosmetics(testUser);
//		Bet b = new Bet(100, "player1", 1.25, "testman", 1);
//
//
//		// mock the save() method to save argument to the list
//		when(brepo.save(any(Bet.class)))
//				.thenReturn(b);
//
//		// mock the findById method
//		when(repo.findById(testUser.getId())).thenReturn(c);
//		when(repo.findByUsername("testman")).thenReturn(c);
//
//		when(arepo.findByUsername("testman")).thenReturn(new Achievements("testman"));
//
//		when(brepo.findAll()).thenReturn(List.of(b));
//		when(brepo.findById(b.getId())).thenReturn(b);
//		when(brepo.findByLobbyId(b.getLobbyId())).thenReturn(List.of(b));  // Ensure this returns the correct Bet object
//		when(userRepository.findByUsername("testman")).thenReturn(testUser);
//
//		// Perform the payout request
//		controller.perform(delete("/cashoutBets/player1/3")) //Test if in a different server
//				.andExpect(status().isOk());
//
//		verify(repo, times(0)).save(c);
//		// Since there shouldnt be any bets with this id no saves will occur
//
//
//		controller.perform(delete("/cashoutBets/player1/1"))
//				.andExpect(status().isOk());//Test if in same server but loss
//
//		ArgumentCaptor<Cosmetics> captor = ArgumentCaptor.forClass(Cosmetics.class);
//
//		 // Capture the argument passed to save()
//		// Verify that the save() method is called on the cosmeticsRepository
//		verify(repo, times(1)).save(captor.capture());
//
//		Cosmetics updated = captor.getValue();
//
//		assertTrue(updated.getBalance()>900);
//		//Get money from baseline for a win!
//	}
//
//}
//
