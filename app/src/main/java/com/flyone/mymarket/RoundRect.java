package com.flyone.mymarket;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.allure.lbanners.utils.ScreenUtils;

/**
 * Created by wl624 on 2018/4/20.
 */

public class RoundRect extends View {
    private Context context;
    public RoundRect(Context context) {
        super(context);
        this.context=context;
    }

    public RoundRect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    public RoundRect(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xffffffff);
        Paint paint1=new Paint();
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setColor(0xffeee);

        paint1.setTextSize(ScreenUtils.dip2px(context,20));
        paint1.setTextAlign(Paint.Align.CENTER);
        canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()),getHeight()/2,getHeight()/2,paint);
    }
}
