package com.worldline.techforum.obfuscation.api.internal;

import com.worldline.techforum.obfuscation.api.Talk;
import com.worldline.techforum.obfuscation.api.internal.model.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Francois Lolom on 05/05/2016.
 */
final class Mapper {

    private Mapper() {

    }

    static Talk mapSessionToTalk(Session session) {

        if (session == null) {
            return null;
        }

        String startAt = null;
        String endAt = null;
        List<String> authorsTalkList = new ArrayList<>();

        // map authors
        String authors = session.getAuthor();

        if (authors != null) {
            String[] authorsSpitted = authors.split(",");
            authorsTalkList.addAll(Arrays.asList(authorsSpitted));
        }

        // map talk start and endDate
        if (session.getWhen() != null) {
            startAt = session.getWhen().getStart();
            endAt   = session.getWhen().getEnd();
        }

        return new Talk(session.getId(), session.getTitle(), startAt, endAt, authorsTalkList);
    }

}
