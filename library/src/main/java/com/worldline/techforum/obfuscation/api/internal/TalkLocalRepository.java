package com.worldline.techforum.obfuscation.api.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.worldline.techforum.obfuscation.api.Talk;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Francois Lolom on 05/05/2016.
 */
class TalkLocalRepository {

    private static final String PREFERENCES_FILE = "__wl_techforum";

    private static final String KEY_TALKS     = "talks";
    private static final String KEY_FAVORITES = "favorites_ids";

    private static final Type TYPE_LIST_TALK = new TypeToken<List<Talk>>() {}.getType();

    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public TalkLocalRepository(Context context, Gson gson) {
        this.sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        this.gson = gson;
    }

    public void setTalkList(List<Talk> talks) {

        String serializedList = gson.toJson(talks);

        sharedPreferences.edit().putString(KEY_TALKS, serializedList).apply();
    }

    @Nullable
    public List<Talk> getTalkList() {

        String serializedList = sharedPreferences.getString(KEY_TALKS, null);
        if (serializedList == null) {
            return null;
        }

        return gson.fromJson(serializedList, TYPE_LIST_TALK);
    }

    public synchronized boolean addFavoriteTalkId(String id) {
        Set<String> favoriteIds = getFavoriteTalkSet();

        boolean inserted = favoriteIds.add(id);

        if (inserted) {
            sharedPreferences.edit().putStringSet(KEY_FAVORITES, favoriteIds).apply();
        }

        return inserted;
    }

    public synchronized boolean removeFavoriteTalkId(String talkId) {
        Set<String> favoriteIds = getFavoriteTalkSet();

        boolean removed = favoriteIds.remove(talkId);

        if (removed) {
            sharedPreferences.edit().putStringSet(KEY_FAVORITES, favoriteIds).apply();
        }

        return removed;
    }

    public boolean containsFavoriteTalk(String talkId) {
        Set<String> favoriteIds = getFavoriteTalkSet();

        return favoriteIds.contains(talkId);
    }

    @NonNull
    private Set<String> getFavoriteTalkSet() {
        Set<String> favoriteIds = sharedPreferences.getStringSet(KEY_FAVORITES, null);

        if (favoriteIds == null) {
            favoriteIds = new HashSet<>();
        }
        return favoriteIds;
    }

}
