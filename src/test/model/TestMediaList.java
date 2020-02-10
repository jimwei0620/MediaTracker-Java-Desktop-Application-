package model;

import exceptions.EmptyStringException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestMediaList {
    private MediaList list1;

    @BeforeEach
    public void runBefore() {
        try {
        list1 = new MediaList("List1");
        } catch (EmptyStringException e){
            fail("Should not have ran into EmptyStringException");
        }
    }

    @Test
    public void testConstruct() {
        assertEquals(list1.getName(), "List1");
        ArrayList<MediaItem> sampleList = list1.getList();
        assertEquals(0, sampleList.size());
    }

    @Test
    public void testSetName() {
        try {
            list1.setName("List2");
        } catch (EmptyStringException e) {
            fail("Should not have ran into EmptyStringException");
        }
        assertEquals("List2", list1.getName());
    }

    @Test
    public void testAddMedia() {
        try {
        MediaItem newMedia = new MediaItem("Avengers");
        list1.addMedia(newMedia);
        ArrayList<MediaItem> toWatchListArray = list1.getList();
        assertEquals(1, toWatchListArray.size());
        } catch (EmptyStringException e) {
            fail("Should not have ran into EmptyStringException");
        }
    }

    @Test
    public void testRemoveMedia() {
        try {
            MediaItem newMedia1 = new MediaItem("Avengers");
            MediaItem newMedia2 = new MediaItem("Endgame");
            list1.addMedia(newMedia1);
            list1.addMedia(newMedia2);
            list1.removeMedia(newMedia1);
            ArrayList<MediaItem> toWatchListArray = list1.getList();
            assertEquals(1, toWatchListArray.size());
            assertEquals(newMedia2, toWatchListArray.get(0));
        } catch (EmptyStringException e){
            fail("Should not have ran into EmptyStringException");
        }
    }

    @Test
    public void testGetItemByName() {
        try {
            MediaItem newMedia1 = new MediaItem("Avengers");
            list1.addMedia(newMedia1);
            assertNull(list1.getMediaItemByName("Endgame"));
            assertEquals(newMedia1, list1.getMediaItemByName("Avengers"));
        } catch (EmptyStringException e) {
            fail("Should not have ran into EmptyStringException");
        }
    }
}
