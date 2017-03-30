package ca.stclaircollege.fitgrind.api;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ApiResponse {

    @SerializedName("list")
    @Expose
    private List list;

    public boolean hasItems() {
        return list.getItem().size() > 0;
    }

    public ArrayList<Item> getItems() {
        return (ArrayList<Item>) list.getItem();
    }
}