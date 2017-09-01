package com.hins.myreader.api;

/**
 * Created by Hins on 2017/8/26.
 */

public class Api {

    /**
        每日一文api uri
        https://interface.meiriyiwen.com/article/today?dev=1
        url：https://interface.meiriyiwen.com/article/day?dev=1&date= + 日期
        示例: https://interface.meiriyiwen.com/article/day?dev=1&date=20170826

        随机一文api uri
        https://interface.meiriyiwen.com/article/random/
     */
    public static final String EVERYDAY_ARTICLE_BASE_URI = "https://interface.meiriyiwen.com/article/";

    /**
        过往消息
        past posts
        若要查询的11月18日的消息，before后面的数字应该为20161119,增加1天
        if you want to select the posts of November 11th, the number after 'before' should be 20161119, plus 1 day.
        知乎日报的生日为2013 年 5 月 19 日，如果before后面的数字小于20130520，那么只能获取到空消息
        the birthday of ZhiHuDaily is May 19th, 2013. So if the number is lower than 20130520, you will get a null value of post

        http://news.at.zhihu.com/api/4/news/before/;

         消息内容获取与离线下载
         content of post and download offline
         在最新消息中获取到的id，拼接到这个NEWS之后，可以获得对应的JSON格式的内容
         add the id that you got from latest post to ZHIHU_NEWS and you will get the content as json format
     http://news-at.zhihu.com/api/4/news/(news_id)
     */

    public static final String ZHIHU_NEWS_BASE_URI = "http://news-at.zhihu.com/api/4/";

}
