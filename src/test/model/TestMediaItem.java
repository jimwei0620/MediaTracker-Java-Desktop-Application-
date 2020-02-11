package model;

import exceptions.EmptyStringException;
import exceptions.InvalidRatingException;
import exceptions.NullRatingException;
import exceptions.NullTypeException;
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
    public void testMediaItemInit() {
        try {
            MediaItem newItem = new MediaItem("");
        } catch (EmptyStringException e) {
            //expected
        }
    }

    @Test
    public void testConstruct() {
        assertEquals("Avengers", item1.getName());
        assertEquals("NOT WATCHED" ,item1.getWatchStatus());
    }

    @Test
    public void testSetName() {
        try {
            item1.setName("Endgame");
            assertEquals("Endgame", item1.getName());
        } catch (EmptyStringException e) {
            fail("Should not have thrown EmptyStringException");
        }
    }

    @Test
    public void testSetEmptyName() {
        try {
            item1.setName("");
        } catch (EmptyStringException e){
            //expected
        }
    }

    @Test
    public void testSetWatchStatus() {
        try {
            item1.setWatchStatus("WATCHED");
            assertEquals("WATCHED" ,item1.getWatchStatus());
        } catch (EmptyStringException e) {
            fail("Should not have thrown EmptyStringException");
        }
    }

    @Test
    public void testSetEmptyWatchStatus() {
        try {
            item1.setWatchStatus("");
        } catch (EmptyStringException e){
            //expected
        }
    }

    @Test
    public void testSetGetRating() {
        try {
            item1.setRating(1f);
            assertEquals(1f, item1.getRating());
        } catch (InvalidRatingException | NullPointerException e) {
            fail("Exceptions should not have been thrown.");
        }
    }

    @Test
    public void testGetNullRating() {
        try {
            item1.getRating();
        } catch (NullPointerException e) {
            //expected
        }
    }

    @Test
    public void testGetNullType() {
        try {
            item1.getType();
        } catch (NullPointerException e) {
            //expected
        }
    }
    @Test
    public void testInvalidRating() {
        try {
            item1.setRating(-1f);
        } catch (InvalidRatingException e) {
            // expected
        }
    }

    @Test
    public void testSetGetType() {
        item1.setType("Movie");
        try {
            assertEquals("Movie", item1.getType());
        } catch (NullPointerException e) {
            fail("Should not have thrown NullTypeException");
        }
    }

    @Test
    public void testSetGetComment() {
        item1.setComment("Example Comment");
        assertEquals("Example Comment", item1.getComment());
    }
}
