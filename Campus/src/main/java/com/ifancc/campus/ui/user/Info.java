package com.ifancc.campus.ui.user;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.ifancc.campus.ui.Map.MapActivity;
import com.ifancc.campus.R;

import java.io.File;

/**
 * Created by LiPengfei on 13-11-17.
 */
public class Info extends TabActivity implements View.OnClickListener{
    private TabHost mTabHost = null;
    private TabWidget mTabWidget = null;
    private ListView muserpagelist;
    private String [] muserpagelistTexts;
    private String [] muserpagelistText;
    private LinearLayout muser_friends;
    private ImageView imageView;
    private File sdcardTempFile;
    private AlertDialog dialog;
    private int crop=180;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_home);
/*显示App icon左侧的back键*/
     //   ActionBar actionBar = getActionBar();// actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeButtonEnabled(true);
        imageView=(ImageView)findViewById(R.id.imageView11);
        Bitmap bt = BitmapFactory.decodeResource(getResources(), R.drawable.bomanhan);
        imageView.setImageBitmap(toRoundBitmap(bt));
        imageView.setOnClickListener(this);
        sdcardTempFile = new File("/mnt/sdcard/", "tmp_pic_" + SystemClock.currentThreadTimeMillis() + ".jpg");

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mTabWidget = mTabHost.getTabWidget();
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setContent(
                R.id.LinearLayout001).setIndicator("个人信息"));
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setContent(
                R.id.LinearLayout002).setIndicator("日志"));
        mTabHost.addTab(mTabHost.newTabSpec("tab3").setContent(
                R.id.LinearLayout003).setIndicator("相册"));
        muser_friends=(LinearLayout)this.findViewById(R.id.user_friends);
        muserpagelist=(ListView)this.findViewById(R.id.user_details_list);

        muserpagelistTexts=getResources().getStringArray(R.array.userpage_list);
        muserpagelistText=getResources().getStringArray(R.array.navigation_list);
        muserpagelist.setAdapter(new ArrayAdapter<String>(this,R.layout.userpagelist_item,R.id.userpage_list_text1,muserpagelistTexts));

        muser_friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Info.this,MapActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case  android.R.id.home:
                this.finish();
                overridePendingTransition(R.anim.out_form_left,R.anim.in_form_right);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if(v==imageView){
            if(dialog==null){
                dialog=new AlertDialog.Builder(this).setItems(new String[]{"相机","相册"},
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                if(which==0){
                                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                                    intent.putExtra("output", Uri.fromFile(sdcardTempFile));
                                    intent.putExtra("crop", "true");
                                    intent.putExtra("aspectX", 1);// 裁剪框比例
                                    intent.putExtra("aspectY", 1);

                                    intent.putExtra("outputX", crop);// 输出图片大小
                                    intent.putExtra("outputY", crop);

                                    startActivityForResult(intent, 101);

                                }
                                else{
                                    Intent intent = new Intent("android.intent.action.PICK");
                                    intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                                    intent.putExtra("output", Uri.fromFile(sdcardTempFile));
                                    intent.putExtra("crop", "true");
                                    intent.putExtra("aspectX", 1);// 裁剪框比例
                                    intent.putExtra("aspectY", 1);
                                    intent.putExtra("outputX", crop);// 输出图片大小
                                    intent.putExtra("outputY", crop);
                                    startActivityForResult(intent, 100);

                                }

                            }
                        }).create();
            }
            if (!dialog.isShowing()) {
                dialog.show();
            }

        }

    }
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode == RESULT_OK) {

            Bitmap bmp = BitmapFactory.decodeFile(sdcardTempFile.getAbsolutePath());

            imageView.setImageBitmap(toRoundBitmap(bmp));

        }

    }
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
}


