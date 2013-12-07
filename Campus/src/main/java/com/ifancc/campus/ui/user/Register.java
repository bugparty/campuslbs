package com.ifancc.campus.ui.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.ifancc.campus.R;

public class Register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);


    }


}
