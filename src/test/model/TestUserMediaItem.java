package model;

import exceptions.DataExistAlreadyException;
import exceptions.ItemNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestUserMediaItem {
    private UserMediaItem mediaItem;

    @BeforeEach
    void runBefore() {
        mediaItem = new UserMediaItem("Avengers");
    }

    @Test
    void testConstruct() {
        Map<String, String> itemDetails = mediaItem.itemDetails;
        assertTrue(itemDetails.containsKey("Title"));
        assertTrue(itemDetails.containsKey("Type"));
        assertTrue(itemDetails.containsKey("Status"));
        assertTrue(itemDetails.containsKey("UserRating"));
        assertTrue(itemDetails.containsKey("UserComments"));
        try {
            assertEquals("Avengers", mediaItem.getItemInfo("Title"));
        } catch (ItemNotFoundException e) {
            fail("Should Not have ran into Exception");
        }
        assertEquals(5, mediaItem.numOfItemDetails());
        assertEquals(0, mediaItem.numOfListMetaDataTypes() + mediaItem.numOfTagMetaDataTypes());
    }

    @Test
    void testNotActive() {
        assertFalse(mediaItem.isActive());
    }

    @Test
    void testActive() {
        try {
            mediaItem.updateData("List", "To watch list");
            assertTrue(mediaItem.isActive());
        } catch (DataExistAlreadyException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testUpdateDataException() {
        try {
            mediaItem.updateData("notexist", "To watch");
        } catch (DataExistAlreadyException e) {
            fail("Should not have ran into DataExistAlreadyException");
        }
    }

    @Test
    void testUpdateDataExistsException() {
        try {
            mediaItem.updateData("List", "To watch");
            mediaItem.updateData("List", "To watch");
        } catch (DataExistAlreadyException e) {
            //expected
        }
    }

    @Test
    void testGetItemInfo() {
        try {
            assertEquals("Avengers" ,mediaItem.getItemInfo("Title"));
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testRemoveData() {
        try {
            mediaItem.updateData("List", "To watch list");
            mediaItem.removeData("List", "To watch list");
        } catch (ItemNotFoundException | DataExistAlreadyException e) {
            e.printStackTrace();
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testRemoveDataException() {
        try {
            mediaItem.removeData("dontexist", "To watch list");
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testSetInfo() {
        try {
            mediaItem.setItemInfo("Title", "Avengers");
            assertEquals("Avengers", mediaItem.getItemInfo("Title"));
        } catch (ItemNotFoundException e) {
            e.printStackTrace();
            fail("Should not have rain into ItemNotFoundException");
        }
    }

    @Test
    void testSetInfoException() {
        try {
            mediaItem.setItemInfo("doesntexist", "nope");
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testIsEquals() {
        MediaItem mediaItem2 = new UserMediaItem("Avengers");
        assertEquals(mediaItem, mediaItem2);
        try {
            mediaItem.updateData("List", "to watch list");
            mediaItem2.updateData("List", "to watch list");
            assertEquals(mediaItem, mediaItem2);
        } catch (DataExistAlreadyException e) {
            e.printStackTrace();
            fail("Should Not have found any Exception");
        }
    }

    @Test
    void testIsEqualsDiffClass() {
        Tag tag = new Tag("test");
        assertNotEquals(mediaItem, tag);
    }

    @Test
    void testIsEqualsNull() {
        assertNotEquals(mediaItem, null);
    }

    @Test
    void testHash() {
        Map<MediaItem, String> testHash = new HashMap<>();
        MediaItem mediaItem2 = new UserMediaItem("Avengers");
        testHash.put(mediaItem, "test");
        assertEquals("test", testHash.get(mediaItem2));
    }

    @Test
    void testGetMetaDataOfList() {
        try {
            assertEquals(0, mediaItem.getMetaDataOfType("List").size());
        } catch (ItemNotFoundException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testGetMetaDataOfTag() {
        try {
            mediaItem.updateData("Tag", "action");
            assertEquals(1, mediaItem.getMetaDataOfType("Tag").size());
        } catch (ItemNotFoundException | DataExistAlreadyException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testRemoveMetaDataOfTypeException() {
        try {
            mediaItem.removeData("List", "none");
        } catch (ItemNotFoundException e) {
            //expected
        }
    }


    @Test
    void testGetMetaDataOfTypeException() {
        try {
            mediaItem.getMetaDataOfType("none").size();
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testGetItemInfoException() {
        try {
            mediaItem.getItemInfo("none");
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testContainsMetaDataOfList() {
        try {
            mediaItem.updateData("List", "yes");
            mediaItem.updateData("List", "no");
            assertTrue(mediaItem.containMetaDataOf("List", "no"));
        } catch (DataExistAlreadyException | ItemNotFoundException e) {
            fail("Should not have ran into exception");
            e.printStackTrace();
        }
    }

    @Test
    void testContainsMetaDataOfTag() {
        try {
            mediaItem.updateData("Tag", "action");
            mediaItem.updateData("Tag", "adventure");
            assertTrue(mediaItem.containMetaDataOf("Tag", "adventure"));
        } catch (DataExistAlreadyException | ItemNotFoundException e) {
            fail("Should not have ran into exception");
        }
    }

    @Test
    void testContainsMetaDataOfException() {
        try {
            mediaItem.containMetaDataOf("none", "nope");
        } catch (ItemNotFoundException e) {
            //expected
        }
    }

    @Test
    void testNotContainMetaDataOf() {
        try {
            assertFalse(mediaItem.containMetaDataOf("List", "action"));
        } catch (ItemNotFoundException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testToString() {
        assertEquals("Avengers", mediaItem.toString());
    }

}
