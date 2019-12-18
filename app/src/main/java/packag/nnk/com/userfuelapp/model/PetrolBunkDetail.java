
package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PetrolBunkDetail {

    @SerializedName("petrolBunkName")
    @Expose
    private String petrolBunkName;


    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("petrolBunkAddress")
    @Expose
    private String petrolBunkAddress;
    @SerializedName("petrolBunkLatLang")
    @Expose
    private String petrolBunkLatLang;
    @SerializedName("compoundCode")
    @Expose
    private String compoundCode;
    @SerializedName("bunkType")
    @Expose
    private String bunkType;
    @SerializedName("iconId")
    @Expose
    private String iconId;

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

    public String getPetrolBunkLatLang() {
        return petrolBunkLatLang;
    }

    public void setPetrolBunkLatLang(String petrolBunkLatLang) {
        this.petrolBunkLatLang = petrolBunkLatLang;
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
