package ca.stclaircollege.fitgrind.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johnnynguyen on 2017-03-30.
 */
public class Food {
    public static final String NAME_KEY = "name";
    public static final String WEIGHT_KEY = "weight";
    public static final String MEASURE_KEY = "measure";

    private int ndbno;
    private String name;
    private String servingSize;
    // always set it by default to max nutrients. This is great
    private ArrayList<Nutrient> nutrients;

    public Food(int ndbno, String name, String servingSize) {
        this.ndbno = ndbno;
        this.name = name;
        this.servingSize = servingSize;
        // instantiate new array list
        nutrients = new ArrayList<Nutrient>();
    }

    public void addNutrient(Nutrient nutrient) {
        nutrients.add(nutrient);
    }

    public String getName() {
        return name;
    }

    public String getServingSize() {
        return servingSize;
    }

    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }
}
