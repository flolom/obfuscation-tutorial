package com.worldline.techforum.obfuscation.api.internal;

import com.worldline.techforum.obfuscation.api.BaseUnitTest;
import com.worldline.techforum.obfuscation.api.Talk;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Francois Lolom on 05/05/2016.
 */
public class TechForumApiImplTest extends BaseUnitTest {

    private static final String SAMPLE_SERVER_DATA = "[{\"description\":\"sample data\",\"title\":\"sample title\",\"author\":\"author1,author2\",\"when\":{\"start\":\"11:10\",\"end\":\"11:55\"},\"bu\":\"fpl\",\"theme\":[\"obfuscation\"],\"_id\":\"123456\",\"where\":1,\"day\":\"1\"}]";

    private TechForumApiImpl techForumImpl;
    private MockWebServer mockWebServer;

    @Before
    public void before() throws Exception {

        super.before();

        mockWebServer = new MockWebServer();
        mockWebServer.start();

        techForumImpl = new TechForumApiImpl(mockWebServer.url("/"), RuntimeEnvironment.application);
    }

    @After
    public void after() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void should_retrieve_talks_from_network() throws Exception {

        mockWebServer.enqueue(new MockResponse().setBody(SAMPLE_SERVER_DATA));

        List<Talk> talks = techForumImpl.retrieveAllTalks(false);

        assertNotNull(talks);
        assertEquals(1, talks.size());

        Talk talk = talks.get(0);

        assertEquals("123456", talk.getId());
        assertEquals("sample title", talk.getTitle());
        assertEquals("11:10", talk.getStartAt());
        assertEquals("11:55", talk.getEndAt());

        assertEquals(2, talk.getAuthors().size());
        assertEquals("author1", talk.getAuthors().get(0));
        assertEquals("author2", talk.getAuthors().get(1));
    }

    @Test
    public void should_access_cached_talks_after_successful_network_request() throws Exception {

        mockWebServer.enqueue(new MockResponse().setBody(SAMPLE_SERVER_DATA));

        List<Talk> talksNotYetCached = techForumImpl.retrieveAllTalks(true);

        assertNotNull(talksNotYetCached);
        assertEquals(0, talksNotYetCached.size());

        List<Talk> talksFromNetwork = techForumImpl.retrieveAllTalks(false);

        assertNotNull(talksFromNetwork);
        assertEquals(1, talksFromNetwork.size());

        List<Talk> talksCached = techForumImpl.retrieveAllTalks(true);

        assertNotNull(talksCached);

        assertArrayEquals(talksFromNetwork.toArray(), talksCached.toArray());
    }

    @Test
    public void should_retrieve_list_of_favorites_when_favorite_added() throws Exception {

        mockWebServer.enqueue(new MockResponse().setBody(SAMPLE_SERVER_DATA));

        List<Talk> emptyFavoriteTalks = techForumImpl.retrieveFavoriteTalks();

        assertNotNull(emptyFavoriteTalks);
        assertEquals(0, emptyFavoriteTalks.size());

        List<Talk> talks = techForumImpl.retrieveAllTalks(false);
        Talk talk = talks.get(0);

        techForumImpl.addTalkToFavorites(talk);

        List<Talk> favoriteTalks = techForumImpl.retrieveFavoriteTalks();

        assertNotNull(favoriteTalks);
        assertEquals(1, favoriteTalks.size());

        assertEquals(talk, favoriteTalks.get(0));
    }

    @Test
    public void should_retrieve_list_of_favorites_when_favorite_removed() throws Exception {

        mockWebServer.enqueue(new MockResponse().setBody(SAMPLE_SERVER_DATA));

        List<Talk> talks = techForumImpl.retrieveAllTalks(false);
        Talk talk = talks.get(0);

        techForumImpl.addTalkToFavorites(talk);

        List<Talk> favoriteTalksAfterAdd = techForumImpl.retrieveFavoriteTalks();

        assertNotNull(favoriteTalksAfterAdd);
        assertEquals(1, favoriteTalksAfterAdd.size());

        techForumImpl.removeTalkFromFavorites(talk);

        List<Talk> favoriteTalksAfterRemove = techForumImpl.retrieveFavoriteTalks();

        assertNotNull(favoriteTalksAfterRemove);
        assertEquals(0, favoriteTalksAfterRemove.size());
    }

    @Test
    public void should_contains_favorite_talk_when_added() throws Exception {

        mockWebServer.enqueue(new MockResponse().setBody(SAMPLE_SERVER_DATA));

        List<Talk> talks = techForumImpl.retrieveAllTalks(false);
        Talk talk = talks.get(0);

        boolean isFavoriteBeforeAdd = techForumImpl.isTalkFavorite(talk);

        assertFalse(isFavoriteBeforeAdd);

        techForumImpl.addTalkToFavorites(talk);

        boolean isFavoriteAfterAdd = techForumImpl.isTalkFavorite(talk);

        assertTrue(isFavoriteAfterAdd);
    }

}