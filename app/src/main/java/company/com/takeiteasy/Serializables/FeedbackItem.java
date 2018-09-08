package company.com.takeiteasy.Serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeedbackItem implements Serializable {
    @SerializedName("text")
    public String text;
}
