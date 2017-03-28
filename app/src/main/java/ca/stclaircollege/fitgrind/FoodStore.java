package ca.stclaircollege.fitgrind;

import java.util.ArrayList;

/**
 * FoodStore is to hold an array of food results that we have. It's nie to have in a different array list and check
 * @author Johnny Nguyen
 * @version 1.0
 */

public class FoodStore {
    // our arraylist
    private ArrayList<Food> foodList;
    private int totalSearchResults;
    // This is used if results are higher than expected
    private int start;
    private int end;
    private int offset;

    /**
     * the Integer search results is how much the array should hold in a foodList. This is good because we don't have to use
     * an arraylist, since we know what size of the array is. We're not wasting space.
     * @param totalSearchResults
     */
    public FoodStore(int totalSearchResults) {
        this.totalSearchResults = totalSearchResults;
        // now we can make our food list items here
        this.foodList = new ArrayList<Food>();
    }

    /**
     * This constructor is used if the search results is higher than expected. If it is, we have to do some type of pagination.
     * @param totalSearchResults
     * @param offset
     * @param start
     * @param end
     */
    public FoodStore(int totalSearchResults, int offset, int start, int end) {
        this.totalSearchResults = totalSearchResults;
        this.offset = offset;
        this.start = start;
        this.end = end;
        // set the total search results here
        this.foodList = new ArrayList<Food>();
    }

    /**
     * This method adds food into the array.
     * @param food
     */
    public void addFood(Food food) {
        this.foodList.add(food);
    }
}
