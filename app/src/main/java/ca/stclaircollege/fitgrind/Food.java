package ca.stclaircollege.fitgrind;

/**
 * Food class is used to retrieve items.
 */

public class Food {
    private String group;
    private String name;
    private int ndbNo;
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

    public void setGroup(String group) {
        this.group = group;
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

    public void setNdbno(int ndbno) {
        this.ndbNo = ndbno;
    }
}
