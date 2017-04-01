package ca.stclaircollege.fitgrind.api;

import java.util.List;

/**
 * Created by johnnynguyen on 2017-03-30.
 */
public class Food {
    private String ndnbo;
    private String name;
    private double weight;
    private String measure;
    private java.util.List<Nutrient> nutrients = null;

    public List<Nutrient> getNutrients() { return nutrients; }

    public String getMeasure() {
        return measure;
    }

    public double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

}
