//package myapp;
//
//// Import Java libraries
//import java.util.List;
//import java.util.ArrayList;
//
//// import junit/spring tests
//import backendTables.Achievements.AchievementRepository;
//import backendTables.Achievements.Achievements;
//import backendTables.Cosmetics.*;
//import backendTables.Users.User;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.jboss.jandex.Main;
//import org.junit.Test;
//import org.junit.runner.RunWith;
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
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//import org.springframework.http.MediaType;
//import org.yaml.snakeyaml.error.Mark;
//
//// import mockito related
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.mock;
//import static org.hamcrest.Matchers.is;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(CosmeticsController.class)
//public class KadenSystemTest {
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
//		public CosmeticsController cosmeticsController() {
//			return new CosmeticsController();  // The controller bean
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
//
//
//		// mock the save() method to save argument to the list
//		when(repo.save(any(Cosmetics.class)))
//				.thenReturn(c);
//
//		// mock the findById method
//		when(repo.findById(testUser.getId())).thenReturn(c);
//
//		String jsonContent = new ObjectMapper().writeValueAsString(testUser);
//
//		// Perform the POST request
//		controller.perform(post("/cosmetics")
//						.contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//						.content(jsonContent))  // Add the serialized object in the request body
//				.andExpect(status().isOk())
//				// Check the id
//				.andExpect(jsonPath("$.id", is(0)))
//				.andExpect(jsonPath("$.selected", is("Beige"))) // Check the selected skin
//				.andExpect(jsonPath("$.balance", is(1000.0))) // Check balance
//				.andExpect(jsonPath("$.prevBet", is(0.0))) // Check previous bet
//				.andExpect(jsonPath("$.wormbox.counts", contains(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0))) // Check counts in wormbox
//				.andExpect(jsonPath("$.counts", contains(0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0))); // Check counts// Assert HTTP status 200
//
//	}
//
//	@Test
//	public void buySellSkins() throws Exception {
//		User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//		Cosmetics c = new Cosmetics(testUser);
//		Marketplace m = new Marketplace();
//		Achievements a = new Achievements("testman");
//		when(repo.save(any(Cosmetics.class)))
//				.thenReturn(c);
//
//		when(mrepo.save(any(Marketplace.class))).thenReturn(m);
//
//		when(repo.findByUsername(testUser.getUsername())).thenReturn(c);
//
//		when(mrepo.findById(m.getId())).thenReturn(m);
//
//		when(arepo.findByUsername(testUser.getUsername())).thenReturn(a);
//
//		for (int i = 0; i < 5; i++) {
//			controller.perform(put("/cosmetics/buyMarket/Red/testman")).andExpect(status().isOk());
//		}
//
//		controller.perform(get("/cosmetics/getAll/user/testman"))
//				.andExpect(jsonPath("$.counts", contains(5, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0)));
//
//		// Buy 5 of red, 1 of green
//		controller.perform(put("/cosmetics/buyMarket/Green/testman")).andExpect(status().isOk());
//		controller.perform(get("/cosmetics/getAll/user/testman"))
//				.andExpect(jsonPath("$.counts", contains(5, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0)));
//
//		// This shouldn't go through since the player doesn't have enough money.
//		controller.perform(put("/cosmetics/buyMarket/Walter/testman")).andExpect(status().isOk());
//		controller.perform(get("/cosmetics/getAll/user/testman"))
//				.andExpect(jsonPath("$.counts", contains(5, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0)));
//		c.adjustBalance(1000000);
//		controller.perform(put("/cosmetics/buyMarket/House/testman")).andExpect(status().isOk());
//		controller.perform(get("/cosmetics/getAll/user/testman"))
//				.andExpect(jsonPath("$.counts", contains(5, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1)));
//
//
//		// Sell 2 Red
//		for (int i = 0; i < 2; i++) {
//			controller.perform(put("/cosmetics/sellMarket/Red/testman")).andExpect(status().isOk());
//		}
//		controller.perform(get("/cosmetics/getAll/user/testman"))
//				.andExpect(jsonPath("$.counts", contains(3, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1)));
//
//
//		controller.perform(put("/cosmetics/sellMarket/House/testman")).andExpect(status().isOk());
//		controller.perform(get("/cosmetics/getAll/user/testman"))
//				.andExpect(jsonPath("$.counts", contains(3, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0)));
//
//
//		// Shouldn't go through since the user has none of this.
//		controller.perform(put("/cosmetics/sellMarket/Blue/testman")).andExpect(status().isOk());
//		controller.perform(get("/cosmetics/getAll/user/testman"))
//				.andExpect(jsonPath("$.counts", contains(3, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0)));
//
//	}
//	@Test
//	public void testCashChanges() throws Exception {
//		User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//		Cosmetics c = new Cosmetics(testUser);
//
//		when(repo.save(any(Cosmetics.class)))
//				.thenReturn(c);
//
//		when(repo.findByUsername(testUser.getUsername())).thenReturn(c);
//
//		controller.perform(put("/cosmetics/setWormbucks/testman")
//				.contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//				.content("100"))
//				.andExpect(status().isOk());
//
//		controller.perform(get("/cosmetics/getWormbucks/testman"))
//				.andExpect(content().string("1100.0"));
//
//		controller.perform(put("/cosmetics/setWormbucks/testman")
//						.contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//						.content("-200"))
//				.andExpect(status().isOk());
//
//		controller.perform(get("/cosmetics/getWormbucks/testman"))
//				.andExpect(content().string("900.0"));
//
//
//		// This is greater than the available balance, so default to 0.
//		controller.perform(put("/cosmetics/setWormbucks/testman")
//						.contentType(MediaType.APPLICATION_JSON)  // Specify that it's a JSON request
//						.content("-1000"))
//				.andExpect(status().isOk());
//
//		controller.perform(get("/cosmetics/getWormbucks/testman"))
//				.andExpect(content().string("0.0"));
//
//		controller.perform(get("/cosmetics/getPrevBet/testman"))
//				.andExpect(status().isOk())
//				.andExpect(content().string("0.0"));
//	}
//
//	@Test
//	public void deleteTest() throws Exception {
//
//		User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//		Cosmetics c = new Cosmetics(testUser);
//
//
//		// mock the save() method to save argument to the list
//		when(repo.save(any(Cosmetics.class)))
//				.thenReturn(c);
//
//		// mock the findById method
//		when(repo.findById(testUser.getId())).thenReturn(c);
//
//		//Confirm the user exists
//		controller.perform(get("/cosmetics/getAll/user/testman"))
//						.andExpect(status().isOk());
//
//		//Delete the user
//		controller.perform(delete("/cosmetics/user/testman"))
//				.andExpect(status().isOk());
//
//		//Confirm the user no longer gives a value
//		controller.perform(get("/cosmetics/getAll/user/testman"))
//				.andExpect(status().isOk())
//				.andExpect(content().string(""));
//
//		//Delete the user
//		controller.perform(delete("/cosmetics/user/fakeUser"))
//				.andExpect(status().isOk())
//				.andExpect(content().string("{\"message\":\"failure\"}"));
//	}
//
//	@Test
//	public void selectionTest() throws Exception {
//		User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//		Cosmetics c = new Cosmetics(testUser);
//		Marketplace m = new Marketplace();
//		Achievements a = new Achievements("testman");
//
//
//		// mock the save() method to save argument to the list
//		when(repo.save(any(Cosmetics.class)))
//				.thenReturn(c);
//
//		// mock the findById method
//		when(repo.findById(testUser.getId())).thenReturn(c);
//
//		when(repo.findByUsername("testman")).thenReturn(c);
//
//		when(mrepo.findById(m.getId())).thenReturn(m);
//
//		when(arepo.findByUsername("testman")).thenReturn(a);
//
//		//default worm
//		controller.perform(get("/cosmetics/getSelected/testman"))
//				.andExpect(status().isOk())
//				.andExpect(content().string("Beige"));
//
//		// This shouldnt change selected since user has no red worms
//		controller.perform(put("/cosmetics/setSelected/Red/testman"))
//				.andExpect(status().isOk());
//
//		controller.perform(get("/cosmetics/getSelected/testman"))
//				.andExpect(status().isOk())
//				.andExpect(content().string("Beige"));
//
//		//Add an orange worm so we can test a selection switch
//		controller.perform(put("/cosmetics/buyMarket/Orange/testman")).andExpect(status().isOk());
//
//		controller.perform(put("/cosmetics/setSelected/Orange/testman"))
//				.andExpect(status().isOk());
//
//		controller.perform(get("/cosmetics/getSelected/testman"))
//				.andExpect(status().isOk())
//				.andExpect(content().string("Orange"));
//
//	}
//
//	@Test
//	public void marketTest() throws Exception {
//		User testUser = new User("testman", "jared10@iastate.edu", 238383881, "password");
//		Cosmetics c = new Cosmetics(testUser);
//		Marketplace m = new Marketplace();
//		Achievements a = new Achievements("testman");
//
//
//		// mock the save() method to save argument to the list
//		when(mrepo.save(any(Marketplace.class)))
//				.thenReturn(m);
//
//		// mock the findById method
//		when(repo.findById(testUser.getId())).thenReturn(c);
//
//		when(repo.findByUsername("testman")).thenReturn(c);
//
//		when(mrepo.findById(m.getId())).thenReturn(m);
//
//		when(arepo.findByUsername("testman")).thenReturn(a);
//
//		//default worm
//		controller.perform(post("/cosmetics/Market"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.skins", contains("Red", "Orange", "Yellow", "Green",
//						"Blue", "Purple", "Gold", "Beige", "Black", "Walter", "bullWorm", "House")))
//				.andExpect(jsonPath("$.available", contains(100, 100,100, 100, 100, 100, 100, 100, 100, 100, 100, 100)));
//
//		controller.perform(get("/cosmetics/getMarket"))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$.skins", contains("Red", "Orange", "Yellow", "Green",
//						"Blue", "Purple", "Gold", "Beige", "Black", "Walter", "bullWorm", "House")))
//				.andExpect(jsonPath("$.available", contains(100, 100,100, 100, 100, 100, 100, 100, 100, 100, 100, 100)));
//
//	}
//}
//
