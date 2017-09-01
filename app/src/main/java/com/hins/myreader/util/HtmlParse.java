package com.hins.myreader.util;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import static android.text.Html.fromHtml;

/**
 * Created by Hins on 2017/8/28.
 */

public class HtmlParse {

    @SuppressWarnings("deprecation")
    public static Spanned parseHtml(String html) {
        Spanned spanned;
        if (Build.VERSION.SDK_INT > 24) {
            spanned = fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            spanned = Html.fromHtml(html);
        }

        return spanned;
    }
}
