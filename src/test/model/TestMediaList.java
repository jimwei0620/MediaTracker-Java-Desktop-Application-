package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestMediaList {
    private MediaList list1;

    @BeforeEach
    public void runBefore() {
        list1 = new MediaList("List1");
    }

    @Test
    public void testConstruct() {
        assertEquals(list1.getName(), "List1");
        ArrayList<MediaItem> sampleList = list1.getList();
        assertEquals(0, sampleList.size());
    }

    @Test
    public void testSetName() {
        list1.setName("List2");
        assertEquals("List2", list1.getName());
    }

    @Test
    public void testAddMedia() {
        MediaItem newMedia = new MediaItem("Avengers");
        list1.addMedia(newMedia);
        ArrayList<MediaItem> toWatchListArray = list1.getList();
        assertEquals(1, toWatchListArray.size());
    }

    @Test
    public void testRemoveMedia() {
        MediaItem newMedia1 = new MediaItem("Avengers");
        MediaItem newMedia2 = new MediaItem("Endgame");
        list1.addMedia(newMedia1);
        list1.addMedia(newMedia2);
        list1.removeMedia(newMedia1);
        ArrayList<MediaItem> toWatchListArray = list1.getList();
        assertEquals(1, toWatchListArray.size());
        assertEquals(newMedia2, toWatchListArray.get(0));
    }

    @Test
    public void testGetItemByName() {
        MediaItem newMedia1 = new MediaItem("Avengers");
        list1.addMedia(newMedia1);
        assertNull(list1.getMediaItemByName("Endgame"));
        assertEquals(newMedia1, list1.getMediaItemByName("Avengers"));
    }
}
