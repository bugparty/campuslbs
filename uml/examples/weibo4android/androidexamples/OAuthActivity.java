package weibo4android.androidexamples;

import java.util.List;

import weibo4android.Paging;
import weibo4android.Status;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.http.AccessToken;
import weibo4android.http.RequestToken;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OAuthActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		Uri uri=this.getIntent().getData();
		try {
			RequestToken requestToken= OAuthConstant.getInstance().getRequestToken();
			AccessToken accessToken=requestToken.getAccessToken(uri.getQueryParameter("oauth_verifier"));
			OAuthConstant.getInstance().setAccessToken(accessToken);
			TextView textView = (TextView) findViewById(R.id.TextView01);
			textView.setText("得到AccessToken的key和Secret,可以使用这两个参数进行授权登录了.\n Access token:\n"+accessToken.getToken()+"\n Access token secret:\n"+accessToken.getTokenSecret());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		Button button=  (Button) findViewById(R.id.Button01);
		button.setText("某一话题下的微博");
		button.setOnClickListener(new Button.OnClickListener()
        {

            public void onClick( View v )
            {
    				Weibo weibo=OAuthConstant.getInstance().getWeibo();
    				weibo.setToken(OAuthConstant.getInstance().getToken(), OAuthConstant.getInstance().getTokenSecret());
    				List<Status> friendsTimeline;
    					try {
							friendsTimeline = weibo.getTrendStatus("seaeast", new Paging(1,20));
							StringBuilder stringBuilder = new StringBuilder("");
	    					for (Status status : friendsTimeline) {
	    						stringBuilder.append(status.getUser().getScreenName() + "说:\n"
	    								+ status.getText() + "\n--------------------------------------------------\n");
	    					}
	    					TextView textView = (TextView) findViewById(R.id.TextView01);
	    					textView.setText(stringBuilder.toString());
						} catch (WeiboException e) {
							e.printStackTrace();
						}
    					
            }
        } );
//		
	}
}
