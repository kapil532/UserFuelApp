
package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpValidateRes {

    @SerializedName("guest")
    @Expose
    private Guest guest;

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

}
