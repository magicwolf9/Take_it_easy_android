package company.com.takeiteasy.Serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CafeMenuItem implements Serializable {
    @SerializedName("item_id")
    public int itemId;

    @SerializedName("name")
    public String itemName;

    @SerializedName("description")
    public String description;

    @SerializedName("time")
    public String time;

    @SerializedName("icon")
    public String iconUrl;

    @SerializedName("image")
    public String image;

    @SerializedName("price")
    public float price;

    @SerializedName("type")
    public String type;
}
