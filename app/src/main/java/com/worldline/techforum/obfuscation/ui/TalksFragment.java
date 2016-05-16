package com.worldline.techforum.obfuscation.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.worldline.techforum.obfuscation.api.Talk;
import com.worldline.techforum.obfuscation.api.TechForumApi;
import com.worldline.techforum.obfuscation.api.TechForumManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by Francois Lolom on 07/05/2016.
 */
public class TalksFragment extends BottomBarFragment implements SwipeRefreshLayout.OnRefreshListener, TalkAdapter.TalkAdapterListener {

    private static final String TAG = "TalksFragment";

    private static final String EXTRA_IS_FAVORITES = "isFavorites";

    @Bind(R.id.talks_list) RecyclerView talkList;
    @Bind(R.id.talk_refresh_indicator) SwipeRefreshLayout swipeRefreshLayout;

    private TalkAdapter talkAdapter;
    private TechForumApi techForumApi;

    private List<Talk> allTalks;
    private List<Talk> favoriteTalks;

    public static TalksFragment newInstance(boolean isFavoritesView) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_IS_FAVORITES, isFavoritesView);
        TalksFragment fragment = new TalksFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TechForumManager manager = TechForumManager.init(getContext());
        techForumApi = manager.createTechForumApi();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_talks, container, false);

        ButterKnife.bind(this, view);

        talkList.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout.setOnRefreshListener(this);

        talkAdapter = new TalkAdapter(getContext(), techForumApi);
        talkAdapter.setDisplayFavorite(true);
        talkAdapter.setTalkAdapterListener(this);

        talkList.setAdapter(talkAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!getArguments().getBoolean(EXTRA_IS_FAVORITES)) {
            loadLocalTalks(true);
            loadNetworkTalks(true);
        } else {
            loadFavoriteTalks(true);
        }
    }

    private void loadLocalTalks(boolean updateAdapter) {

        // first, load data from the cache, in the main thread
        try {
            Timber.d("Loading all talks from cache");

            List<Talk> talksCached = techForumApi.retrieveAllTalks(true);

            allTalks = talksCached;

            if (updateAdapter) {
                talkAdapter.setTalkList(talksCached);
            }

            Timber.d("List loaded with success, with %d elements", talksCached.size());
        } catch (IOException e) {
            Timber.e(e, "Error while loading talks from cache");
        }


    }

    private void loadFavoriteTalks(boolean updateAdapter) {
        Timber.d("Loading favorites talks");

        favoriteTalks = techForumApi.retrieveFavoriteTalks();

        if (updateAdapter) {
            talkAdapter.setTalkList(favoriteTalks);
        }

        Timber.d("List loaded with success, with %d elements", favoriteTalks.size());

        swipeRefreshLayout.setRefreshing(false);
    }

    private void loadNetworkTalks(final boolean updateAdapter) {

        // then, try to load data from the internet, if possible.

        // see https://stackoverflow.com/questions/26858692/swiperefreshlayout-setrefreshing-not-showing-indicator-initially
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

                new AsyncTask<Void, Void, List<Talk>>() {

                    @Override
                    protected List<Talk> doInBackground(Void... params) {

                        Timber.d("Loading all talks from network");

                        try {
                            List<Talk> talks = techForumApi.retrieveAllTalks(false);

                            Timber.d("List loaded with success, with %d elements", talks.size());

                            return talks;

                        } catch (IOException e) {
                            Timber.e(e, "Error while loading talks from network");
                            return null;
                        }

                    }

                    @Override
                    protected void onPostExecute(List<Talk> talks) {
                        allTalks = new ArrayList<>(talks);

                        if (getView() == null) {
                            return;
                        }

                        swipeRefreshLayout.setRefreshing(false);
                        if (updateAdapter) {
                            talkAdapter.setTalkList(talks);
                        }
                    }

                }.execute();
            }
        });
    }

    @Override
    public void scrollToTop() {

        talkList.post(new Runnable() {
            @Override
            public void run() {
                talkList.scrollToPosition(0);
            }
        });
    }

    @Override
    public void onRefresh() {
        if (!getArguments().getBoolean(EXTRA_IS_FAVORITES)) {
            loadNetworkTalks(true);
        } else {
            loadFavoriteTalks(true);
        }
    }

    @Override
    public void onTalkFavoriteClicked(int position, Talk talk) {

        if (techForumApi.isTalkFavorite(talk)) {
            techForumApi.removeTalkFromFavorites(talk);
        } else {
            techForumApi.addTalkToFavorites(talk);
        }

        talkAdapter.notifyItemChanged(position);
    }

    public void switchToFavoritesTalks() {

        if (getView() == null) {
            return;
        }

        // always reload favorites on switch
        loadFavoriteTalks(false);

        getArguments().putBoolean(EXTRA_IS_FAVORITES, true);
        talkAdapter.animateTo(favoriteTalks);
        talkList.scrollToPosition(0);
    }

    public void switchToAllTalks() {

        if (getView() == null) {
            return;
        }

        if (allTalks == null) {
            loadLocalTalks(false);
        }

        getArguments().putBoolean(EXTRA_IS_FAVORITES, false);
        talkAdapter.animateTo(allTalks);
        talkList.scrollToPosition(0);
    }


}
