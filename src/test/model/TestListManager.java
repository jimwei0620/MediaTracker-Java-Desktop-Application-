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
    public void runBefore() {
        listManager = new ListManager();
    }

    @Test
    public void testConstruct() {
        assertEquals(0, listManager.numOfLists());
        assertEquals(0, listManager.numOfTags());
        assertEquals(0, listManager.totalNumOfUserItems());
    }

    @Test
    public void testAddTag() {
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
    public void testAddTagExistException() {
        Tag tag = new Tag("action");
        try {
            listManager.addNewTag(tag);
            listManager.addNewTag(tag);
        } catch (KeyAlreadyExistsException e) {
            //expected
        }
    }

    @Test
    public void testDeleteTag() {
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
    public void testTagInList() {
        Tag tag = new Tag("action");
        listManager.addNewTag(tag);
        try {
            listManager.tagInList(tag);
        } catch (ItemNotFoundException e) {
            fail("Should not have ran into exception");
        }
    }

    @Test
    public void testTagNotInList() {
        Tag tag = new Tag("action");
        try {
            listManager.tagInList(tag);
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    public void testTagItem() {
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
    public void testTagItemAlreadyExist() {
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
    public void testRemoveTag() {
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
    public void testRemoveItemWithNoTag() {
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
    public void testRemoveInactiveItemFalse() {
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
    public void testRemoveInactiveTrue() {
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
    public void testListNotAlreadyExists() {
        try {
            MediaList mediaList = new MediaList("new list");
            listManager.addNewList(mediaList);
            listManager.listAlreadyExists(mediaList);
        } catch (EmptyStringException | ItemNotFoundException | KeyAlreadyExistsException e) {
            fail("Should not have ran into exception");
        }
    }

    @Test
    public void testListAlreadyExists() {
        try {
            MediaList mediaList = new MediaList("new list");
            listManager.listAlreadyExists(mediaList);
        } catch (EmptyStringException | ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    public void testAddNewListException() {
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
    public void testGetMediaListByNameException() {
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
    public void testGetMediaListByName() {
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
    public void testGetListOfMedia() {
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
    public void testGetMediaItemInList() {
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
    public void testGetMediaItemInListNotFound() {
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
    public void testGetTagSet() {
        Map<Tag, ArrayList<MediaItem>> tagSet = listManager.getTagSet();
        assertEquals(0, tagSet.keySet().size());
    }

    @Test
    public void testGetAllActiveList() {
        assertEquals(0, listManager.allActiveLists().size());
    }

    @Test
    public void testDeleteMediaFromList() {
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
    public void testAddMediaToListAlreadyExist() {
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
    public void testGetAllUserMediaItems() {
        assertEquals(0, listManager.getAllUserMediaItems().size());
    }

    @Test
    public void testGetAllTags() {
        Tag tag = new Tag("action");
        Tag tag2 = new Tag("adventure");
        listManager.addNewTag(tag);
        listManager.addNewTag(tag2);
        assertEquals(2, listManager.getAllActiveTags().size());
    }
}
