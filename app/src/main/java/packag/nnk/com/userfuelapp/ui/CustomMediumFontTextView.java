package packag.nnk.com.userfuelapp.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class CustomMediumFontTextView extends androidx.appcompat.widget.AppCompatTextView
    {

        public CustomMediumFontTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public CustomMediumFontTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public CustomMediumFontTextView(Context context) {
            super(context);
            init();
        }

        public void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/GOTHAMRND-MEDIUM.OTF");
            setTypeface(tf);

        }
}
