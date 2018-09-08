package company.com.takeiteasy.Serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CafeOpeningHours implements Serializable {
    @SerializedName("opening_time")
    public String openingTime;

    @SerializedName("closing_time")
    public String closingTime;
}
