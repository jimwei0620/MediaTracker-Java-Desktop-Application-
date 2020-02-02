package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
       ArrayList<MediaItem> toWatchList = list1.getList();
       assertEquals(toWatchList.size(), 1);
    }
}
