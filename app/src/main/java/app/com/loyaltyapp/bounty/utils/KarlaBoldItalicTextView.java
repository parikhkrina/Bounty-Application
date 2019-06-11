package app.com.loyaltyapp.bounty.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class KarlaBoldItalicTextView extends AppCompatTextView {


    public KarlaBoldItalicTextView(Context context) {
        super(context);
        init(context);
    }

    public KarlaBoldItalicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KarlaBoldItalicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        setCustomFont(this, context);
    }


    private void setCustomFont(KarlaBoldItalicTextView textView, Context context) {
        Typeface tf = Util.FontCache.get("Karla-BoldItalic.ttf", context);
        if (tf != null) {
            textView.setTypeface(tf);
        }
    }

}


