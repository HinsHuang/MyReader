package com.hins.myreader.modle;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hins on 2017/8/26.
 */

public interface ArticleService {

    /*
       路径用@Path 参数用@Query GET 表示http get方法
     */
    @GET("day")
    Call<Article> getSpecifiedDayArticle(@Query("dev") int dev, @Query("date") String date);

    @GET("random")
    Call<Article> getRandomArticle();
}
