package ca.stclaircollege.fitgrind;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

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
    private static final String URL_SEARCH = "https://api.nal.usda.gov/ndb/search/?format=json";
    private static final String URL_INFO = "";
    public static int MAX_RESULTS = 150; // the max results. The API returns a limit of 150 items, but the search results could be more.

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
    public FoodStore searchFood(String food) {
        // we want to retrieve the results, and we want to encode the URL params too
        String urlSearch = URL_SEARCH + "&q=" + food + "&api_key=" + this.apiKey;
        // from urlSearch get the encoded
        String encodedSeach = encodeUrl(urlSearch);
        // check if it doesn't return null, if it doesn't we can do an async search
        if (encodedSeach != null) {
            // We want to create an async http request object
            AsyncHttpClient client = new AsyncHttpClient();
            // we want a GET request, so we use the get method from async
            client.get(urlSearch, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // If the response is JSONObject instead of expected JSONArra
                    try {
                        // create the store
                        FoodStore store = null;
                        // get list
                        JSONObject list = response.getJSONObject("list");
                        JSONArray jsonArray = list.getJSONArray("item");
                        // depending on the constructor, we can change based on it
                        if (list.getInt("total") > MAX_RESULTS) {
                            store = new FoodStore(list.getInt("total"), list.getInt("offset"), list.getInt("start"), list.getInt("end"));
                        } else {
                            store = new FoodStore(list.getInt("total"));
                        }
                        // Once done, we want to iterate through the JSON array to get the rest of the contents in the item class
                        for (int i=0; i < jsonArray.length(); i++) {
                            JSONObject item = jsonArray.getJSONObject(i);
                            // once retrieved, we know the item KEY already so we can just add it in without needing to know much
                            
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        // finally return the object
        return null;
    }

    /**
     * This private method will help us encode URL links when POSTing or GETing requests
     * @param url
     * @return a String that's been properly encoded
     */
    private String encodeUrl(String url) {
        String results = null;
        // get the url, and encode the results
        try {
            // encode it in utf-8
            results = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return results;
    }

}
