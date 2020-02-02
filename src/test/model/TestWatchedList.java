package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestWatchedList {
    private WatchedList list1;

    @BeforeEach
    public void runBefore() {
        list1 = new WatchedList("List1");
    }

    @Test
    public void testConstruct() {
        assertEquals(list1.getName(), "List1");
        ArrayList<MediaItem> sampleList = list1.getList();
        assertEquals(sampleList.size(), 0);
    }

    @Test
    public void testSetName() {
        list1.setName("List2");
        assertEquals(list1.getName(), "List2");
    }

    @Test
    public void testAddMedia() {
        MediaItem newMedia = new MediaItem("Avengers", true);
        list1.addMedia(newMedia);
        ArrayList<MediaItem> toWatchListArray = list1.getList();
        assertEquals(toWatchListArray.size(), 1);
    }

    @Test
    public void testRemoveMedia() {
        MediaItem newMedia1 = new MediaItem("Avengers", true);
        MediaItem newMedia2 = new MediaItem("Endgame", true);
        list1.addMedia(newMedia1);
        list1.addMedia(newMedia2);
        list1.removeMedia(newMedia1);
        ArrayList<MediaItem> toWatchListArray = list1.getList();
        assertEquals(toWatchListArray.size(), 1);
        assertEquals(toWatchListArray.get(0), newMedia2);
    }

    @Test
    public void testSetMediaToNotWatchedList() {
        MediaItem newMedia1 = new MediaItem("Avengers", false);
        ToWatchList toWatchList = new ToWatchList("to be watched");
        list1.addMedia(newMedia1);
        list1.setMediaToNotWatched(newMedia1, toWatchList);
        ArrayList<MediaItem> watchedListArray = list1.getList();
        ArrayList<MediaItem> toWatchListArray = toWatchList.getList();
        assertEquals(toWatchListArray.size(),1);
        assertFalse(newMedia1.getWatchStatus());
        assertEquals(watchedListArray.size(), 0);
    }
}
