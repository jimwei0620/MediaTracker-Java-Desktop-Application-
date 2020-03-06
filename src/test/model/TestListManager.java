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
        listManager = new ListManager();
    }

    @Test
    void testConstruct() {
        assertEquals(0, listManager.numOfLists());
        assertEquals(0, listManager.numOfTags());
        assertEquals(0, listManager.totalNumOfUserItems());
    }

    @Test
    void testAddTag() {
        Tag tag = new Tag("action");
        listManager.addNewTag(tag);
        assertEquals(1, listManager.numOfTags());
        try {
            assertEquals(0, listManager.getListOfMediaWithTag(tag).size());
        } catch (ItemNotFoundException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testAddTagExistException() {
        Tag tag = new Tag("action");
        try {
            listManager.addNewTag(tag);
            listManager.addNewTag(tag);
        } catch (KeyAlreadyExistsException e) {
            //expected
        }
    }

    @Test
    void testDeleteTag() {
        Tag tag = new Tag("action");
        Tag tag2 = new Tag("adventure");
        MediaItem mediaItem = new UserMediaItem("test");
        MediaItem mediaItem1 = new UserMediaItem("test2");
        listManager.addNewTag(tag2);
        listManager.addNewTag(tag);
        try {
            listManager.tagItem(tag, mediaItem);
            listManager.tagItem(tag, mediaItem1);
            listManager.deleteTag(tag);
            assertEquals(1, listManager.numOfTags());
        } catch (ItemNotFoundException | DataExistAlreadyException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testTagInList() {
        Tag tag = new Tag("action");
        listManager.addNewTag(tag);
        try {
            listManager.tagInList(tag);
        } catch (ItemNotFoundException e) {
            fail("Should not have ran into exception");
        }
    }

    @Test
    void testTagNotInList() {
        Tag tag = new Tag("action");
        try {
            listManager.tagInList(tag);
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testTagItem() {
        Tag tag = new Tag("action");
        listManager.addNewTag(tag);
        MediaItem testMediaItem = new UserMediaItem("Avengers");
        try {
            listManager.tagItem(tag, testMediaItem);
            assertEquals(1, listManager.getListOfMediaWithTag(tag).size());
        } catch (DataExistAlreadyException | ItemNotFoundException e) {
            e.printStackTrace();
            fail("Should not have ran into any exception");
        }
    }

    @Test
    void testTagItemAlreadyExist() {
        Tag tag = new Tag("action");
        listManager.addNewTag(tag);
        MediaItem testMediaItem = new UserMediaItem("Avengers");
        try {
            listManager.tagItem(tag, testMediaItem);
            listManager.tagItem(tag, testMediaItem);
        } catch (ItemNotFoundException e) {
            fail("Should not have ran into this exception");
        } catch (DataExistAlreadyException e) {
            //expected
        }
    }

    @Test
    void testRemoveTag() {
        Tag tag = new Tag("action");
        listManager.addNewTag(tag);
        MediaItem testMediaItem = new UserMediaItem("Avengers");
        try {
            listManager.tagItem(tag, testMediaItem);
            listManager.removeTag(tag, testMediaItem);
            System.out.println("Wprking");
            assertEquals(0, listManager.getListOfMediaWithTag(tag).size());
        } catch (DataExistAlreadyException | ItemNotFoundException e) {
            e.printStackTrace();
            fail("Should not have ran into any exceptions");
        }
    }

    @Test
    void testRemoveItemWithNoTag() {
        Tag tag = new Tag("action");
        listManager.addNewTag(tag);
        MediaItem testMediaItem = new UserMediaItem("Avengers");
        try {
            listManager.removeTag(tag, testMediaItem);
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testRemoveInactiveItemFalse() {
        UserMediaItem mediaItem = new UserMediaItem("Avengers");
        try {
            MediaList mediaList = new MediaList("list");
            listManager.addNewList(mediaList);
            listManager.addMediaItemToList(mediaList, mediaItem);
            assertFalse(listManager.removeInactiveItem(mediaItem));
        } catch (EmptyStringException | ItemNotFoundException | DataExistAlreadyException e) {
            e.printStackTrace();
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testRemoveInactiveTrue() {
        UserMediaItem mediaItem = new UserMediaItem("Avengers");
        try {
            MediaList mediaList = new MediaList("list");
            listManager.addNewList(mediaList);
            listManager.addMediaItemToList(mediaList, mediaItem);
            listManager.removeList(mediaList);
            assertEquals(0, listManager.numOfLists());
            assertEquals(0, listManager.totalNumOfUserItems());
        } catch (EmptyStringException | ItemNotFoundException | DataExistAlreadyException e) {
            e.printStackTrace();
            fail("Should not have ran into Exception");
        }
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
    void testGetTagSet() {
        Map<Tag, ArrayList<MediaItem>> tagSet = listManager.getTagSet();
        assertEquals(0, tagSet.keySet().size());
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
    void testAddMediaToListInAll() {
        try {
            MediaList mediaList = new MediaList("test");
            UserMediaItem mediaItem = new UserMediaItem("testItem");
            listManager.addNewList(mediaList);
            listManager.getAllUserMediaItems().add(mediaItem);
            listManager.addMediaItemToList(mediaList, mediaItem);
        } catch (DataExistAlreadyException | ItemNotFoundException |EmptyStringException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testGetAllUserMediaItems() {
        assertEquals(0, listManager.getAllUserMediaItems().size());
    }

    @Test
    void testGetAllTags() {
        Tag tag = new Tag("action");
        Tag tag2 = new Tag("adventure");
        listManager.addNewTag(tag);
        listManager.addNewTag(tag2);
        assertEquals(2, listManager.getAllActiveTags().size());
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
}
