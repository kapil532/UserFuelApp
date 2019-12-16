
package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RangeTransaction {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("driverId")
    @Expose
    private String driverId;
    @SerializedName("petrolBunkId")
    @Expose
    private String petrolBunkId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("paymentDate")
    @Expose
    private String paymentDate;
    @SerializedName("petrolBunkDetail")
    @Expose
    private PetrolBunkDetail petrolBunkDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getPetrolBunkId() {
        return petrolBunkId;
    }

    public void setPetrolBunkId(String petrolBunkId) {
        this.petrolBunkId = petrolBunkId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PetrolBunkDetail getPetrolBunkDetail() {
        return petrolBunkDetail;
    }

    public void setPetrolBunkDetail(PetrolBunkDetail petrolBunkDetail) {
        this.petrolBunkDetail = petrolBunkDetail;
    }

}
