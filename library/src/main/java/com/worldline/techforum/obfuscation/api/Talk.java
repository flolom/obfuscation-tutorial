package com.worldline.techforum.obfuscation.api;

import java.util.Collections;
import java.util.List;

/**
 * Created by Francois Lolom on 05/05/2016.
 */
public class Talk {

    private String id;

    private String title;

    private String startAt;
    private String endAt;

    List<String> authors;

    public Talk(String id, String title, String startAt, String endAt, List<String> authors) {
        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.authors = Collections.unmodifiableList(authors);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStartAt() {
        return startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public List<String> getAuthors() {
        return authors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Talk talk = (Talk) o;

        return id.equals(talk.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
