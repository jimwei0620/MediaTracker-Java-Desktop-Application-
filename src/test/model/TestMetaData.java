package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestMetaData {
    public MetaData metaData;

    @BeforeEach
    public void runBefore() {
        metaData = new MetaData("List");
    }

    @Test
    public void testConstruct() {
        Calendar calendar = Calendar.getInstance();
        assertEquals(calendar.getTime().toString(), metaData.getDate());
        assertEquals("List", metaData.getNameOfObject());
    }

    @Test
    public void testEquals() {
        MetaData data2 = new MetaData("List");
        assertEquals(data2, metaData);
        MetaData data3 = new MetaData("list");
        assertNotEquals(data3, metaData);
    }

    @Test
    public void testIsEqualsNull() {
        Tag tag = new Tag("test");
        assertNotEquals(metaData, tag);
    }

    @Test
    public void testIsEquals() {
        MetaData newMetaData = new MetaData(metaData.getNameOfObject());
        assertEquals(metaData, newMetaData);
    }

    @Test
    public void testIsEqualsSameObject() {
        assertEquals(metaData, metaData);
    }


    @Test
    public void testHashCode() {
        Map<MetaData, String> testHashMap = new HashMap<>();
        testHashMap.put(metaData, "test");
        MetaData data2 = new MetaData("List");
        assertEquals("test", testHashMap.get(data2));
        MetaData data3 = new MetaData("list");
        assertNotEquals("test", testHashMap.get(data3));
    }

}
