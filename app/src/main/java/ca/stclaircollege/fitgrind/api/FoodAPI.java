package ca.stclaircollege.fitgrind.api;

import android.content.Context;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import java.util.ArrayList;

/**
 * FoodAPI class is where we retrieve all our food, nutritional values and much more.
 * This is primarily where we get our requests
 * NOTE: We are now using the RetroFit Library, provided by Square. This to ensure that our APP works on Android 2.3+
 * @author Johnny Nguyen
 * @version 3.0
 */
public class FoodAPI {
    // We want to create constant URLS so we don't mess up.
    // URL Search is the URL needed for searching for a certain food item.
    // URL INFO is to get nutritional info from the search parameters.
    // We can create constants inside here to use later on
    private static final String BASE_URL = "https://api.nal.usda.gov/ndb/";
    private static final String NUTRIENT_URL = "&nutrients=208&nutrients=269&nutrients=204&nutrients=205&nutrients=606&nutrients=605" +
            "&nutrients=601&nutrients=307&nutrients=291&nutrients=203&nutrients=320&nutrients=401&nutrients=301&nutrients=303&nutrients=306";
    public static final int MAX_NUTRIENTS = 15;

    // We will need the API key. We can use context to pass, but having it passed like this might be much better
    private String apiKey;

    public FoodAPI(Context context, String apiKey) {
        this.apiKey = apiKey;
        // Initialize it here
        AndroidNetworking.initialize(context);
    }
    

    /**
     * Item search for food. This is the easiest way to get food items
     * @param food
     * @param requestListener
     */
    public void foodSearch(String food, JSONObjectRequestListener requestListener) {
        AndroidNetworking.get(BASE_URL + "search/?")
                .addQueryParameter("format", "json")
                .addQueryParameter("q", food)
                .addQueryParameter("api_key", this.apiKey)
                .build()
                .getAsJSONObject(requestListener);
    }

    /**
     * Food Results from specified number
     * @param ndbno
     * @param requestListener
     */
    public void foodResult(int ndbno, JSONObjectRequestListener requestListener) {
        // because there's no easy way, we'll have to add it manually like so
        AndroidNetworking.get(BASE_URL + "nutrients/?format=json" + NUTRIENT_URL)
                .addQueryParameter("ndbno", Integer.toString(ndbno))
                .addQueryParameter("api_key", this.apiKey)
                .build()
                .getAsJSONObject(requestListener);
    }
}
