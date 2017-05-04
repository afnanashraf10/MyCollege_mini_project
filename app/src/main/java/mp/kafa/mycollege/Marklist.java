package mp.kafa.mycollege;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amrutha on 5/4/2017.
 */

public class Marklist {
    @SerializedName("id")
    int id;
    @SerializedName("teachid")
    int teachid;
    @SerializedName("subject")
    String subject;
    @SerializedName("studid")
    int studid;
    @SerializedName("S1")
    int S1;
    @SerializedName("S2")
    int S2;
    @SerializedName("A1")
    int A1;
    @SerializedName("A2")
    int A2;
}
