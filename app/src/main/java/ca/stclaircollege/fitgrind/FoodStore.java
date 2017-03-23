package ca.stclaircollege.fitgrind;

/**
 * FoodStore is to hold an array of food results that we have. It's nie to have in a different array list and check
 * @author Johnny Nguyen
 * @version 1.0
 */

public class FoodStore {
    // our arraylist
    private Food[] foodList;
    private int totalSearchResults;

    /**
     * the Integer search results is how much the array should hold in a foodList. This is good because we don't have to use
     * an arraylist, since we know what size of the array is. We're not wasting space.
     * @param totalSearchResults
     */
    public FoodStore(int totalSearchResults) {
        this.totalSearchResults = totalSearchResults;
        // now we can make our food list items here
        this.foodList = new Food[this.totalSearchResults];
    }

    public Food[] getFoodList() {
        return this.foodList;
    }



}
