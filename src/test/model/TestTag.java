package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestTag {
    private Tag tag;

    @BeforeEach
    void runBefore() {
        tag = new Tag("Action");
    }

    @Test
    void testConstruct() {
        assertEquals("Action", tag.getTagName());
    }

    @Test
    void testSetName() {
        tag.setTagName("newName");
        assertEquals("newName" ,tag.getTagName());
    }

    @Test
    void testEquals() {
        Tag tag2 = new Tag("Action");
        assertEquals(tag2, tag);
        Tag tag3 = new Tag("action");
        assertEquals(tag3, tag);
    }

    @Test
    void testIsEqualsNull() {
        assertNotEquals(tag, null);
    }

    @Test
    void testIsEqualsDiffClass() {
        MediaItem mediaItem = new UserMediaItem("test");
        assertNotEquals(tag, mediaItem);
    }
    @Test
    void testIsEquals() {
        Tag newTag = new Tag(tag.getTagName());
        assertEquals(tag, newTag);
    }

    @Test
    void testIsEqualsSameObject() {
        assertEquals(tag, tag);
    }

    @Test
    void testHashCode() {
        Map<Tag, String> testHashMap = new HashMap<>();
        testHashMap.put(tag, "test");
        Tag tag2 = new Tag("Action");
        assertEquals("test", testHashMap.get(tag2));
        Tag tag3 = new Tag("action");
        assertEquals("test", testHashMap.get(tag3));
    }


}
