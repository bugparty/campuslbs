package com.ifancc.campus.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ifancc.campus.R;
import com.ifancc.campus.config.URL;
import com.ifancc.campus.dao.OAuthTokenDao;
import com.ifancc.campus.exceptions.WeiboException;
import com.ifancc.campus.util.LogUtils;

/**
 * Created by bowman on 13-12-24.
 */
public class OAuthActivity extends BaseActivity {
    public static String TAG = LogUtils.makeLogTag(OAuthActivity.class);
    public final static int LOGIN_PENDING = 1;
    public final static int LOGIN_SUCCESS = 2;
    public final static int LOGIN_FAILED = 3;

    Handler loginHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case OAuthActivity.LOGIN_PENDING:
                    OAuthActivity.this.mMainContent.setVisibility(View.GONE);
                    OAuthActivity.this.mLoginSpinner.setVisibility(View.VISIBLE);
                    break;
                case OAuthActivity.LOGIN_SUCCESS:
                    Bundle b = msg.getData();
                    String token = b.getString("token");
                    Toast.makeText(OAuthActivity.this,token, Toast.LENGTH_LONG );

                    break;
            }
        }
    };

    private EditText mEmail;
    private EditText mPassword;
    private View mOAuthBtn;
    private View mLoginSpinner;
    private View mMainContent;
    private GetTokenThread mGetTokenThread;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_oauth);
        mEmail = (EditText)findViewById(R.id.et_email);
        mPassword = (EditText)findViewById(R.id.et_password);
        mOAuthBtn = findViewById(R.id.btn_OAuth);
        mLoginSpinner = findViewById(R.id.loading_spinner);
        mLoginSpinner.setVisibility(View.GONE);
        mMainContent = findViewById(R.id.main_content);
        mOAuthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animate2Wait();
                mGetTokenThread = new GetTokenThread(mEmail.getText().toString(),
                        mPassword.getText().toString(), URL.APP_KEY,URL.APP_SECRET
                );

                new Thread(mGetTokenThread).start();
            }
        });


    }
    public void animate2Wait(){
        mMainContent.setVisibility(View.GONE);
        mLoginSpinner.setVisibility(View.VISIBLE);
    }
    private class GetTokenThread extends OAuthTokenDao implements  Runnable{
        private GetTokenThread(String username, String password, String key, String secret) {
            super(username, password, key, secret);
        }

        @Override
        public void run() {
            try {
                String [] result = this.login();
                Message msg = new Message();
                if (result == null){
                    msg.what = OAuthActivity.LOGIN_FAILED;
                    OAuthActivity.this.loginHandler.sendMessage(msg);
                }else{
                    msg.what = OAuthActivity.LOGIN_SUCCESS;
                    Bundle b = new Bundle();
                    b.putString("token",result[0]);
                    b.putString("expires_in",result[1]);
                    msg.setData(b);

                    OAuthActivity.this.loginHandler.sendMessage(msg);
                }


            } catch (WeiboException e) {
                e.printStackTrace();
            }
        }
    }

}
