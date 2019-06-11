package app.com.loyaltyapp.bounty.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

public class ComicTextView extends AppCompatTextView {

    public ComicTextView(Context context) {
        super(context);
        init(context);
    }

    public ComicTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ComicTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        /*Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "SEGOEUI.TTF");
        setTypeface(tf);*/
        setCustomFont(this, context);

    }

    public void setCustomFont(TextView textview, Context context) {
        Typeface tf = Util.FontCache.get("COMIC.TTF", context);
        if (tf != null) {
            textview.setTypeface(tf);
        }
    }


}
