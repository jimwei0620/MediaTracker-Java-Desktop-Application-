package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestMetaData {
    private MetaData metaData;

    @BeforeEach
    void runBefore() {
        metaData = new MetaData("List");
    }

    @Test
    void testConstruct() {
        Calendar calendar = Calendar.getInstance();
        assertEquals(calendar.getTime().toString(), metaData.getDate());
        assertEquals("List", metaData.getNameOfObject());
    }

    @Test
    void testEquals() {
        MetaData data2 = new MetaData("List");
        assertEquals(data2, metaData);
        MetaData data3 = new MetaData("list");
        assertNotEquals(data3, metaData);
    }

    @Test
    void testIsEqualsNull() {
        assertNotEquals(metaData, null);
    }

    @Test
    void testIsEquals() {
        MetaData newMetaData = new MetaData(metaData.getNameOfObject());
        assertEquals(metaData, newMetaData);
    }

    @Test
    void testIsEqualsSameObject() {
        assertEquals(metaData, metaData);
    }

    @Test
    void testIsEqualsDiffClass() {
        Tag tag = new Tag("test");
        assertNotEquals(metaData, tag);
    }


    @Test
    void testHashCode() {
        Map<MetaData, String> testHashMap = new HashMap<>();
        testHashMap.put(metaData, "test");
        MetaData data2 = new MetaData("List");
        assertEquals("test", testHashMap.get(data2));
        MetaData data3 = new MetaData("list");
        assertNotEquals("test", testHashMap.get(data3));
    }

    @Test
    void testSetNameOfObject() {
        metaData.setNameOfObject("newObject");
        assertEquals("newObject", metaData.getNameOfObject());
    }

}
