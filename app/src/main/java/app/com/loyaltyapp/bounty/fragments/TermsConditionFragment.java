package app.com.loyaltyapp.bounty.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import app.com.loyaltyapp.bounty.R;


public class TermsConditionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terms_condition, container, false);
        init(view);
        return view;
    }

    private void init(View view) {

        try {
            WebView wvTermsAndConditions = (WebView) view.findViewById(R.id.wvTermsAndConditions);
            wvTermsAndConditions.getSettings().setJavaScriptEnabled(true);
            wvTermsAndConditions.loadUrl("https://developer.android.com/studio/terms");

        } catch (Exception ex) {
            Log.e("init: ", ex + " >>");
        }

    }


}
