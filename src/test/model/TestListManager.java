package model;

import exceptions.DataExistAlreadyException;
import exceptions.EmptyStringException;
import exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestListManager {
    private ListManager listManager;

    @BeforeEach
    void runBefore() {
        ListManager.removeInstance();
        listManager = ListManager.getInstance();
    }

    @Test
    void testConstruct() {
        assertEquals(0, listManager.numOfLists());
    }

    @Test
    void testListNotAlreadyExists() {
        try {
            MediaList mediaList = new MediaList("new list");
            listManager.addNewList(mediaList);
            listManager.listAlreadyExists(mediaList);
        } catch (EmptyStringException | ItemNotFoundException | KeyAlreadyExistsException e) {
            fail("Should not have ran into exception");
        }
    }

    @Test
    void testListAlreadyExists() {
        try {
            MediaList mediaList = new MediaList("new list");
            listManager.listAlreadyExists(mediaList);
        } catch (EmptyStringException | ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testAddNewListException() {
        try {
            MediaList mediaList = new MediaList("new list");
            listManager.addNewList(mediaList);
            listManager.addNewList(mediaList);
        } catch (EmptyStringException e) {
            fail("Should not have ran into these exceptions");
        } catch (KeyAlreadyExistsException e) {
            //expected
        }
    }

    @Test
    void testGetMediaListByNameException() {
        try {
            MediaList mediaList = new MediaList("1");
            MediaList mediaList2 = new MediaList("2");
            listManager.addNewList(mediaList);
            listManager.addNewList(mediaList2);
            listManager.getMediaListByName("none");
        } catch (EmptyStringException e) {
            fail("Should not have ran into EmptyStringException");
        } catch (ItemNotFoundException e) {
            //expected;
        }
    }

    @Test
    void testGetMediaListByName() {
        try {
            MediaList mediaList = new MediaList("new");
            MediaList mediaList2 = new MediaList("2");
            listManager.addNewList(mediaList);
            listManager.addNewList(mediaList2);
            assertTrue(mediaList2.equals(listManager.getMediaListByName("2")));
        } catch (EmptyStringException | ItemNotFoundException e) {
            fail("Should not have ran into exception");
        }
    }

    @Test
    void testGetListOfMedia() {
        try {
            MediaList mediaList = new MediaList("new");
            UserMediaItem mediaItem = new UserMediaItem("Avengers");
            listManager.addNewList(mediaList);
            listManager.addMediaItemToList(mediaList, mediaItem);
            assertEquals(1, listManager.getListOfMedia(mediaList).size());
        } catch (DataExistAlreadyException | EmptyStringException | ItemNotFoundException e) {
            fail("Should not have ran into exception");
        }
    }

    @Test
    void testGetMediaItemInList() {
        try {
            MediaList mediaList = new MediaList("new");
            UserMediaItem mediaItem = new UserMediaItem("Avengers");
            UserMediaItem mediaItem1 = new UserMediaItem("Avengers1");
            UserMediaItem mediaItem2 = new UserMediaItem("Avengers2");
            listManager.addNewList(mediaList);
            listManager.addMediaItemToList(mediaList, mediaItem);
            listManager.addMediaItemToList(mediaList, mediaItem1);
            listManager.addMediaItemToList(mediaList, mediaItem2);
            ArrayList<MediaItem> listOfItems = listManager.getListOfMedia(mediaList);
            MediaItem itemReturned = listManager.getMediaItemInListByName("Avengers2", listOfItems);
            assertEquals(mediaItem2, itemReturned);
        } catch (DataExistAlreadyException | EmptyStringException | ItemNotFoundException e) {
            fail("Should not have ran into exception");
        }
    }

    @Test
    void testGetMediaItemInListNotFound() {
        try {
            MediaList mediaList = new MediaList("new");
            UserMediaItem mediaItem = new UserMediaItem("Avengers");
            UserMediaItem mediaItem1 = new UserMediaItem("Avengers1");
            listManager.addNewList(mediaList);
            listManager.addMediaItemToList(mediaList, mediaItem);
            listManager.addMediaItemToList(mediaList, mediaItem1);
            ArrayList<MediaItem> listOfItems = listManager.getListOfMedia(mediaList);
            MediaItem itemReturned = listManager.getMediaItemInListByName("none", listOfItems);
        } catch (DataExistAlreadyException | EmptyStringException e) {
            fail("Should not have ran into exception");
        } catch (ItemNotFoundException e) {
            //expected;
        }
    }

    @Test
    void testGetAllActiveList() {
        assertEquals(0, listManager.allActiveLists().size());
    }

    @Test
    void testDeleteMediaFromList() {
        try {
            MediaList mediaList = new MediaList("test");
            UserMediaItem mediaItem = new UserMediaItem("Avengers");
            listManager.addNewList(mediaList);
            listManager.addMediaItemToList(mediaList, mediaItem);
            listManager.deleteMediaItemFromList(mediaList, mediaItem);
            assertEquals(0, listManager.getListOfMedia(mediaList).size());
        } catch (ItemNotFoundException | EmptyStringException | DataExistAlreadyException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testAddMediaToListAlreadyExist() {
        try {
            MediaList mediaList = new MediaList("test");
            UserMediaItem mediaItem = new UserMediaItem("yes");
            UserMediaItem mediaItem1 = new UserMediaItem("yes");
            listManager.addNewList(mediaList);
            listManager.addMediaItemToList(mediaList, mediaItem);
            listManager.addMediaItemToList(mediaList, mediaItem1);
        } catch (EmptyStringException | ItemNotFoundException | DataExistAlreadyException e) {
            fail ("Should not have ran into this Exception");
        } catch (KeyAlreadyExistsException e) {
            //expected
        }
    }

    @Test
    void testAddEmptyMediaItemListToList() {
        try {
            MediaList mediaList = new MediaList("test");
            listManager.addNewList(mediaList);
            listManager.addListOfItemToList(mediaList, new ArrayList<>());
            assertEquals(0, listManager.getListOfMedia(mediaList).size());
        } catch (EmptyStringException | DataExistAlreadyException | ItemNotFoundException e) {
            fail("Should not have ran into this exception");
        }
    }

    @Test
    void testAddListOfMediaToList() {
        try {
            MediaList mediaList = new MediaList("test");
            listManager.addNewList(mediaList);
            ArrayList<MediaItem> listOfItems = new ArrayList<>();
            MediaItem mediaItem1 = new UserMediaItem("1");
            MediaItem mediaItem2 = new UserMediaItem("2");
            MediaItem mediaItem3 = new UserMediaItem("3");
            listOfItems.add(mediaItem1);
            listOfItems.add(mediaItem2);
            listOfItems.add(mediaItem3);
            listManager.addListOfItemToList(mediaList, listOfItems);
            assertEquals(3, listManager.getListOfMedia(mediaList).size());
        } catch (EmptyStringException | DataExistAlreadyException | ItemNotFoundException e) {
            fail("Should not have ran into this exception");
        }
    }

    @Test
    void testRemoveList() {
        try {
            MediaList testList = new MediaList("list1");
            MediaItem mediaItem = new UserMediaItem("testItem");
            MediaItem mediaItem1 = new UserMediaItem("item 2");
            listManager.addNewList(testList);
            listManager.addMediaItemToList(testList, mediaItem);
            listManager.addMediaItemToList(testList, mediaItem1);
            listManager.removeList(testList);
            assertEquals(0, mediaItem.getMetaDataOfType("List").size());
            assertEquals(0, listManager.allActiveLists().size());
        } catch (EmptyStringException | ItemNotFoundException | DataExistAlreadyException e) {
            fail("should not have ran into exception");
        }
    }

    @Test
    void testRemoveListNotFound() {
        try {
            MediaList testList = new MediaList("list1");
            listManager.removeList(testList);
            fail("Should have caught exception");
        } catch (EmptyStringException | ItemNotFoundException e) {
            //expected
        }
    }
}
