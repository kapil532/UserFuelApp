
package packag.nnk.com.userfuelapp.transaction;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionPojo {

    @SerializedName("transaction")
    @Expose
    private List<Transaction> transaction = null;

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

}
