package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestToWatchList {
    private ToWatchList list1;

    @BeforeEach
    public void runBefore() {
        list1 = new ToWatchList("List1");
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
       MediaItem newMedia = new MediaItem("Avengers", false);
       list1.addMedia(newMedia);
       ArrayList<MediaItem> toWatchListArray = list1.getList();
       assertEquals(toWatchListArray.size(), 1);
    }

    @Test
    public void testRemoveMedia() {
        MediaItem newMedia1 = new MediaItem("Avengers", false);
        MediaItem newMedia2 = new MediaItem("Endgame", false);
        list1.addMedia(newMedia1);
        list1.addMedia(newMedia2);
        list1.removeMedia(newMedia1);
        ArrayList<MediaItem> toWatchListArray = list1.getList();
        assertEquals(toWatchListArray.size(), 1);
        assertEquals(toWatchListArray.get(0), newMedia2);
    }

    @Test
    public void testSetMediaToWatchedList() {
        MediaItem newMedia1 = new MediaItem("Avengers", false);
        WatchedList watchedList = new WatchedList("watched");
        list1.addMedia(newMedia1);
        list1.setMediaToWatched(newMedia1, watchedList);
        ArrayList<MediaItem> toWatchListArray = list1.getList();
        ArrayList<MediaItem> watchedListArray = watchedList.getList();
        assertEquals(toWatchListArray.size(),0);
        assertTrue(newMedia1.getWatchStatus());
        assertEquals(watchedListArray.size(), 1);
    }
}
