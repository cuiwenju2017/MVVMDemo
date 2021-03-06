package com.example.mvvmdemo.ui.webview;

import android.view.View;

import com.example.mvvmdemo.R;
import com.example.mvvmdemo.base.BaseActivity;
import com.example.mvvmdemo.base.NormalViewModel;
import com.example.mvvmdemo.databinding.ActivityWebBinding;

/**
 * Created by leo
 * on 2019/11/12.
 */
public class WebActivity extends BaseActivity<NormalViewModel, ActivityWebBinding> {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_web;
    }

    @Override
    protected void processLogic() {
        String url = getIntent().getStringExtra("url");
        binding.webViewX5.setTitleBar(binding.leoTitleBar);
        binding.webViewX5.loadUrl(url);
        binding.leoTitleBar.bar_left_btn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.webViewX5.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.webViewX5.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webViewX5.destroy();
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bar_left_btn:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.webViewX5.canGoBack()) {
            binding.webViewX5.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
