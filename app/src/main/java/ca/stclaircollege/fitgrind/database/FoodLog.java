package ca.stclaircollege.fitgrind.database;

import java.util.ArrayList;

import ca.stclaircollege.fitgrind.api.Food;

/**
 * Created by johnnynguyen on 2017-04-05.
 */

public class FoodLog {
    private String date;
    private ArrayList<Food> foodList;

    public FoodLog(String date, ArrayList<Food> foodList) {
        this.date = date;
        this.foodList = foodList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    @Override
    public String toString() {
        return "FoodLog{" +
                "date='" + date + '\'' +
                '}';
    }
}
