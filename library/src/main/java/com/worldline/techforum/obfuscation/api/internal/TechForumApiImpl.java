package com.worldline.techforum.obfuscation.api.internal;

import android.content.Context;
import android.support.annotation.NonNull;

import com.worldline.techforum.obfuscation.api.Talk;
import com.worldline.techforum.obfuscation.api.TechForumApi;
import com.worldline.techforum.obfuscation.api.internal.model.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Francois Lolom on 05/05/2016.
 */
public class TechForumApiImpl implements TechForumApi {

    private TalkRemoteRepository remoteRepository;
    private TalkLocalRepository localRepository;
    private Context context;

    public TechForumApiImpl(HttpUrl baseUrl, Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        remoteRepository = retrofit.create(TalkRemoteRepository.class);
        localRepository = new TalkLocalRepository(context);
    }

    @NonNull
    @Override
    public List<Talk> retrieveAllTalks(boolean fromCache) throws IOException {

        final List<Talk> response = new ArrayList<>();

        if (fromCache) {
            List<Talk> cachedResponses = localRepository.getTalkList();

            // cache miss
            if (cachedResponses == null) {

                return response; // empty list
            } else {

                response.addAll(cachedResponses);
            }

        } else {
            Call<List<Session>> talkListCall = remoteRepository.getAllTalks();

            Response<List<Session>> talkListResponse = talkListCall.execute();

            if (!talkListResponse.isSuccessful()) {
                throw new IOException("error while loading session list");
            }

            List<Session> talkList = talkListResponse.body();

            if (talkList == null) {
                return response; // empty list
            }

            for (Session session : talkList) {

                response.add(Mapper.mapSessionToTalk(session));
            }

            localRepository.setTalkList(response);
        }

        return response;
    }

    @NonNull
    @Override
    public List<Talk> retrieveFavoriteTalks() {

        final List<Talk> favorites = new ArrayList<>();

        List<Talk> cachedTalks = localRepository.getTalkList();

        if (cachedTalks == null) {
            return favorites; // empty list
        }

        // search and add the talks with an id stored in the favorite talk list
        for (Talk cachedTalk : cachedTalks) {

            if (localRepository.containsFavoriteTalk(cachedTalk.getId())) {
                favorites.add(cachedTalk);
            }
        }

        return favorites;
    }

    @Override
    public void addTalkToFavorites(Talk talk) {
        localRepository.addFavoriteTalkId(talk.getId());

    }

    @Override
    public void removeTalkFromFavorites(Talk talk) {
        localRepository.removeFavoriteTalkId(talk.getId());
    }

    @Override
    public boolean isTalkFavorite(Talk talk) {
        return localRepository.containsFavoriteTalk(talk.getId());
    }
}
