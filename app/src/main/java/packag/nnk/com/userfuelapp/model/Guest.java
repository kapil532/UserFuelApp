
package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Guest {

    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("guestId")
    @Expose
    private String guestId;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("active")
    @Expose
    private Integer active;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

}
