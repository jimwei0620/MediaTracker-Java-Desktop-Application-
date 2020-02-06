package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestListManager {
    private ListManager listManager;

    @BeforeEach
    public void runBefore() {
        listManager = new ListManager();
    }

    @Test
    public void testConstruct() {
        assertEquals(0, listManager.size());
    }

    @Test
    public void testAddToColl() {
        MediaList mediaList = new MediaList("Things to watch");
        listManager.addToColl(mediaList);
        assertEquals(1, listManager.size());
    }

    @Test
    public void testRemove() {
        MediaList mediaList1 = new MediaList("To watch list");
        MediaList mediaList2 = new MediaList("Watched list");
        listManager.addToColl(mediaList1);
        listManager.addToColl(mediaList2);
        assertEquals(2, listManager.size());
        listManager.remove(mediaList2);
        assertEquals(1, listManager.size());
    }

    @Test
    public void testFindMediaListByName() {
        assertEquals(null, listManager.findMediaListByName("To watch list"));
        MediaList mediaList = new MediaList("To watch list");
        MediaList mediaList1 = new MediaList("Watched list");
        listManager.addToColl(mediaList);
        listManager.addToColl(mediaList1);
        assertEquals(mediaList, listManager.findMediaListByName("To watch list"));
        assertEquals(mediaList1, listManager.findMediaListByName("Watched list"));
    }

    @Test
    public void testGetList() {
        MediaList mediaList = new MediaList("To watch list");
        listManager.addToColl(mediaList);
        ArrayList<MediaList> returnedList = listManager.getList();
        assertEquals(1, returnedList.size());
        assertEquals(mediaList, returnedList.get(0));
    }
}
