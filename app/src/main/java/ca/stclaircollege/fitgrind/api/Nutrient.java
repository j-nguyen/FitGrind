package ca.stclaircollege.fitgrind.api;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * This class retreives information from the Item class. By getting the NDBNo, we can retreive
 * nutrition value from the food.
 * @author Johnny Nguyen
 * @version 1.0
 */

public class Nutrient implements Parcelable {
    public static final String ID_KEY = "nutrient_id";
    public static final String NUTRIENT_KEY = "nutrient";
    public static final String UNIT_KEY = "unit";
    public static final String VALUE_KEY = "value";

    private int nutrientId;
    private String nutrient;
    private String unit;
    private double value;

    public Nutrient(int nutrientId, String nutrient, String unit, String value) {
        this.nutrientId = nutrientId;
        // if we land on energy, we want it to say Calories instead of energy. This is better for the user.
        this.nutrient = (nutrient.equals("Energy")) ? "Calories" : nutrient;
        this.unit = unit;
        this.value = (value.equals("--")) ? 0 : Double.parseDouble(value);
    }

    public Nutrient(String nutrient, double value) {
        this.nutrient = nutrient;
        this.value = value;
    }

    protected Nutrient(Parcel in) {
        nutrientId = in.readInt();
        nutrient = in.readString();
        unit = in.readString();
        value = in.readDouble();
    }

    public int getNutrientId() {
        return nutrientId;
    }

    public String getNutrient() {
        return nutrient;
    }

    public String getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nutrientId);
        dest.writeString(nutrient);
        dest.writeString(unit);
        dest.writeDouble(value);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Nutrient> CREATOR = new Parcelable.Creator<Nutrient>() {
        @Override
        public Nutrient createFromParcel(Parcel in) {
            return new Nutrient(in);
        }

        @Override
        public Nutrient[] newArray(int size) {
            return new Nutrient[size];
        }
    };
}
