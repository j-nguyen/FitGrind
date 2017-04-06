package ca.stclaircollege.fitgrind.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by johnnynguyen on 2017-03-30.
 */
public class Food {
    public static final String NAME_KEY = "name";
    public static final String WEIGHT_KEY = "weight";
    public static final String MEASURE_KEY = "measure";

    private long id;
    private String logDate;
    private String name;
    private String servingSize;
    // always set it by default to max nutrients. This is great
    private ArrayList<Nutrient> nutrients;

    public Food(String name, String servingSize) {
        this.name = (name.indexOf(", UPC") != -1) ? name.substring(0, name.indexOf(", UPC")) : name;
        this.servingSize = servingSize;
        // instantiate new array list
        nutrients = new ArrayList<Nutrient>();
    }

    public Food(long id, String name, String servingSize, ArrayList<Nutrient> nutrients) {
        this.id = id;
        this.name = name;
        this.servingSize = servingSize;
        this.nutrients = nutrients;
    }

    public Food(long id, String name, String servingSize, String logDate) {
        this.id = id;
        this.name = name;
        this.servingSize = servingSize;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = sdf.parse(logDate);
            this.logDate = new SimpleDateFormat("yyyy-MM-dd K:mm a").format(d);
        } catch (ParseException e) {
            e.printStackTrace();
            this.logDate = logDate;
        }
        this.nutrients = new ArrayList<Nutrient>();
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

    public long getId() {
        return id;
    }

    public String getLogDate() {
        return logDate;
    }

    public ArrayList<Nutrient> getNutrients() {
        return nutrients;
    }
}
