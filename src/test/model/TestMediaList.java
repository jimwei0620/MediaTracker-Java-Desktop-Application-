package model;

import exceptions.EmptyStringException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestMediaList {
    private MediaList list1;

    @BeforeEach
    void runBefore() {
        try {
            list1 = new MediaList("List1");
        } catch (EmptyStringException e){
            fail("Should not have ran into EmptyStringException");
        }
    }

    @Test
    void testMediaListInitEmpty() {
        try {
            MediaList newList = new MediaList("");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    void testConstruct() {
        Calendar calendar = Calendar.getInstance();
        assertEquals("List1", list1.getName());
        assertEquals(calendar.getTime().toString(), list1.getDate());
    }

    @Test
    void testSetName() {
        try {
            list1.setName("New Name");
            assertEquals("New Name", list1.getName());
        } catch (EmptyStringException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testSetNameException() {
        try {
            list1.setName("");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    void testIsEquals() {
        try {
            MediaList mediaList2 = new MediaList("List1");
            MediaList mediaList3 = new MediaList("list1");
            assertEquals(list1, mediaList2);
            assertNotEquals(list1, mediaList3);
        } catch (EmptyStringException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testIsEqualsNull() {
        assertNotEquals(list1, null);
    }

    @Test
    void testIsEqualsDiffClass() {
        MediaItem mediaItem = new UserMediaItem("test");
        assertNotEquals(list1, mediaItem);
    }

    @Test
    void testHashCode() {
        Map<MediaList, String> hashCode = new HashMap<>();
        try {
            MediaList mediaList2 = new MediaList("List1");
            MediaList mediaList3 = new MediaList("list1");
            hashCode.put(list1, "test");
            assertEquals("test", hashCode.get(mediaList2));
            assertFalse(hashCode.containsKey(mediaList3));
        } catch (EmptyStringException e) {
            fail("Should not have ran into Exception");
        }
    }

    @Test
    void testToString() {
        assertEquals("List1", list1.toString());
    }

    @Test
    void testSetDate() {
        list1.setDate("new date");
        assertEquals("new date", list1.getDate());
    }
}
