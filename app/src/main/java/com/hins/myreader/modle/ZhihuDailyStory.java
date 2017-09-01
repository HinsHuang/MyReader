package com.hins.myreader.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hins on 2017/8/28.
 */

public class ZhihuDailyStory {

    public String date;
    public List<Discussion> stories;

    public class Discussion {

        public List<String> images;
        public int type;
        public int id;

        @SerializedName("ga_prefix")
        public String prefix;
        public String title;

    }

}
