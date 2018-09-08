package company.com.takeiteasy.Serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Cafe implements Serializable {
    @SerializedName("cafe_id")
    public String cafeId;

    @SerializedName("cafe_name")
    public String cafeName;

    @SerializedName("cafe_rating")
    public float cafeRating;

    @SerializedName("cafe_description")
    public String cafeDescription;

    @SerializedName("cafe_coordinates")
    public CafeLocation cafeCoordinates;

    @SerializedName("cafe_menu")
    public CafeMenuItem[] cafeMenu;

    @SerializedName("add_time")
    public Date addTime;

    @SerializedName("icon")
    public String iconUrl;

    @SerializedName("cafe_feedback")
    public FeedbackItem[] cafeFeedback;

    @SerializedName("cafe_opening_hours")
    public CafeOpeningHours cafeOpeningHours;

    @SerializedName("cafe_address")
    public CafeAddress cafeAddress;

    public Cafe(){

    }

}
