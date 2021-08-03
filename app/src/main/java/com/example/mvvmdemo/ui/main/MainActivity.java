package com.example.mvvmdemo.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.baselibrary.base.BaseRVAdapter;
import com.example.baselibrary.base.BaseRVHolder;
import com.example.baselibrary.utils.ActivitysBuilder;
import com.example.baselibrary.utils.ButtonClickUtils;
import com.example.baselibrary.utils.ToastUtils;
import com.example.mvvmdemo.R;
import com.example.mvvmdemo.base.BaseActivity;
import com.example.mvvmdemo.api.ParamsBuilder;
import com.example.mvvmdemo.bean.BannersBean;
import com.example.mvvmdemo.bean.GetDataType;
import com.example.mvvmdemo.bean.UserArticle;
import com.example.mvvmdemo.databinding.ActivityMainBinding;
import com.example.mvvmdemo.ui.adapter.ImageTitleNumAdapter;
import com.example.mvvmdemo.ui.webview.WebActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.lihang.nbadapter.BaseAdapter;
import com.youth.banner.Banner;

import java.util.List;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> implements BaseAdapter.OnItemClickListener<UserArticle.DatasBean>, View.OnClickListener {

    private int page = 0;
    private BaseRVAdapter<UserArticle.DatasBean> adapter;
    private Banner banner;
    //沉浸式状态栏
    protected ImmersionBar mImmersionBar;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void processLogic() {
        initStatusBar();
        binding.setOnclickListener(this);

        //干货banners图
        getBanner(ParamsBuilder.build().isShowDialog(false));
        //广场列表数据
        getUserArticleList(page, null, GetDataType.GETDATA);

        adapter = new BaseRVAdapter<UserArticle.DatasBean>(R.layout.item_user_article) {
            @Override
            public void onBindVH(BaseRVHolder holder, UserArticle.DatasBean data, int position) {
                holder.setText(R.id.tv_title, data.getTitle());
                holder.setText(R.id.tv_shareUser, data.getShareUser());
                holder.setText(R.id.tv_niceShareDate, data.getNiceShareDate());

                holder.getView(R.id.ll).setOnClickListener(v -> {
                    ActivitysBuilder.build(MainActivity.this, WebActivity.class)
                            .putExtra("url", data.getLink())
                            .startActivity();
                });
            }
        };

        //banner轮播图
        View header = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_header, null, false);
        banner = header.findViewById(R.id.banner);
        adapter.addHeaderView(header);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 1));//列数设置
    }

    private void initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
                .navigationBarDarkIcon(true) //导航栏图标是深色，不写默认为亮色
                .keyboardEnable(true)
                .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
                .fitsSystemWindows(true);
        mImmersionBar.init();
    }

    private void getBanner(ParamsBuilder paramsBuilder) {
        mViewModel.getBanner(paramsBuilder).observe(this, resource -> resource.handler(new OnCallback<List<BannersBean>>() {
            @Override
            public void onSuccess(List<BannersBean> data) {
                banner.setAdapter(new ImageTitleNumAdapter(MainActivity.this, data));
                banner.removeIndicator();
            }
        }));
    }

    @Override
    protected void setListener() {
        binding.smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            getUserArticleList(page, ParamsBuilder.build().isShowDialog(false), GetDataType.REFRESH);
            //干货banners图
            getBanner(ParamsBuilder.build().isShowDialog(false));
        });

        binding.smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            getUserArticleList(page, ParamsBuilder.build().isShowDialog(false), GetDataType.LOADMORE);
        });
    }

    @Override
    public void onClick(View v) {
        if (ButtonClickUtils.isFastClick()) {//防止快速点击
            return;
        }
        switch (v.getId()) {
        }
    }

    private void getUserArticleList(int page, ParamsBuilder paramsBuilder, Integer type) {
        mViewModel.getUserArticleList(page, paramsBuilder).observe(this, resource -> resource.handler(new OnCallback<UserArticle>() {
            @Override
            public void onSuccess(UserArticle data) {
                if (data != null) {
                    switch (type) {
                        case GetDataType.GETDATA://获取数据成功
                            adapter.setNewData(data.getDatas());
                            break;
                        case GetDataType.REFRESH://刷新成功
                            adapter.setNewData(data.getDatas());
                            binding.smartRefreshLayout.finishRefresh();
                            binding.smartRefreshLayout.setNoMoreData(false);
                            break;
                        case GetDataType.LOADMORE://加载成功
                            if (data.getDatas() != null && !data.getDatas().isEmpty()) {
                                adapter.addData(data.getDatas());
                                if (data.getDatas().size() < page) {
                                    binding.smartRefreshLayout.finishLoadMoreWithNoMoreData();
                                } else {
                                    binding.smartRefreshLayout.finishLoadMore();
                                }
                            } else {
                                binding.smartRefreshLayout.finishLoadMoreWithNoMoreData();
                            }
                            break;
                    }
                } else {
                    //无数据视图显示
                    View empty = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_no_data, null, false);
                    adapter.setEmptyView(empty);
                }
            }
        }));
    }

    @Override
    public void onItemClick(UserArticle.DatasBean item, int position) {
        ActivitysBuilder.build(this, WebActivity.class)
                .putExtra("url", item.getLink())
                .startActivity();
    }

    /**
     * 再按一次退出程序
     */
    private long currentBackPressedTime = 0;
    private static int BACK_PRESSED_INTERVAL = 2000;

    @SuppressLint("RestrictedApi")
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
                currentBackPressedTime = System.currentTimeMillis();
                ToastUtils.showToast("再按一次退出程序");
                return true;
            } else {//退出程序
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return false;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}