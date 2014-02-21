package com.ifancc.campus.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import com.ifancc.campus.R;
/**
 * Created by bowman on 14-2-14.
 */
public class CircleView extends View {
    private Boolean mShowText;
    private Integer mTextPos;
    private Paint myPen;

    private Drawable mRawImage;
    private Drawable mRoundedImage;
    private int mResId;
    private Uri mUri;
    private String mRawUriStr;
    private int mDrawableWidth;
    private int mDrawableHeight;
    private int mWidth, mHeight;
    private Resources mRes;
    private int mRadius;
    public final static String TAG = "CircleView";
    private static  enum Oriention {vertical, horitonal};
    private Oriention mRawOri;

    private Context mContext;
    private Path mCirclePath;
    private void updateImage(){
        roundImage();

        configureBounds();

    }
    protected void onDrawableChanged(){
        Log.d(TAG, "onDrawableChanged");
        mDrawableHeight = mRawImage.getIntrinsicHeight();
        mDrawableWidth = mRawImage.getIntrinsicWidth();
        mRadius = (mDrawableHeight < mDrawableWidth?mDrawableHeight/2:mDrawableWidth/2) ;
        if(mDrawableWidth > mDrawableHeight){
            mRawOri = Oriention.horitonal;
        }else{
            mRawOri = Oriention.vertical;
        }
        Log.d(TAG, "radius:"+mRadius);
        mCirclePath.addCircle(mDrawableHeight/2, mDrawableWidth/2,mRadius,
                Path.Direction.CW
                );

    }
    private void initCircleView(){
        myPen = new Paint();
        myPen.setColor(Color.RED);
        mCirclePath = new Path();
        mRes= mContext.getResources();
    }
    private Bitmap drawable2bitmap(Drawable drawable){
        Bitmap bmpRaw = Bitmap.createBitmap(mDrawableWidth, mDrawableHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpRaw);
        configureBounds();
        canvas.clipPath(mCirclePath);
        drawable.draw(canvas);
        return bmpRaw;
    }
    public boolean dumpBitmap(Bitmap out){
        Log.d(TAG, "dump Bitmap");
        SimpleDateFormat sDateFormat   =   new   SimpleDateFormat("yyyy_MM-ddhh_mm_ss");
        String   now   =   sDateFormat.format(new   java.util.Date());

       // File f = new File(now+".bmp");
        FileOutputStream of;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();


//you can create a new file name "test.jpg" in sdcard folder.
        File f = new File(Environment.getExternalStorageDirectory()
                + File.separator + now+".jpg");
        Log.d(TAG, "file path:"+f.getPath());
        try{
            out.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
            f.createNewFile();
//write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

// remember close de FileOutput
            fo.close();

        }catch (FileNotFoundException e){
            Toast.makeText(mContext,"failed to dump",Toast.LENGTH_SHORT);
            Log.d(TAG, "save failed, file not found");
            e.printStackTrace();
            return false;
        }catch( IOException e){
            Toast.makeText(mContext,"failed to dump",Toast.LENGTH_SHORT);
            Log.d(TAG, "save failed, IO exception"+e.toString());
            e.printStackTrace();
            return false;
        }



        return true;
    }
    private void cachePic(){
        mRoundedImage = new BitmapDrawable(mRes,getCircled(mRawImage, mWidth));
    }
    private  void roundImage(){
        Log.d(TAG,"roundImage");
        if(mWidth==0 || mHeight==0)
            return;

        Bitmap out = getCircled(mRawImage,mWidth);
        mRoundedImage = new BitmapDrawable(mRes, out);
        mRoundedImage.setBounds(0,0,mWidth,mWidth);
        Log.d(TAG, "mRoundedImage\theight"+mRoundedImage.getIntrinsicHeight()+"\twidth"+mRoundedImage.getIntrinsicWidth());

    }
    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CircleView(Context context) {
        super(context);
        mContext = context;
        initCircleView();
    }
    Bitmap getCircled(Drawable drawable,int sideLength){
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap out;
        //crop a square
        if(h>w){
            //vertical shape image
            out = copy(drawable, 0, (h - w) / 2, w, w);
        }else{
            //horizontal
            out = copy(drawable, (w - h) / 2, 0, h, h);
        }
        out = circled(out);
        return crop(out,sideLength,sideLength);

    }

