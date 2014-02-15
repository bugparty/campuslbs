package com.ifancc.campus.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.ifancc.campus.R;
import com.ifancc.campus.config.URL;
import com.ifancc.campus.ui.dialog.WeiboErrorDialog;

/**
 * Created by bowman on 13-12-25.
 */
public class OAuth2Activity extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oauthactivity_layout);
        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new OAuthWebViewClient());


        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSaveFormData(false);
        settings.setSavePassword(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }


    public static enum DBResult {
        add_successfuly, update_successfully
    }

    public  class OAuthWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            if (url.startsWith(URL.DIRECT_URL)) {

                handleRedirectUrl(view, url);
                view.stopLoading();
                return;
            }
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            new WeiboErrorDialog().show(getSupportFragmentManager(), "");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (!url.equals("about:blank"))
                completeRefresh();
        }
    }
    public void refresh() {
        webView.clearView();
        webView.loadUrl("about:blank");
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // ImageView iv = (ImageView) inflater.inflate(R.layout.refresh_action_view, null);

        //Animation rotation = AnimationUtils.loadAnimation(this, R.anim.refresh);
       // iv.startAnimation(rotation);

        //refreshItem.setActionView(iv);
       // webView.loadUrl(getWeiboOAuthUrl());
    }

    private void completeRefresh() {
        //if (refreshItem.getActionView() != null) {
        //    refreshItem.getActionView().clearAnimation();
       //     refreshItem.setActionView(null);
        //}
    }

    private void handleRedirectUrl(WebView view, String url) {
       // Bundle values = Utility.parseUrl(url);

       // String error = values.getString("error");
        //String error_code = values.getString("error_code");

        Intent intent = new Intent();
       // intent.putExtras(values);

       if (true /**error == null && error_code == null**/) {

       //     String access_token = values.getString("access_token");
       //     String expires_time = values.getString("expires_in");
       //     setResult(RESULT_OK, intent);
            //new OAuthTask().execute(access_token, expires_time);
        } else {
           // Toast.makeText(OAuth2Activity.this, getString(R.string.you_cancel_login), Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}
