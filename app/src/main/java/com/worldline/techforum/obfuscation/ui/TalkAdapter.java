package com.worldline.techforum.obfuscation.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldline.techforum.obfuscation.api.Talk;
import com.worldline.techforum.obfuscation.api.TechForumApi;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Francois Lolom on 07/05/2016.
 */
public class TalkAdapter extends RecyclerView.Adapter<TalkAdapter.TalkViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private final TechForumApi techForumApi;

    private List<Talk> talkList;
    private boolean displayFavorite;
    private TalkAdapterListener talkAdapterListener;

    public TalkAdapter(Context context, TechForumApi techForumApi) {
        this.context = context;
        this.techForumApi = techForumApi;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public TalkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_talk, parent, false);

        return new TalkViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return talkList != null ? talkList.size() : 0;
    }

    @Override
    public void onBindViewHolder(TalkViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Talk talk = talkList.get(position);

        holder.title.setText(talk.getTitle());
        holder.schedule.setText(context.getString(R.string.talk_schedule, talk.getStartAt(), talk.getEndAt()));

        if (talk.getAuthors() != null) {
            holder.authors.setText(TextUtils.join(", ", talk.getAuthors()));
        } else {
            holder.authors.setText(null);
        }

        if (displayFavorite) {

            if (techForumApi.isTalkFavorite(talk)) {
                holder.favorite.setImageResource(R.drawable.ic_talk_favorite);
            } else {
                holder.favorite.setImageResource(R.drawable.ic_talk_not_favorite);
            }

            holder.favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (talkAdapterListener != null) {
                        talkAdapterListener.onTalkFavoriteClicked(position, talk);
                    }
                }
            });
        }
    }

    public void setDisplayFavorite(boolean shouldDisplayIfTalkFavorite) {
        this.displayFavorite = shouldDisplayIfTalkFavorite;

        // rebind views
        notifyDataSetChanged();
    }

    public void setTalkAdapterListener(TalkAdapterListener listener) {
        this.talkAdapterListener = listener;
    }

    public void setTalkList(List<Talk> talks) {
        this.talkList = talks;

        notifyDataSetChanged();
    }

    static class TalkViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_talk_title) TextView title;
        @Bind(R.id.item_talk_schedule) TextView schedule;
        @Bind(R.id.item_talk_authors) TextView authors;
        @Bind(R.id.item_talk_favorite) ImageView favorite;

        public TalkViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface TalkAdapterListener {

        void onTalkFavoriteClicked(int position, Talk talk);

    }


    private void applyAndAnimateRemovals(List<Talk> newModels) {
        for (int i = talkList.size() - 1; i >= 0; i--) {
            final Talk model = talkList.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<Talk> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Talk model = newModels.get(i);
            if (!talkList.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<Talk> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Talk model = newModels.get(toPosition);
            final int fromPosition = talkList.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public void animateTo(@Nullable List<Talk> models) {

        List<Talk> newModel  = models != null ? models : new ArrayList<Talk>();

        applyAndAnimateRemovals(newModel);
        applyAndAnimateAdditions(newModel);
        applyAndAnimateMovedItems(newModel);
    }

    public Talk removeItem(int position) {
        final Talk model = talkList.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Talk model) {
        talkList.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Talk model = talkList.remove(fromPosition);
        talkList.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }

    public List<Talk> getTalkList() {
        return talkList;
    }
}
