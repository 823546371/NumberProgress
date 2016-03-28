package com.jwenfeng.numberprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 当前类注释:
 * 项目名：NumberProgress
 * 包名：com.jwenfeng.numberprogress
 * 作者：jinwenfeng on 16/3/24 14:29
 * 邮箱：823546371@qq.com
 * QQ： 823546371
 * 公司：南京穆尊信息科技有限公司
 * © 2016 jinwenfeng
 * ©版权所有，未经允许不得传播
 */
public class CircleNumberProgress extends View {

    /**
     * 百分比文字颜色
     * */
    private int textColor;
    /**
     * 百分比文字大小
     * */
    private float textSize;

    private int unProgressColor;
    private int progressColor;


    private Paint circlePaint;
    private Paint arcPaint;
    private Paint textPaint;

    private float arcWidth;

    private RectF oval =new RectF(0,0,0,0);

    /**
     * 当前进度
     * */
    private int currentProgress = 0;
    /**
     * 总进度
     * */
    private int maxProgress = 100;

    private String currentDrawText;

    public CircleNumberProgress(Context context) {
        this(context, null);
    }

    public CircleNumberProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleNumberProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.NumberProgress);
        textColor  = a.getColor(R.styleable.NumberProgress_textColor, Color.parseColor("#3498DB"));
        textSize = sp2px(context, a.getDimension(R.styleable.NumberProgress_textSize, 14f));
        unProgressColor = a.getColor(R.styleable.NumberProgress_unProgressColor, Color.parseColor("#113498DB"));
        progressColor = a.getColor(R.styleable.NumberProgress_progressColor, Color.parseColor("#3498DB"));

        currentProgress = a.getInt(R.styleable.NumberProgress_currentProgress, 0);
        maxProgress = a.getInt(R.styleable.NumberProgress_maxProgress, 100);

        arcWidth = dip2px(context,a.getDimension(R.styleable.NumberProgress_progressWidth,4));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(arcWidth);
        circlePaint.setColor(unProgressColor);

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setColor(progressColor);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int padding = getPaddingTop()+getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST){
                result = Math.max(result, size);
            }
        }
        return result;
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) dip2px(getContext(),100);
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) dip2px(getContext(),100);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 绘制浅色圆环
        // 1.圆心（x,y）坐标值
        float centerX = (getWidth()-getPaddingLeft()-getPaddingRight())/2.0f;
        float centerY = (getHeight() - getPaddingTop() - getPaddingBottom())/2.0f;
        // 2.圆环半径
        float radius;
        if (getWidth() >= getHeight()) {
            radius = (centerY - arcWidth / 2);
        }else{
            radius = (centerX - arcWidth / 2);
        }
        canvas.drawCircle(centerX,centerY,radius,circlePaint);

        oval.left = centerX - radius;
        oval.right = centerX + radius;
        oval.top = centerY - radius;
        oval.bottom = centerY+radius;

        canvas.drawArc(oval, 0, (float)360 * currentProgress / (float)maxProgress, false, arcPaint);

        currentDrawText = String.format("%d", currentProgress * 100 /maxProgress);
        currentDrawText = (currentDrawText)+"%";
        float drawTextWidth = textPaint.measureText(currentDrawText);

        canvas.drawText(currentDrawText,centerX-drawTextWidth/2.0f,centerY-((textPaint.descent() + textPaint.ascent()) / 2.0f),textPaint);



    }

    private int measureHeight(int heightMeasureSpec) {
        int result;
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int padding = getPaddingTop()+getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY){
            result = size;
        }else{
            result = getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST){
                result = Math.min(result, size);
            }
        }
        return result;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }

    /**
     * dp2px
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    /**
     * sp2px
     */
    public static float sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (spValue * fontScale + 0.5f);
    }
}
