package ca.stclaircollege.fitgrind.api;


/**
 * This class retreives information from the Item class. By getting the NDBNo, we can retreive
 * nutrition value from the food.
 * @author Johnny Nguyen
 * @version 1.0
 */

public class Nutrient {
    private String nutrientId;
    private String nutrient;
    private String unit;
    private String value;
    private String gm;

    public String getNutrientId() { return nutrientId; }

    public String getNutrient() {
        if (nutrient.equals("--")) return "0";
        return nutrient;
    }

    public String getUnit() {
        return unit;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Nutrient{" +
                "nutrientId='" + nutrientId + '\'' +
                ", nutrient='" + nutrient + '\'' +
                ", unit='" + unit + '\'' +
                ", value='" + value + '\'' +
                ", gm='" + gm + '\'' +
                '}';
    }
}
