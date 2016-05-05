package com.worldline.techforum.obfuscation.api;

import android.content.Context;

import com.worldline.techforum.obfuscation.api.internal.TechForumApiImpl;

import okhttp3.HttpUrl;

/**
 * Created by Francois Lolom on 05/05/2016.
 */
public class TechForumManager {

    private static final HttpUrl BASE_URL = HttpUrl.parse("https://raw.githubusercontent.com/got5/TechForum2016/master/");

    private Context context;

    public static TechForumManager init(Context context) {
        return new TechForumManager(context);
    }

    private TechForumManager(Context context) {
        this.context = context;
    }

    public TechForumApi createTechForumApi() {
        return new TechForumApiImpl(BASE_URL, context);
    }

}
