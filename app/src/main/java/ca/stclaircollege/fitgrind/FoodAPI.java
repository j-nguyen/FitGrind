package ca.stclaircollege.fitgrind;

import android.content.Context;

/**
 * FoodAPI class is where we retrieve all our food, nutiritional values and much more.
 * This is primarily where we get our requests
 * @author Johnny Nguyen
 * @version 1.0
 */
public class FoodAPI {
    // We want to create constant URLS so we don't mess up.
    // URL Search is the URL needed for searching for a certain food item.
    // URL INFO is to get nutritional info from the search parameters.
    private static final String URL_SEARCH = "https://api.nal.usda.gov/ndb/search/";
    private static final String URL_INFO = "";

    // We will need the API key. We can use context to pass, but having it passed like this might be much better
    private String apiKey;

    public FoodAPI(String apiKey)  {
        // get API Key
        this.apiKey = apiKey;
    }

    /**
     * This method searches for the food based on what's given from a textfield, most likely.
     * @param food
     * @return Food object, which should provide important items needed
     */
    public Food searchFood(String food) {
        return null;
    }

}
