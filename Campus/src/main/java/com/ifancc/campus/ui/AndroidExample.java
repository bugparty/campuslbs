package com.ifancc.campus.ui;

import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ifancc.campus.R;

public class AndroidExample extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_oauth);
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	Button beginOuathBtn=  (Button) findViewById(R.id.buttonOAuth);
    	

    	beginOuathBtn.setOnClickListener(new Button.OnClickListener()
        {

            public void onClick( View v )
            {   
            	Weibo weibo = new Weibo();
            	RequestToken requestToken;
				try {
					requestToken =weibo.getOAuthRequestToken("CampusOAuth://OAuthActivity");
	    			OAuthConstant.getInstance().setRequestToken(requestToken);
					Uri uri = Uri.parse(requestToken.getAuthenticationURL()+ "&display=mobile");
	    			startActivity(new Intent(Intent.ACTION_VIEW, uri));
				} catch (WeiboException e) {
					e.printStackTrace();
				}
    			
            }
        } );
	}
}