package backendTables.Game;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONObject;
import org.json.JSONArray;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@Tag(name = "Get Weather", description = "Method to get current weather states as an intermediate API")
public class WeatherController {

    @Operation(
            summary = "Get Weather",
            description = "Gives weather information from past 15 minutes: IsDay(true/false), Cloud Cover (percent as an int), Rainfall (as a decimal), Snowfall (as a decimal), Temperature (In fahrenheit)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully deleted",
                            content = @Content(schema = @Schema(type="String", example = "isDaytime/CloudCover/rainfall/snowfall/temp/true/87/0.35/0.0/45"))
                    ),
            }
    )
    @GetMapping("/getWeather")
    public String getWeather() {
        String resp = "";
        try {
            // URL for Weather API of Ames IA's weather district. Hourly forecast
            String url = "https://api.open-meteo.com/v1/forecast?latitude=42.0347&longitude=-93.6199&current=cloud_cover,temperature_2m,is_day,rain,snowfall&forecast_days=1&temperature_unit=fahrenheit";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse the JSON response
            JSONObject current = new JSONObject(response.body()).getJSONObject("current");



            resp += "isDaytime/cloudCover/rainfall/snowfall/temp,";

            resp += current.getInt("is_day") + ",";

            int cloudCover = current.getInt("cloud_cover");
            resp += cloudCover + ",";

            resp += current.getDouble("rain") + ",";

            resp += current.getDouble("snowfall") + ",";

            resp += current.getDouble("temperature_2m");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }
}
