package packag.nnk.com.userfuelapp.about_us;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.BaseActivity;

/**
 * Created by Kapil Katiyar on 6/23/2016.
 */
public class AboutUsScreen extends BaseActivity {

    private Toolbar mToolbar;
    private TextView title;
    WebView webView;
//    CircleProgressBar bar;
    public static String url="https://www.driver.gudy.in/";
    public static String title_text="";
    TextView help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_us_screen);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("About Us");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        String text = "<html><body style=\"text-align:justify\"> %s </body></Html>";

        //String data = getResources().getString(R.string.about_us);
//        bar=(CircleProgressBar) findViewById(R.id.progressBar2);
        webView = (WebView) findViewById(R.id.WebView);
        webView.setWebViewClient(new MyBrowser());
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });
        custom();
    }


    private void custom()
    {





    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);



        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
          //  bar.setVisibility(View.GONE);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }
}
