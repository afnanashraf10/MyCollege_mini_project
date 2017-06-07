package mp.kafa.mycollege;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Amrutha on 06-Jun-17.
 */

public class OneStudent {
    @SerializedName("id")
    String id;
    @SerializedName("fname")
    String firstname;
    @SerializedName("lname")
    String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getId() {
        return id;
    }
}
