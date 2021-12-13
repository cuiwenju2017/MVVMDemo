package com.example.mvvmdemo.api;

import com.example.mvvmdemo.bean.BannersBean;
import com.example.mvvmdemo.bean.ResponModel;
import com.example.mvvmdemo.bean.UserArticle;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by leo
 * on 2019/8/14.
 * Retrofit 接口请求配置都在这
 */
public interface RetrofitApiService {

    //广场列表数据
    @GET("user_article/list/{page}/json")
    Observable<ResponModel<UserArticle>> getUserArticleList(@Path("page") int page);

    //干货banners图
    @GET("banner/json")
    Observable<ResponModel<List<BannersBean>>> getBanner();
}
