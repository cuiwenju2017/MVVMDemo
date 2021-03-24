package com.example.mvvmdemo.api;

import com.example.mvvmdemo.base.BaseModel;
import com.example.mvvmdemo.bean.BannersBean;
import com.example.mvvmdemo.bean.Resource;
import com.example.mvvmdemo.bean.UserArticle;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

/**
 * Created by leo
 * on 2019/10/15.
 * 这是所有的网络请求所在的位置
 */
public class RepositoryImpl extends BaseModel {

    public MutableLiveData<Resource<UserArticle>> getUserArticleList(int page, ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<UserArticle>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getUserArticleList(page), liveData, paramsBuilder);
    }

    public MutableLiveData<Resource<List<BannersBean>>> getBanner(ParamsBuilder paramsBuilder) {
        MutableLiveData<Resource<List<BannersBean>>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().getBanner(), liveData,paramsBuilder);
    }
}
