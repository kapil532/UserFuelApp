
package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiError
{

    @SerializedName("error")
    @Expose
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
