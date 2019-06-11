package app.com.loyaltyapp.bounty.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class KarlaItalicTextView extends AppCompatTextView {


    public KarlaItalicTextView(Context context) {

        super(context);

        init(context);
    }

    public KarlaItalicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KarlaItalicTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        setCustomFont(this, context);
    }

    private void setCustomFont(KarlaItalicTextView textview, Context context) {

        Typeface tf = Util.FontCache.get("Karla-Italic.ttf", context);
        if (tf != null) {
            textview.setTypeface(tf);
        }
    }


}
