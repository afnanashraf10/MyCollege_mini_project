package mp.kafa.mycollege;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sangeeth Nandakumar on 19-04-2017.
 */

public class Subject {
    @SerializedName("id")
    int id;
    @SerializedName("branch")
    String branch;
    @SerializedName("name")
    String name;
    @SerializedName("semester")
    String semester;
}
