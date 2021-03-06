package com.example.mvvmdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.baselibrary.utils.LogUtils;
import com.example.mvvmdemo.R;
import com.lihang.smartloadview.UIUtil;

/**
 * Created by leo
 * on 2019/11/8.
 */
public class LeoTitleBar extends FrameLayout {

    public LinearLayout leoBar;
    public TextView txt_title;
    public ImageView bar_left_btn;
    public TextView bar_right_text;
    public ImageView bar_right_btn;
    //这个用来添加子view
    public RelativeLayout view_container;
    public TextView line;
    public LinearLayout linear_;

    public LeoTitleBar(@NonNull Context context) {
        this(context, null);
    }

    public LeoTitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeoTitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_m_toolbar, this);
        leoBar = findViewById(R.id.leoBar);
        txt_title = findViewById(R.id.txt_title);
        bar_left_btn = findViewById(R.id.bar_left_btn);
        bar_right_text = findViewById(R.id.bar_right_text);
        bar_right_btn = findViewById(R.id.bar_right_btn);
        view_container = findViewById(R.id.view_container);
        linear_ = findViewById(R.id.linear_);
        line = findViewById(R.id.line);
        init(attrs);
        LogUtils.i("运行哪边先的呢", "11111");

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtils.i("这个是多少呢", getChildCount() + "");
        if (getChildCount() > 1) {
            View view = getChildAt(1);
            removeViewInLayout(view);
            if (view != null) {
                view_container.addView(view);
            }
        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!TextUtils.isEmpty(txt_title.getText().toString().trim())) {
            confirmTitle();
        }
    }


    public void confirmTitle() {
        int leftDistance = 0;//左边的距离
        if (bar_left_btn.getVisibility() == View.VISIBLE) {
            leftDistance = (int) getResources().getDimension(R.dimen.dp_35);
        }

        int rightDistance = 0;//右边的距离
        if (bar_right_btn.getVisibility() == View.GONE && bar_right_text.getVisibility() == View.GONE) {

        } else {
            rightDistance = linear_.getMeasuredWidth();
        }

        if (view_container.getChildCount() > 0) {
            rightDistance += view_container.getMeasuredWidth();
        }
        int max = Math.max(leftDistance, rightDistance);
        int screenWith = UIUtil.getWidth(getContext());
        int textWith = (int) txt_title.getPaint().measureText(txt_title.getText().toString().trim());
        if (textWith == 0) {
            return;
        }

        if ((screenWith - 2 * max) > textWith) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) txt_title.getLayoutParams();
            layoutParams.leftMargin = max;
            layoutParams.rightMargin = max;
            txt_title.setLayoutParams(layoutParams);
            LogUtils.i("我们看看现在的情况", " ---- 上");
        } else {
            if (rightDistance == 0) {
                rightDistance = (int) getResources().getDimension(R.dimen.dp_35);
            }

            if (leftDistance == 0) {
                leftDistance = (int) getResources().getDimension(R.dimen.dp_35);
            }
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) txt_title.getLayoutParams();
            layoutParams.leftMargin = leftDistance;
            layoutParams.rightMargin = rightDistance;
            txt_title.setLayoutParams(layoutParams);
            LogUtils.i("我们看看现在的情况", " ---- 下" + leftDistance + "******" + rightDistance);
        }


        LogUtils.i("我们看看现在的情况", "左边的距离 == " + leftDistance);
        LogUtils.i("我们看看现在的情况", "右右边边的距离 == " + rightDistance);
        LogUtils.i("我们看看现在的情况", "文本长度 == " + textWith);
        LogUtils.i("我们看看现在的情况", "屏幕宽度 == " + screenWith);
    }

    public void setTitle(String s) {
        txt_title.setText(s);
        confirmTitle();
    }

    private void init(AttributeSet attrs) {
        //自定义属性，
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LeoTitleBar);

        //背景颜色值
        int backColor = typedArray.getColor(R.styleable.LeoTitleBar_hl_background, Color.WHITE);
        leoBar.setBackgroundColor(backColor);

        //toolbar标题
        int titleColor = typedArray.getColor(R.styleable.LeoTitleBar_hl_textTitleColor, Color.BLACK);
        txt_title.setTextColor(titleColor);
        int titleSize = (int) typedArray.getDimension(R.styleable.LeoTitleBar_hl_textTitleSize, 18);
        txt_title.setTextSize(titleSize);

        String titleStr = typedArray.getString(R.styleable.LeoTitleBar_hl_textTitle);
        if (TextUtils.isEmpty(titleStr)) {
            txt_title.setText("");
        } else {
            txt_title.setText(titleStr);
        }


        //左边图标
        boolean isShowLeftBtn = typedArray.getBoolean(R.styleable.LeoTitleBar_hl_showLeftBtn, true);
        if (isShowLeftBtn) {
            bar_left_btn.setVisibility(View.VISIBLE);
        } else {
            bar_left_btn.setVisibility(View.GONE);
        }

        Drawable leftDrawable = typedArray.getDrawable(R.styleable.LeoTitleBar_hl_leftBtnDrawable);
        if (leftDrawable != null) {
            bar_left_btn.setImageDrawable(leftDrawable);
        }

        /*
         * 右边
         * */

        //右边是否显示文字
        String rightStr = typedArray.getString(R.styleable.LeoTitleBar_hl_showRightText);
        if (TextUtils.isEmpty(rightStr)) {
            bar_right_text.setVisibility(View.GONE);
        } else {
            bar_right_text.setText(rightStr);
        }

        //右边是否先是图标
        Drawable rightDrawable = typedArray.getDrawable(R.styleable.LeoTitleBar_hl_rightBtnDrawable);
        if (rightDrawable == null) {
            bar_right_btn.setVisibility(View.GONE);
        } else {
            bar_right_btn.setVisibility(View.VISIBLE);
            bar_right_btn.setImageDrawable(rightDrawable);
        }

        //分割线颜色，如果bar背景颜色和window背景颜色一致，需要分割线
        int divide_color = typedArray.getColor(R.styleable.LeoTitleBar_hl_divideColor, Color.TRANSPARENT);
        line.setBackgroundColor(divide_color);

    }

}
