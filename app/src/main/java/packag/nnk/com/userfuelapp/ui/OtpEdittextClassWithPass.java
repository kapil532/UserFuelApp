package packag.nnk.com.userfuelapp.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

import packag.nnk.com.userfuelapp.R;

public class OtpEdittextClassWithPass extends AppCompatEditText
{
        private float mSpace = 24; //24 dp by default, space between the lines
        private float mNumChars = 4;
        private float mLineSpacing = 8; //8dp by default, height of the text from our lines
        private int mMaxLength = 4;
        private float mLineStroke = 2;
        private Paint mLinesPaint;
        private OnClickListener mClickListener;

        public OtpEdittextClassWithPass(Context context) {
            super(context);
        }

        public OtpEdittextClassWithPass(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        public OtpEdittextClassWithPass(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context, attrs);
        }

        private void init(Context context, AttributeSet attrs) {
            float multi = context.getResources().getDisplayMetrics().density;
//            setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
//            setTransformationMethod(PasswordTransformationMethod.getInstance());
            mLineStroke = multi * mLineStroke;
            mLinesPaint = new Paint(getPaint());
            mLinesPaint.setStrokeWidth(mLineStroke);
            mLinesPaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
            setBackgroundResource(0);
            mSpace = multi * mSpace; //convert to pixels for our density
            mLineSpacing = multi * mLineSpacing; //convert to pixels for our density
            mNumChars = mMaxLength;

            super.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // When tapped, move cursor to end of text.
                    setSelection(getText().length());
                    if (mClickListener != null) {
                        mClickListener.onClick(v);
                    }
                }
            });
        }

        @Override
        public void setOnClickListener(OnClickListener l) {
            mClickListener = l;
        }

        @Override
        public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
            throw new RuntimeException("setCustomSelectionActionModeCallback() not supported.");
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int availableWidth = getWidth() - getPaddingRight() - getPaddingLeft();
            float mCharSize;
            if (mSpace < 0) {
                mCharSize = (availableWidth / (mNumChars * 2 - 1));
            } else {
                mCharSize = (availableWidth - (mSpace * (mNumChars - 1))) / mNumChars;
            }

            int startX = getPaddingLeft();
            int bottom = getHeight() - getPaddingBottom();
            String ar[]= {"*","*","**","***","****"};
            //Text Width
            Editable text = getText();
//            Log.d("TEXTT","TES"+text.toString());
            Editable text1 =Editable.Factory.getInstance().newEditable(ar[text.length()]);// new SpannableStringBuilder("*");
            int textLength = text.length();
            float[] textWidths = new float[textLength];
            getPaint().getTextWidths(getText(), 0, textLength, textWidths);


            for (int i = 0; i < mNumChars; i++) {
                canvas.drawLine(startX, bottom, startX + mCharSize, bottom, mLinesPaint);
                if (getText().length() > i) {
                    float middle = startX + mCharSize / 2;
                    canvas.drawText(text1, i, i + 1, middle - textWidths[0] / 2, bottom - mLineSpacing, getPaint());
                }
                if (mSpace < 0) {
                    startX += mCharSize * 2;
                } else {
                    startX += mCharSize + mSpace;
                }
            }
        }
}