    Bitmap draw2bmp(Drawable drawable){
        int h = drawable.getIntrinsicHeight();
        int w = drawable.getIntrinsicWidth();
        Bitmap bmp = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(c);
        return bmp;

    }
    Bitmap circled(Bitmap src){
        int w=src.getWidth();
        int h = src.getHeight();
        if(w!=h)
            throw new IllegalArgumentException("the width and the height must be equal");
        Bitmap out = Bitmap.createBitmap(h,h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(out);
        Path pathCircle = new Path();

        pathCircle.addCircle(h / 2, h / 2,h/2,
                Path.Direction.CW
        );

        canvas.clipPath(pathCircle);
        canvas.drawBitmap(src, 0, 0, new Paint());
        return out;
    }
    Bitmap circledCopy(Drawable drawable,int x, int y,int width, int height){
        int drawableHeight = drawable.getIntrinsicHeight();
        int drawableWidth = drawable.getIntrinsicWidth();
        Bitmap out = Bitmap.createBitmap(drawableWidth,drawableHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(out);
        Path pathCircle = new Path();
        int radius =  (drawableHeight < drawableWidth?drawableHeight/2:drawableWidth/2) ;
        pathCircle.addCircle(drawableHeight / 2, drawableWidth / 2, radius,
                Path.Direction.CW
        );
        c.clipPath(pathCircle);
        drawable.setBounds(0, 0, drawableWidth, drawableHeight);
        draw(c);

        try{
            out = Bitmap.createBitmap(out, x,y,width,height);
        }catch( IllegalArgumentException e){
            Log.e(TAG,"copy:"+e.toString());
            throw new IllegalArgumentException(e);
        }
        return out;

    }
    Bitmap copy(Drawable drawable,int x, int y,int width, int height){
        Bitmap out = draw2bmp(drawable);
        try{
            out = Bitmap.createBitmap(out, x,y,width,height);
        }catch( IllegalArgumentException e){
            Log.e(TAG,"copy:"+e.toString());
            throw new IllegalArgumentException(e);
        }
        return out;

    }

    Bitmap crop(Bitmap bitmap,float dstW,float dstH){
        Bitmap out ;
        float ratioW = dstW / bitmap.getWidth();
        float ratioH = dstH / bitmap.getHeight();
        Matrix m = new Matrix();
        m.setScale(ratioW,ratioH);
        out  = Bitmap.createBitmap(bitmap,0,0, bitmap.getWidth(),
                bitmap.getHeight(),m,true);
        return out;
    }
    private void configureBounds(){

    }
    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initCircleView();
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleView,
                0, 0);


        Drawable d = a.getDrawable(R.styleable.CircleView_src);
        if (d != null){
            //Log.d(TAG, "src successful loaded");

            updateImage(d);
            requestLayout();
            invalidate();
        }

        a.recycle();

    }


    private void updateImage(Drawable d){
        mRawImage = d;
        onDrawableChanged();
        updateImage();
    }

    public void setImageResource(int resId){
        mResId = resId;
        //@todo:update the image and load image
        onDrawableChanged();
        invalidate();
    }
    /**
     * Sets a drawable as the content of this ImageView.
     *
     * @param drawable The drawable to set
     */
    public void setImageDrawable(Drawable drawable){
        mRawImage = drawable;
        onDrawableChanged();
        //todo:...
        invalidate();
    }
    /**
     * Sets a Bitmap as the content of this ImageView.
     *
     * @param bm The bitmap to set
     */
    public void setImageBitmap(Bitmap bm){
        mRawImage = new BitmapDrawable(mContext.getResources(),bm);
        //todo:...
        onDrawableChanged();
        invalidate();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.d(TAG, "onDraw");
        mRoundedImage.draw(canvas);

        //canvas.drawCircle(250,250,50,myPen);
        //Log.d(TAG , "drawing mRawImage"+mRawImage.isVisible());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
        switch(MeasureSpec.getMode(widthMeasureSpec)){
            case MeasureSpec.AT_MOST:
                Log.d(TAG, "at_most");
                break;
            case MeasureSpec.EXACTLY:
                Log.d(TAG, "exactly");
                mWidth = MeasureSpec.getSize(widthMeasureSpec);
                break;
            default:
                break;
        }
        switch(MeasureSpec.getMode(heightMeasureSpec)){
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mHeight = MeasureSpec.getSize(heightMeasureSpec);
                break;
            default:
                break;
        }
        updateImage();
    }



}
