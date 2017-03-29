package ca.stclaircollege.fitgrind;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Food class is used to retrieve items.
 */

public class Food implements Parcelable {
    private String group;
    private String name;
    private int ndbNo;
    private String servingSize = ""; // default ""
    // provide a list of nutrients. This is always going to be a set value.
    private Nutrient[] nutrients;

    public Food(String group, String name, int ndbno) {
        this.group = group;
        // only showcase the Name of the food, not the UPC #. That isn't important to the user.
        if (name.indexOf(", UPC") != -1) {
            this.name = name.substring(0, name.indexOf(", UPC"));
        } else {
            this.name = name;
        }
        this.ndbNo = ndbno;
        // set the new nutrients, we know the max, so we can use a normal array
        this.nutrients = new Nutrient[FoodAPI.MAX_NUTRIENTS];
    }

    protected Food(Parcel in) {
        this.group = in.readString();
        this.name = in.readString();
        this.ndbNo = in.readInt();
    }

    public Nutrient[] getNutrients() {
        return nutrients;
    }

    public void addNutrient(Nutrient nutrient, int index) {
        this.nutrients[index] = nutrient;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNdbno() {
        return ndbNo;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(group);
        dest.writeString(name);
        dest.writeInt(ndbNo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Food> CREATOR = new Parcelable.Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };


    // testing

    @Override
    public String toString() {
        return "Food{" +
                "group='" + group + '\'' +
                ", name='" + name + '\'' +
                ", ndbNo=" + ndbNo +
                ", nutrients=" + Arrays.toString(nutrients) +
                '}';
    }
}
