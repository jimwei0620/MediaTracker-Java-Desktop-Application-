package model;

import exceptions.DataExistAlreadyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestItemManager {
    private ItemManager itemManager;

    @BeforeEach
    void runBefore() {
        ItemManager.removeInstance();
        itemManager = ItemManager.getInstance();
    }

    @Test
    void testConstruct() {
        assertEquals(0, itemManager.totalNumOfUserItems());
    }

    @Test
    void testGetAllUserMediaItems() {
        assertEquals(0, ItemManager.getInstance().getAllUserMediaItems().size());
    }

    @Test
    void testContains() {
        MediaItem testItem = new UserMediaItem("test");
        itemManager.addItem(testItem);
        assertTrue(itemManager.contains(testItem));
        itemManager.removeItem(testItem);
        assertFalse(itemManager.contains(testItem));
    }

    @Test
    void testDoesNotContain() {
        MediaItem testItem = new UserMediaItem("test");
        MediaItem notInList = new UserMediaItem("test2");
        itemManager.addItem(testItem);
        itemManager.removeItem(notInList);
        assertEquals(1, itemManager.getAllUserMediaItems().size());
    }

    @Test
    void testRemoveInActiveItemsTrue() {
        MediaItem testItem = new UserMediaItem("test");//item not related to any lists or tags
        itemManager.addItem(testItem);
        assertTrue(itemManager.removeInactiveItem(testItem));
        assertFalse(itemManager.contains(testItem));
    }

    @Test
    void testRemoveInActiveItemsFalse() {
        MediaItem testItem = new UserMediaItem("test");
        itemManager.addItem(testItem);
        try {
            testItem.updateData("List", "new list");
        } catch (DataExistAlreadyException e) {
            fail("should not have ran into exception");
        }
        assertFalse(itemManager.removeInactiveItem(testItem));
        assertTrue(itemManager.contains(testItem));
    }
}
