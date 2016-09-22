package com.sk.blurimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by owner on 12-Apr-15.
 */
public class BlurImageView extends ImageView {
    private int radius;

    public BlurImageView(Context context) {
        super(context);


        setScaleType(ScaleType.MATRIX);
    }

    public BlurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.blur, 0, 0);
        try {
            radius = ta.getInt(R.styleable.blur_blur_radius, 1);
        } finally {
            ta.recycle();
        }
        setScaleType(ScaleType.MATRIX);

    }

    public BlurImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.blur, 0, 0);
        try {
            radius = ta.getInt(R.styleable.blur_blur_radius, 1);
        } finally {
            ta.recycle();
        }
        setScaleType(ScaleType.MATRIX);

    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        Matrix matrix = getImageMatrix();
        float scaleFactor = getWidth() / (float) getDrawable().getIntrinsicWidth();
        matrix.setScale(scaleFactor, scaleFactor, 0, 0);
        setImageMatrix(matrix);
        return super.setFrame(l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();

        BlurImage blurImage = new BlurImage();

        Bitmap roundBitmap = BlurImage.fastblur(getContext(), bitmap, radius);
        canvas.drawBitmap(roundBitmap, 0, 0, new Paint());

    }

}
