package com.hins.myreader.modle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hins on 2017/8/28.
 */

public interface ZhihuStoryService {

    @GET("news/before/{date}")
    Call<ZhihuDailyStory> getZhihuDailyStory(@Path("date") String date);

    @GET("news/{id}")
    Call<ZhihuDetailStory> getZhihuDetailStory(@Path("id") int id);
}
