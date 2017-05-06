package com.frosquivel.examplemagicalcamera.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import com.frosquivel.examplemagicalcamera.R;

/**
 * Created by Fabian on 04/03/2017.
 * This class open all webViews that the app need
 */

public class WebViewActivity extends Activity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_web_view);
        webView = (WebView) this.findViewById(R.id.webView);

        String link = getIntent().getStringExtra("link");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }
}
