package com.example.newsapplication;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewINtent extends AppCompatActivity {
    WebView webView;
    TextView textView;
    // Intent fetchUrl=getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_inent);
        webView = findViewById(R.id.wbViw);
        getSupportActionBar().hide();
        String fetchedUrl = getIntent().getExtras().getString("url");

        webView.loadUrl(fetchedUrl);
        webView.setWebViewClient(new WebViewClient());

    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        super.onBackPressed();
    }
}
