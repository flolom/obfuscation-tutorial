package com.worldline.techforum.obfuscation.api;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import java.io.IOException;
import java.util.List;

/**
 * Created by Francois Lolom on 05/05/2016.
 */
public interface TechForumApi {

    /**
     *
     * Retrieve the list of all the talks, from the local cache or from the internet.
     * <p/>
     * If the list if loaded from the internet, it will refresh the local cache with the new talks.
     *
     * @param fromCache indicates if the talks should be loaded from the local cache, or from the internet
     * @return a non null list of talks
     * @throws IOException if a network error occur while retrieving the talk list from the internet
     */
    @WorkerThread
    @NonNull
    List<Talk> retrieveAllTalks(boolean fromCache) throws IOException;

    /**
     * Retrieve the list of all the talks marked as favorite with {@link #addTalkToFavorites(Talk)}
     *
     * @return a non null list of talks
     */
    @NonNull
    List<Talk> retrieveFavoriteTalks();

    /**
     * Add a talk to the favorite list
     *
     * @param talk the talk marked as favorite
     * @see #retrieveFavoriteTalks()
     */
    void addTalkToFavorites(Talk talk);

    /**
     * Remove a talk from the favorite list
     * @param talk the talk to remove from the favorite list
     * @see #retrieveFavoriteTalks()
     */
    void removeTalkFromFavorites(Talk talk);

    /**
     * Indicates if a given talk was marked as favorite
     * @param talk the talk
     * @return true if the talk is in the user favorite list, false otherwise
     * @see #retrieveFavoriteTalks()
     * @see #addTalkToFavorites(Talk)
     */
    boolean isTalkFavorite(Talk talk);

}
