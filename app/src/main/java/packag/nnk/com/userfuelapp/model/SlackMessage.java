package packag.nnk.com.userfuelapp.model;

import com.google.gson.annotations.SerializedName;

public class SlackMessage
{
    @SerializedName("text")
   private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
