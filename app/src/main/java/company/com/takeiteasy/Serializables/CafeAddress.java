package company.com.takeiteasy.Serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CafeAddress implements Serializable {
    @SerializedName("country")
    public String country;

    @SerializedName("city")
    public String city;

    @SerializedName("street")
    public String street;

    @SerializedName("house")
    public String house;
}
