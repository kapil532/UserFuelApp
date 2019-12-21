
package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PetrolBunkDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("petrolBunkName")
    @Expose
    private String petrolBunkName;
    @SerializedName("petrolBunkAddress")
    @Expose
    private String petrolBunkAddress;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("compoundCode")
    @Expose
    private String compoundCode;
    @SerializedName("bunkType")
    @Expose
    private String bunkType;
    @SerializedName("iconId")
    @Expose
    private String iconId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetrolBunkName() {
        return petrolBunkName;
    }

    public void setPetrolBunkName(String petrolBunkName) {
        this.petrolBunkName = petrolBunkName;
    }

    public String getPetrolBunkAddress() {
        return petrolBunkAddress;
    }

    public void setPetrolBunkAddress(String petrolBunkAddress) {
        this.petrolBunkAddress = petrolBunkAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCompoundCode() {
        return compoundCode;
    }

    public void setCompoundCode(String compoundCode) {
        this.compoundCode = compoundCode;
    }

    public String getBunkType() {
        return bunkType;
    }

    public void setBunkType(String bunkType) {
        this.bunkType = bunkType;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

}
