package ca.stclaircollege.fitgrind.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List {

    @SerializedName("q")
    @Expose
    private String q;
    @SerializedName("sr")
    @Expose
    private String sr;
    @SerializedName("ds")
    @Expose
    private String ds;
    @SerializedName("start")
    @Expose
    private int start;
    @SerializedName("end")
    @Expose
    private int end;
    @SerializedName("total")
    @Expose
    private int total;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("sort")
    @Expose
    private String sort;
    @SerializedName("item")
    @Expose
    private java.util.List<Item> item = null;

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    public Integer getTotal() {
        return total;
    }

    public String getGroup() {
        return group;
    }

    public String getSort() {
        return sort;
    }

    public java.util.List<Item> getItem() {
        return item;
    }
}