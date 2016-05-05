package com.worldline.techforum.obfuscation.api.internal;

import com.worldline.techforum.obfuscation.api.internal.model.Session;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Francois Lolom on 05/05/2016.
 */
interface TalkRemoteRepository {

    @GET("www/data/conferences.json")
    Call<List<Session>> getAllTalks();

}
