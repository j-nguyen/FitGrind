package ca.stclaircollege.fitgrind.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * FoodAPI class is where we retrieve all our food, nutritional values and much more.
 * This is primarily where we get our requests
 * NOTE: We are now using the RetroFit Library, provided by Square. This to ensure that our APP works on Android 2.3+
 * @author Johnny Nguyen
 * @version 2.0
 */
public class FoodAPI {
    // We want to create constant URLS so we don't mess up.
    // URL Search is the URL needed for searching for a certain food item.
    // URL INFO is to get nutritional info from the search parameters.
    // We can create constants inside here to use later on
    private static final String BASE_URL = "https://api.nal.usda.gov/ndb/";
    private static final String URL_SEARCH = "https://api.nal.usda.gov/ndb/";
    private static final String URL_INFO = "https://api.nal.usda.gov/ndb/nutrients/?format=json";

    // Constant of how many nutrients there are
    private static final int[] NUTRIENT_LIST = new int[]{208,269,204,205,606,605,601,307,291,203,320,401,301,303,306};
    public static final int MAX_NUTRIENTS = NUTRIENT_LIST.length;

    // the max results. The API returns a limit of 150 items, but the search results could be more.
    public static final int MAX_RESULTS = 150;

    // We will need the API key. We can use context to pass, but having it passed like this might be much better
    private String apiKey;

    // our private retrofit variables to use for searching
    private FoodService foodService;

    // Create an inner food service instead for us to use upon
    public interface FoodService {

        // create one for food search
        @GET("?format=json")
        Call<ApiResponse> searchFood(@Query("q") String food, @Query("api_key") String apiKey);

    }

    public FoodAPI(String apiKey) {
        this.apiKey = apiKey;
        // create the Retrofit class. One is for search, the other is for getting the nutrient info
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // and now set it to the interface
        foodService = retrofit.create(FoodService.class);
    }
    

    /**
     * Item search
     * @param food
     * @param handler
     */
    public void foodSearch(String food, Callback<ApiResponse> handler) {
        foodService.searchFood("json", this.apiKey).enqueue(handler);
    }

    /**
     * This method searches for the food based on what's given from a textfield, most likely.
     * @param food
     * @return Item object, which should provide important items needed
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
