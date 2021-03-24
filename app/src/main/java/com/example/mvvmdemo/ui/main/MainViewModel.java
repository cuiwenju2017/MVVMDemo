package com.example.mvvmdemo.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mvvmdemo.base.BaseViewModel;
import com.example.mvvmdemo.api.ParamsBuilder;
import com.example.mvvmdemo.api.RepositoryImpl;
import com.example.mvvmdemo.bean.BannersBean;
import com.example.mvvmdemo.bean.Resource;
import com.example.mvvmdemo.bean.UserArticle;

import java.util.List;

public class MainViewModel extends BaseViewModel<RepositoryImpl> {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    //广场列表数据
    public LiveData<Resource<UserArticle>> getUserArticleList(int page, ParamsBuilder paramsBuilder) {
        return getRepository().getUserArticleList(page, paramsBuilder);
    }

    //干货banners图
    public LiveData<Resource<List<BannersBean>>> getBanner(ParamsBuilder paramsBuilder) {
        return getRepository().getBanner(paramsBuilder);
    }
}
