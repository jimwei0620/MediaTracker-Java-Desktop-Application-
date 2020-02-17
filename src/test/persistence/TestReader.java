package persistence;


import model.MediaItem;
import model.MediaList;
import model.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestReader {
    private static final String TEST_TAG_FILE = "./data/testReadTagFile.json";
    private static final String TEST_LIST_FILE = "./data/testReadListFile.json";
    private static final String TEST_ITEM_FILE = "./data/testReadItemFile.json";

    @Test
    void testReadTestListFile() {
        try {
            ArrayList<MediaList> lists = Reader.readListFile(TEST_LIST_FILE);
            assertEquals(2, lists.size());
            MediaList listReturned = lists.get(1);
            assertEquals("bruh", listReturned.getName());
            ArrayList<Tag> tags = Reader.readTagFile(TEST_TAG_FILE);
            assertEquals(2, tags.size());
            Tag tagsReturned = tags.get(1);
            assertEquals("adventure", tagsReturned.getTagName());
            ArrayList<ReadUserItem> items = Reader.readItemFile(TEST_ITEM_FILE);
            ReadUserItem itemReturned = items.get(1);
            assertEquals("duh", itemReturned.getTitle());
            assertEquals(2, items.size());
        } catch (IOException | NullPointerException e) {
            fail("Should not have thrown IOException");
        }
    }


    @Test
    void testListFileNotFound() {
        try {
            ArrayList<MediaList> listRead = Reader.readListFile("./data/donotexist.json");
            File file = new File("./data/donotexist.json");
            assertTrue(file.delete());
        } catch (IOException e) {
            fail("Should not have found a exception. Exception should've been caught");
        }
    }

    @Test
    void testItemFileNotFound() {
        try {
            ArrayList<ReadUserItem> itemRead = Reader.readItemFile("./data/donotexist.json");
            File file = new File("./data/donotexist.json");
            assertTrue(file.delete());
        } catch (IOException e) {
            fail("Should not have found a exception. Exception should've been caught");
        }
    }

    @Test
    void testTagFileNotFound() {
        try {
            ArrayList<Tag> tagRead = Reader.readTagFile("./data/donotexist.json");
            File file = new File("./data/donotexist.json");
            assertTrue(file.delete());
        } catch (IOException e) {
            fail("Should not have found a exception. Exception should've been caught");
        }
    }

    @Test
    void testIOException() {
        try {
            ArrayList<MediaList> listRead = Reader.readListFile("./data/do/not/exist/path/why.json");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testCreateFile() {
        try {
            Reader.createFile("./data/do/not/exist.json");
        } catch (IOException e) {
            // expected
        }
    }
}
