
package packag.nnk.com.userfuelapp.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transaction {

    @SerializedName("petrolBunkName")
    @Expose
    private String petrolBunkName;
    @SerializedName("petrolBunkAddress")
    @Expose
    private String petrolBunkAddress;
    @SerializedName("petrolBunkLatLang")
    @Expose
    private String petrolBunkLatLang;
    @SerializedName("driverAggregator")
    @Expose
    private String driverAggregator;
    @SerializedName("petrolBunkIcon")
    @Expose
    private String petrolBunkIcon;
    @SerializedName("transactionTime")
    @Expose
    private String transactionTime;
    @SerializedName("transactionAmount")
    @Expose
    private String transactionAmount;

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

    public String getDriverAggregator() {
        return driverAggregator;
    }

    public void setDriverAggregator(String driverAggregator) {
        this.driverAggregator = driverAggregator;
    }

    public String getPetrolBunkIcon() {
        return petrolBunkIcon;
    }

    public void setPetrolBunkIcon(String petrolBunkIcon) {
        this.petrolBunkIcon = petrolBunkIcon;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

}
