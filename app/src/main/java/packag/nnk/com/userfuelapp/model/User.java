
package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("cabNumber")
    @Expose
    private Object cabNumber;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("driverAggregator")
    @Expose
    private Object driverAggregator;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("isPinAvailable")
    @Expose
    private Boolean isPinAvailable;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object getCabNumber() {
        return cabNumber;
    }

    public void setCabNumber(Object cabNumber) {
        this.cabNumber = cabNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Object getDriverAggregator() {
        return driverAggregator;
    }

    public void setDriverAggregator(Object driverAggregator) {
        this.driverAggregator = driverAggregator;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getIsPinAvailable() {
        return isPinAvailable;
    }

    public void setIsPinAvailable(Boolean isPinAvailable) {
        this.isPinAvailable = isPinAvailable;
    }

}
