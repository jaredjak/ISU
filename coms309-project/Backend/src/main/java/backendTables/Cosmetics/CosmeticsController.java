package backendTables.Cosmetics;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import backendTables.Achievements.AchievementRepository;
import backendTables.Achievements.Achievements;
import backendTables.Club.Club;
import backendTables.Users.User;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/**
 *
 * @author
 *
 */

@RestController
@Tag(name="Cosmetic Activity", description="Store User's Skin info and wormbuck Counts")
public class CosmeticsController {

    @Autowired
    CosmeticsRepository cosmeticsRepository;
    @Autowired
    MarketplaceRepository marketplaceRepository;
    @Autowired
    AchievementRepository achievementRepository;

    private final String success = "{\"message\":\"success\"}";
    private final String failure = "{\"message\":\"failure\"}";

    // Create a new user
    @Operation(
            summary = "Create Cosmetics Account",
            description = "Create a cosmetics account for user with given username. Typically done by backend upon user creation",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully created",
                            content = @Content(schema = @Schema(implementation = Cosmetics.class))
                    ),
            }
    )
    @PostMapping("/cosmetics")
    public Cosmetics createUser(@Parameter(description = "The user account to associated with this cosmetics account", required = true) @RequestBody User user) {
        Cosmetics c = new Cosmetics(user);
        return cosmeticsRepository.save(c);
    }

    @Operation(
            summary = "Get Skin Information",
            description = "Get counts for skins owned by the user with this username",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cosmetics account found",
                            content = @Content(schema = @Schema(implementation = Wormbox.class))
                    ),
            }
    )
    @GetMapping(path = "/cosmetics/getAll/user/{username}")
    public Wormbox getUsers(@Parameter(description = "The username of the user associated with this cosmetics account", required = true)@PathVariable String username) {
        Cosmetics c = cosmeticsRepository.findByUsername(username);
        if (c == null) return null;
        return c.getWormbox();
    }

    @Operation(
            summary = "Get Wormbucks",
            description = "Returns the current balance of wormbucks for this username",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cosmetics account found",
                            content = @Content(schema = @Schema(type="double", example = "234.5"))
                    ),
            }
    )
    @GetMapping(path = "/cosmetics/getWormbucks/{username}")
    double getWormbucks(@Parameter(description = "Username of account to check balance", required = true) @PathVariable String username) {
        return cosmeticsRepository.findByUsername(username).getBalance();
    }

    @Operation(
            summary = "Get Previos Bet",
            description = "Returns the result of the previous bet based on the most recent wormbuck value added to the account",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully found account",
                            content = @Content(schema = @Schema(type="double", example = "-345.4"))
                    ),
            }
    )
    @GetMapping(path = "/cosmetics/getPrevBet/{username}")
    double getBet(@Parameter(description = "Username of the account to check prev bet of",required = true) @PathVariable String username) {
        return cosmeticsRepository.findByUsername(username).getPrevBet();
    }


    @Operation(
            summary = "Increment Wormbucks",
            description = "Add/remove wormbucks of given amount to this cosmetics account"
    )
    @PutMapping(path = "/cosmetics/setWormbucks/{username}")
    void setWormbucks(@Parameter(description = "Username of the account to add wormbucks to.", required = true)@PathVariable String username,
                      @Parameter(description = "The integer amount to add (or remove if negative) from the account", required = true)@RequestBody int amount) {
        cosmeticsRepository.findByUsername(username).adjustBalance(amount);
    }

    @Operation(
            summary = "Delete Cosmetics account",
            description = "Delete the given cosmetics account of given username",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted",
                            content = @Content(schema = @Schema(implementation = String.class))
                    ),
            }
    )
    @DeleteMapping("/cosmetics/user/{username}")
    public String deleteUser(@Parameter(description = "Username of the cosmetics account to be deleted",required = true) @PathVariable String username) {
        Cosmetics c = cosmeticsRepository.findByUsername(username);
        if(c!= null){
            cosmeticsRepository.deleteById(c.getId());
            return "{\"message\":\"success\"}";
        }
        return "{\"message\":\"failure\"}";
    }

    //Cosmetics Controller for marketplace buying and selling
    @Operation(
            summary = "Create Marketplace object",
            description = "Create a marketplace object for cosmetics accounts to buy from. Contains prices, and counts of skins available, which alters prices accordingly",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully created",
                            content = @Content(schema = @Schema(implementation = Marketplace.class))
                    ),
            }
    )
    @PostMapping (path = "/cosmetics/Market")
    public Marketplace createMarket() {
        Marketplace m = new Marketplace();
        marketplaceRepository.save(m);
        return m;
    }

    @Operation(
            summary = "Get Marketplace",
            description = "Gives the marketplace object and it's various skins, counts of skins available and prices of skins",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved",
                            content = @Content(schema = @Schema(implementation = Marketplace.class))
                    ),
            }
    )
    @GetMapping(path = "/cosmetics/getMarket")
    Marketplace getMarket() {
        return marketplaceRepository.findById(1);
    }

    @Operation(
            summary = "Buy skin from Marketplace",
            description = "The cosmetics account with given username will buy a skin of given color from marketplace. " +
                    "Prices then fluctuate based on available stock. Fails if user lacks the funds or not enough stock in Market"
    )
    @PutMapping(path = "/cosmetics/buyMarket/{color}/{username}")
    void buySkin(@Parameter(description = "The skin name to be purchased from marketplace.", required = true)@PathVariable String color,
                 @Parameter(description = "The username of the cosmetics account to buy add the skin to", required = true) @PathVariable String username) {
        Marketplace m = marketplaceRepository.findById(1);
        Cosmetics c = cosmeticsRepository.findByUsername(username);
        if (c.getBalance() > m.getPrice(color)) {

            Achievements a = achievementRepository.findByUsername(username);

            // If the skin is any of the premiums, give an achievement for having it
            if (color.equals("Walter")) {
                a.increaseCount(10, 1);
            }
            else if (color.equals("House")) {
                a.increaseCount(11, 1);
            }
            else if (color.equals("Bullworm")) {
                a.increaseCount(12, 1);
            }

            double cost = m.buySkin(color);
            c.adjustBalance((-cost));
            c.addWorm(color);

            // Check if they now have enough worms to get next tier.
            a.increaseCount(2, 1);

            marketplaceRepository.save(m);
            cosmeticsRepository.save(c);
            achievementRepository.save(a);
        }
    }

    @Operation(
            summary = "Sell skin to Marketplace",
            description = "The cosmetics account with given username will sell a skin of given color to the marketplace. " +
                    "Prices then fluctuate based on available stock. Fails if user doesn't have the skin"
    )
    @PutMapping(path = "/cosmetics/sellMarket/{color}/{username}")
    void sellSkin(@Parameter(description = "The skin name to decrease the count of in the cosmetics account.",required = true) @PathVariable String color,
                  @Parameter(description = "The username of the cosmetics account to remove the skin from.", required = true) @PathVariable String username) {
        Marketplace m = marketplaceRepository.findById(1);
        Cosmetics c = cosmeticsRepository.findByUsername(username);
        String s = c.viewSkin(color);
        if (s != null) {
            c.removeWorm(s);
            c.adjustBalance(m.sellSkin(color));
            marketplaceRepository.save(m);
            cosmeticsRepository.save(c);
        }
    }

    @Operation(
            summary = "Set Selected Skin",
            description = "Changes the user's selected skin to the given skin. Fails if user doesn't have that skin.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved",
                            content = @Content(schema = @Schema(type="boolean", example = "true"))
                    ),
            }
    )
    @PutMapping(path="/cosmetics/setSelected/{color}/{username}")
    boolean setSelected(@Parameter(description = "The skin name to put as the selected skin.", required = true) @PathVariable String color,
                        @Parameter(description = "The username of the cosmetics account to adjust value of", required = true)@PathVariable String username) {
        Cosmetics c = cosmeticsRepository.findByUsername(username);
        boolean b = c.select(color);
        cosmeticsRepository.save(c);
        return b;
    }

    @Operation(
            summary = "Get Selected Skin",
            description = "Returns the skin the user has currently selected",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved",
                            content = @Content(schema = @Schema(type="String", example = "Blue"))
                    ),
            }
    )
    @GetMapping(path="/cosmetics/getSelected/{username}")
    String getSelected(@Parameter(description = "Username of the account to get the information from.",required = true) @PathVariable String username) {
        Cosmetics c = cosmeticsRepository.findByUsername(username);
        return c.getSelected();
    }

}

