package com.ifancc.campus.ui.user;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ifancc.campus.R;
import java.util.Random;
public class Finpas extends Activity {
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_findpas);
        Random ra=new Random();

        int a1=ra.nextInt(10);
        int b1=ra.nextInt(10);
        int c1=ra.nextInt(10);
        int d1=ra.nextInt(10);
        final String[] check1 = new String[1];
      final String check=(""+a1+b1+c1+d1).trim();
      final TextView tv=(TextView)findViewById(R.id.text4);
        tv.setText(check);
        Button bu1=(Button)findViewById(R.id.editText2);
        bu1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Random ra=new Random();
                int a2=ra.nextInt(10);
                int b2=ra.nextInt(10);
                int c2=ra.nextInt(10);
                int d2=ra.nextInt(10);
             check1[0] =(""+a2+b2+c2+d2).trim();

                tv.setText(check1[0]);
            }

        });
        Button bu=(Button)findViewById(R.id.button);

         bu.setOnClickListener(new View.OnClickListener() {
               public void onClick(View arg0) {
                   EditText et=(EditText)findViewById(R.id.editText);
                   String edit= et.getText().toString();
                   if(edit.equals(check) || edit.equals(check1[0]) ){
                       Toast.makeText(Finpas.this, "正在发送，请稍后", Toast.LENGTH_LONG).show();
                   }
                   else{
                       Toast.makeText(Finpas.this, "重新输入", Toast.LENGTH_LONG).show();
                   }
                }

        });

   }
}