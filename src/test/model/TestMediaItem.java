package model;

import exceptions.EmptyStringException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestMediaItem {
    private MediaItem item1;

    @BeforeEach
    public void testMediaItem() {
        try {
            item1 = new MediaItem("Avengers");
        } catch (EmptyStringException e){
            fail("Should not have ran into EmptyStringException");
        }
    }
    @Test
    public void testConstruct() {
        assertEquals("Avengers", item1.getName());
        assertEquals("NOT WATCHED" ,item1.getWatchStatus());
    }

    @Test
    public void testSetName() {
        item1.setName("Endgame");
        assertEquals("Endgame", item1.getName());
    }

    @Test
    public void testSetWatchStatus() {
        item1.setWatchStatus("WATCHED");
        assertEquals("WATCHED" ,item1.getWatchStatus());
    }
}
