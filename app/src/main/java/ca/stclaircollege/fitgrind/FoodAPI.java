package ca.stclaircollege.fitgrind;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.util.HashSet;
import java.util.Set;

/**
 * FoodAPI class is where we retrieve all our food, nutritional values and much more.
 * This is primarily where we get our requests
 * @author Johnny Nguyen
 * @version 1.0
 */
public class FoodAPI {
    // We want to create constant URLS so we don't mess up.
    // URL Search is the URL needed for searching for a certain food item.
    // URL INFO is to get nutritional info from the search parameters.
    private static final String URL_SEARCH = "https://api.nal.usda.gov/ndb/search/?format=json";
    private static final String URL_INFO = "https://api.nal.usda.gov/ndb/nutrients/?format=json";

    // Constant of how many nutrients there are
    private static final int[] NUTRIENT_LIST = new int[]{208,269,204,205,606,605,601,307,291,203,320,401,301,303,306};
    public static final int MAX_NUTRIENTS = NUTRIENT_LIST.length;

    // the max results. The API returns a limit of 150 items, but the search results could be more.
    public static final int MAX_RESULTS = 150;

    // We will need the API key. We can use context to pass, but having it passed like this might be much better
    private String apiKey;

    public FoodAPI(String apiKey) { this.apiKey = apiKey; }

    /**
     * This method searches for the food based on what's given from a textfield, most likely.
     * @param food
     * @return Food object, which should provide important items needed
     */
    public void searchFood(String food, AsyncHttpResponseHandler handler) {
        // we want to retrieve the results, and we want to encode the URL params too
        RequestParams params = new RequestParams();
        // we want to add using basic name value pairs, which is what this RequestParams class uses.
        params.put("q", food);
        params.put("api_key", this.apiKey);
        // create the client aobject
        AsyncHttpClient client = new AsyncHttpClient();
        // reference this one
        client.get(URL_SEARCH, params, handler);
    }

    public void getFoodResult(int ndbNo, AsyncHttpResponseHandler handler) {
        // finish the rest of the URL parameters
        RequestParams params = new RequestParams();
        Set<Integer> set = new HashSet<>();
        // We now want to add our URL encode
        for (int nutrient : NUTRIENT_LIST) set.add(nutrient);
        // add all the required parameters
        params.put("nutrients", set);
        // add in the ndb Search for
        params.put("ndbno", ndbNo);
        // put your api key
        params.put("api_key", this.apiKey);
        // Create an async request client
        AsyncHttpClient client = new AsyncHttpClient();
        // and now use the handler received
        client.get(URL_INFO, params, handler);
    }
}
