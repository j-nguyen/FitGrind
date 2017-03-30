package ca.stclaircollege.fitgrind.api;


/**
 * This class retreives information from the Item class. By getting the NDBNo, we can retreive
 * nutrition value from the food.
 * @author Johnny Nguyen
 * @version 1.0
 */

public class Nutrient {
    private int nutrientId;
    private String name;
    private String unit;
    private double value;

    public Nutrient(int nutrientId, String name, String unit, double value) {
        this.nutrientId = nutrientId;
        this.name = name;
        this.unit = unit;
        this.value = value;
    }

    public int getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getValueUnit() {
        return this.value + this.unit;
    }
}
