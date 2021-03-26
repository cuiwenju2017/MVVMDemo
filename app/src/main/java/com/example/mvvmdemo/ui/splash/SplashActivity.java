package com.example.mvvmdemo.ui.splash;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.baselibrary.utils.ActivitysBuilder;
import com.example.mvvmdemo.R;
import com.example.mvvmdemo.base.BaseActivity;
import com.example.mvvmdemo.base.NormalViewModel;
import com.example.mvvmdemo.databinding.ActivitySplashBinding;
import com.example.mvvmdemo.ui.main.MainActivity;
import com.gyf.immersionbar.ImmersionBar;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity<NormalViewModel, ActivitySplashBinding> {

    //沉浸式状态栏
    protected ImmersionBar mImmersionBar;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void processLogic() {
        initStatusBar();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_welcome);
        binding.iv.startAnimation(animation);

        Observable.timer(2000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(aLong -> {
            ActivitysBuilder.build(this, MainActivity.class)
                    .withAnimal(0, R.anim.anim_alpha_hide)
                    .startActivity();
            finish();
        });
    }

    private void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
//                .statusBarColor(R.color.yellow)
                .keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                .fitsSystemWindows(false);
        mImmersionBar.init();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }
}