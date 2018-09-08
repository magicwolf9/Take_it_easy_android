package company.com.takeiteasy.Serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CafeLocation implements Serializable {
    @SerializedName("lat")
    public float lat;

    @SerializedName("lon")
    public float lon;

}
