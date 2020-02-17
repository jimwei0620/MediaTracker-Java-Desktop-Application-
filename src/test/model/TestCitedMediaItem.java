package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TestCitedMediaItem {
    private CitedMediaItem citedMediaItem;

    @BeforeEach
    public void runBefore() {
        citedMediaItem = new CitedMediaItem();
    }

    @Test
    public void testConstruct() {
        Map<String, String> itemDetails = citedMediaItem.itemDetails;
        assertTrue(itemDetails.containsKey("Title"));
        assertTrue(itemDetails.containsKey("Year"));
        assertTrue(itemDetails.containsKey("Rated"));
        assertTrue(itemDetails.containsKey("Runtime"));
        assertTrue(itemDetails.containsKey("Genre"));
        assertTrue(itemDetails.containsKey("Title"));
        assertTrue(itemDetails.containsKey("Released"));
        assertTrue(itemDetails.containsKey("Plot"));
        assertTrue(itemDetails.containsKey("Poster"));
        assertTrue(itemDetails.containsKey("Rating"));
        assertTrue(itemDetails.containsKey("Votes"));
        assertTrue(itemDetails.containsKey("Type"));
        assertTrue(itemDetails.containsKey("Status"));
        assertTrue(itemDetails.containsKey("UserRating"));
        assertTrue(itemDetails.containsKey("UserComments"));
        assertEquals("", itemDetails.get("Title"));
    }

    @Test //will develop after implementing getting info from online sources
    public void testSave() {
        assertNull(citedMediaItem.save());
    }
}
