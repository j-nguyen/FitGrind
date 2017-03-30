package ca.stclaircollege.fitgrind.api;

import java.util.ArrayList;

/**
 * FoodStore is to hold an array of food results that we have. It's nie to have in a different array list and check
 * @author Johnny Nguyen
 * @version 1.0
 */

public class FoodStore {
    // our arraylist
    private ArrayList<Item> itemList;
    private int totalSearchResults;
    // This is used if results are higher than expected
    private int start;
    private int end;

    /**
     * the Integer search results is how much the array should hold in a itemList. This is good because we don't have to use
     * an arraylist, since we know what size of the array is. We're not wasting space.
     * @param totalSearchResults
     */
    public FoodStore(int totalSearchResults) {
        this.totalSearchResults = totalSearchResults;
        // now we can make our food list items here
        this.itemList = new ArrayList<Item>();
    }

    /**
     * This constructor is used if the search results is higher than expected. If it is, we have to do some type of pagination.
     * @param totalSearchResults
     * @param start
     * @param end
     */
    public FoodStore(int totalSearchResults, int start, int end) {
        this.totalSearchResults = totalSearchResults;
        this.start = start;
        this.end = end;
        // set the total search results here
        this.itemList = new ArrayList<Item>();
    }

    /**
     * This method adds item into the array.
     * @param item
     */
    public void addFood(Item item) {
        this.itemList.add(item);
    }

    public ArrayList<Item> getFoods() {
        return this.itemList;
    }
}
