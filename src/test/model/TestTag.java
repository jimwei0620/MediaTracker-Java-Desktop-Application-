package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestTag {
    public Tag tag;

    @BeforeEach
    public void runBefore() {
        tag = new Tag("Action");
    }

    @Test
    public void testConstruct() {
        assertEquals("Action", tag.getTagName());
    }

    @Test
    public void testSetName() {
        tag.setTagName("newName");
        assertEquals("newName" ,tag.getTagName());
    }

    @Test
    public void testEquals() {
        Tag tag2 = new Tag("Action");
        assertEquals(tag2, tag);
        Tag tag3 = new Tag("action");
        assertEquals(tag3, tag);
    }

    @Test
    public void testIsEqualsNull() {
        MediaItem mediaItem = new UserMediaItem("test");
        assertNotEquals(tag, mediaItem);
    }

    @Test
    public void testIsEquals() {
        Tag newTag = new Tag(tag.tagName);
        assertEquals(tag, newTag);
    }

    @Test
    public void testIsEqualsSameObject() {
        assertEquals(tag, tag);
    }

    @Test
    public void testHashCode() {
        Map<Tag, String> testHashMap = new HashMap<>();
        testHashMap.put(tag, "test");
        Tag tag2 = new Tag("Action");
        assertEquals("test", testHashMap.get(tag2));
        Tag tag3 = new Tag("action");
        assertEquals("test", testHashMap.get(tag3));
    }
}
