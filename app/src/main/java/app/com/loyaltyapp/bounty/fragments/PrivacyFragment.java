package app.com.loyaltyapp.bounty.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import app.com.loyaltyapp.bounty.R;


public class PrivacyFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_privacy, container, false);
        init(view);



        return view;
    }

    private void init(View view) {

        try {
            WebView wvTermsAndConditions = (WebView) view.findViewById(R.id.wvTermsAndConditions);
            wvTermsAndConditions.getSettings().setJavaScriptEnabled(true);

            WebSettings webSettings = wvTermsAndConditions.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setLoadWithOverviewMode(true);

            wvTermsAndConditions.loadUrl("https://developer.android.com/studio/terms");


        } catch (Exception ex) {
            Log.e("init: ", ex + " >>");
        }


    }



}
