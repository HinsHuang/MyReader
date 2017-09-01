package com.hins.myreader.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hins on 2017/8/28.
 */

public class ZhihuDetailStory {

    public String body;

    @SerializedName("image_source")
    public String imageSource;
    public String title;
    public String image;

    @SerializedName("share_url")
    public String shareUrl;

    public List<String> js;

    @SerializedName("ga_prefix")
    public String prefix;

    public List<String> images;
    public int type;
    public int id;

    public List<String> css;

}
