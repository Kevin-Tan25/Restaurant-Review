package persistence;

import model.RatedRestaurants;
import model.RatedRestaurantsName;
import model.Restaurant;
import model.Review;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Taken from JsonSerializationDemo file
// Represents a reader that reads user from JSON data stored in file
public class JsonReaderAllRestaurantsName {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderAllRestaurantsName(String source) {
        this.source = source;
    }

    // EFFECTS: reads user from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RatedRestaurantsName read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseReview(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ratedRestaurants from JSON object and returns it
    private RatedRestaurantsName parseReview(JSONObject jsonObject) {
        RatedRestaurants restaurants = new RatedRestaurants();
        RatedRestaurantsName restaurantsName = new RatedRestaurantsName();
        addRestaurantNames(restaurantsName, jsonObject);
        return restaurantsName;
    }

    // MODIFIES: restaurantsName
    // EFFECTS: parses reviews from JSON object and adds them to ratedRestaurant
    private void addRestaurantNames(RatedRestaurantsName restaurantsName, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
        for (Object json : jsonArray) {
            JSONObject restaurant = (JSONObject) json;
            addRestaurantName(restaurantsName, restaurant);
        }
    }

    // MODIFIES: restaurantsName
    // EFFECTS: parses restaurant from JSON object and adds it to Rated Restaurants
    private void addRestaurantName(RatedRestaurantsName restaurantsName, JSONObject jsonObject) {
        String restaurantName = jsonObject.getString("restaurantName");
        restaurantsName.addRestaurant(restaurantName);
    }
}