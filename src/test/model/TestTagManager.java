package model;

import exceptions.DataExistAlreadyException;
import exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.openmbean.KeyAlreadyExistsException;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestTagManager {
    private TagManager tagManager;

    @BeforeEach
    void runBefore() {
        TagManager.removeInstance();
        tagManager = TagManager.getInstance();
    }

    @Test
    void testConstruct() {
        assertEquals(0, tagManager.numOfTags());
    }

    @Test
    void testAddTag() {
        Tag tag = new Tag("action");
        tagManager.addNewTag(tag);
        assertEquals(1, tagManager.numOfTags());
        try {
            assertEquals(0, tagManager.getListOfMediaWithTag(tag).size());
        } catch (ItemNotFoundException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testAddTagExistException() {
        Tag tag = new Tag("action");
        try {
            tagManager.addNewTag(tag);
            tagManager.addNewTag(tag);
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
        tagManager.addNewTag(tag2);
        tagManager.addNewTag(tag);
        try {
            tagManager.tagItem(tag, mediaItem);
            tagManager.tagItem(tag, mediaItem1);
            tagManager.deleteTag(tag);
            assertEquals(1, tagManager.numOfTags());
        } catch (ItemNotFoundException | DataExistAlreadyException e) {
            fail("Should not have ran into Exception");
        }
    }


    @Test
    void testTagInList() {
        Tag tag = new Tag("action");
        tagManager.addNewTag(tag);
        try {
            tagManager.tagInList(tag);
        } catch (ItemNotFoundException e) {
            fail("Should not have ran into exception");
        }
    }

    @Test
    void testTagNotInList() {
        Tag tag = new Tag("action");
        try {
            tagManager.tagInList(tag);
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testTagItem() {
        Tag tag = new Tag("action");
        tagManager.addNewTag(tag);
        MediaItem testMediaItem = new UserMediaItem("Avengers");
        try {
            tagManager.tagItem(tag, testMediaItem);
            assertEquals(1, tagManager.getListOfMediaWithTag(tag).size());
        } catch (DataExistAlreadyException | ItemNotFoundException e) {
            e.printStackTrace();
            fail("Should not have ran into any exception");
        }
    }

    @Test
    void testTagItemAlreadyExist() {
        Tag tag = new Tag("action");
        tagManager.addNewTag(tag);
        MediaItem testMediaItem = new UserMediaItem("Avengers");
        try {
            tagManager.tagItem(tag, testMediaItem);
            tagManager.tagItem(tag, testMediaItem);
        } catch (ItemNotFoundException e) {
            fail("Should not have ran into this exception");
        } catch (DataExistAlreadyException e) {
            //expected
        }
    }

    @Test
    void testRemoveTag() {
        Tag tag = new Tag("action");
        tagManager.addNewTag(tag);
        MediaItem testMediaItem = new UserMediaItem("Avengers");
        try {
            tagManager.tagItem(tag, testMediaItem);
            tagManager.removeTag(tag, testMediaItem);
            System.out.println("Wprking");
            assertEquals(0, tagManager.getListOfMediaWithTag(tag).size());
        } catch (DataExistAlreadyException | ItemNotFoundException e) {
            e.printStackTrace();
            fail("Should not have ran into any exceptions");
        }
    }

    @Test
    void testRemoveItemWithNoTag() {
        Tag tag = new Tag("action");
        tagManager.addNewTag(tag);
        MediaItem testMediaItem = new UserMediaItem("Avengers");
        try {
            tagManager.removeTag(tag, testMediaItem);
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testGetAllTags() {
        Tag tag = new Tag("action");
        Tag tag2 = new Tag("adventure");
        tagManager.addNewTag(tag);
        tagManager.addNewTag(tag2);
        assertEquals(2, tagManager.getAllActiveTags().size());
    }
}
