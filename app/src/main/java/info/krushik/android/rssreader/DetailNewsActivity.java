package info.krushik.android.rssreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class DetailNewsActivity extends AppCompatActivity {

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        mWebView = (WebView) findViewById(R.id.webView);

        mWebView.setWebViewClient(new MyWebViewClient());

        Intent intent = getIntent();
        String url = intent.getStringExtra(CardAdapter.TEG_URL_NEWS);

        // включаем поддержку JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);

    }
//кнопка Back
//    @Override
//    public void onBackPressed() {
//        if(mWebView.canGoBack()) {
//            mWebView.goBack();
//        } else {
//            super.onBackPressed();
//        }
//    }

    //открываем браузер в приложении
    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
}
