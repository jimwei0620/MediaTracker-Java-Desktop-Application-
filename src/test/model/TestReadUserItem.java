package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestReadUserItem {
    private ReadUserItem readUserItem;

    @BeforeEach
    void runBefore() {
        readUserItem = new ReadUserItem();
    }

    @Test
    void testGetters() {
        assertEquals(0, readUserItem.getMetaDataList().size());
        assertEquals(0, readUserItem.getMetaDataTag().size());
        assertEquals("testTitle", readUserItem.getTitle());
        assertEquals("testType", readUserItem.getType());
        assertEquals("testUserComments", readUserItem.getUserComments());
        assertEquals("testUserRating", readUserItem.getUserRating());
        assertEquals("testStatus", readUserItem.getStatus());
    }
}
