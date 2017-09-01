package com.hins.myreader.util;

import android.text.TextUtils;

import com.hins.myreader.api.Api;
import com.hins.myreader.modle.Article;
import com.hins.myreader.modle.ArticleService;
import com.hins.myreader.modle.ZhihuDailyStory;
import com.hins.myreader.modle.ZhihuDetailStory;
import com.hins.myreader.modle.ZhihuStoryService;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hins on 2017/8/26.
 */

public class RetrofitUtil {

    public static Call<Article> initDataForEveryDayArticle(String date) {

        Call<Article> call;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.EVERYDAY_ARTICLE_BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ArticleService service = retrofit.create(ArticleService.class);

        if (!TextUtils.isEmpty(date)) {
            call = service.getSpecifiedDayArticle(1, date);
        } else {
            call = service.getRandomArticle();
        }

        return call;
    }

    public static Call<ZhihuDailyStory> initDataForZhihuDailyStory(String date) {
        Call<ZhihuDailyStory> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ZHIHU_NEWS_BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ZhihuStoryService service = retrofit.create(ZhihuStoryService.class);
        call = service.getZhihuDailyStory(date);
        return call;
    }

    public static Call<ZhihuDetailStory> initDataForZhihuDetailStory(int id) {
        Call<ZhihuDetailStory> call;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.ZHIHU_NEWS_BASE_URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ZhihuStoryService service = retrofit.create(ZhihuStoryService.class);
        call = service.getZhihuDetailStory(id);
        return call;
    }



//    public static Retrofit initRetrofit(String baseUrl) {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        return retrofit;
//    }


}
