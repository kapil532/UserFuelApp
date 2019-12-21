
package packag.nnk.com.userfuelapp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("history")
    @Expose
    private List<History_> history = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<History_> getHistory() {
        return history;
    }

    public void setHistory(List<History_> history) {
        this.history = history;
    }

}
