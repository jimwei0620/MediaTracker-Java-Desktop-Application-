package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestMediaItem {
    private MediaItem item1;

    @BeforeEach
    public void testMediaItem() {
        item1 = new MediaItem("Avengers", false);
    }

    @Test
    public void testConstruct() {
        assertEquals(item1.getName(), "Avengers");
        assertFalse(item1.getWatchStatus());
    }

    @Test
    public void testSetName() {
        item1.setName("Endgame");
        assertEquals(item1.getName(), "Endgame");
    }

    @Test
    public void testSetWatchStatus() {
        item1.setWatchStatus(true);
        assertTrue(item1.getWatchStatus());
    }
}
