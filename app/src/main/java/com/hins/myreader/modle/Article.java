package com.hins.myreader.modle;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hins on 2017/8/26.
 */

public class Article {

    public Data data;

    public class Data {

    public String author;
    public String title;
    public String digest;
    public String content;

    @SerializedName("date")
    public DateChain dateChain;

    public class DateChain {
        public String curr;
        public String prev;
        public String next;
    }
    }

}
