package persistence;

import exceptions.DataExistAlreadyException;
import exceptions.EmptyStringException;
import exceptions.ItemNotFoundException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestWriter {
    private static final String TEST_TAG_FILE = "./data/testWriteTagFile.json";
    private static final String TEST_LIST_FILE = "./data/testWriteListFile.json";
    private static final String TEST_ITEM_FILE = "./data/testWriteItemFile.json";
    private Writer testWriter;
    private ListManager manager;

    @BeforeEach
    void runBefore() throws IOException {
        testWriter = new Writer(new File(TEST_LIST_FILE), new File(TEST_TAG_FILE), new File(TEST_ITEM_FILE));
        manager = new ListManager();
    }

    @Test
    void testWriteEmptyAll() {
        testWriter.write(manager);
        try {
            ArrayList<MediaList> lists = Reader.readListFile(TEST_LIST_FILE);
            ArrayList<Tag> tags =  Reader.readTagFile(TEST_TAG_FILE);
            ArrayList<ReadUserItem> readUserItems = Reader.readItemFile(TEST_ITEM_FILE);
            assertNull(lists);
            assertNull(tags);
            assertNull(readUserItems);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteLists() {
        try {
            MediaList testList = new MediaList("To watch List");
            MediaList testList2 = new MediaList("Watched List");
            UserMediaItem testItem = new UserMediaItem("Avengers");
            UserMediaItem testItem2 = new UserMediaItem("Endgame");
            Tag testTag = new Tag("action");
            Tag testTag2 = new Tag("adventure");
            manager.addNewList(testList);
            manager.addNewList(testList2);
            manager.addMediaItemToList(testList, testItem);
            manager.addMediaItemToList(testList, testItem2);
            manager.addNewTag(testTag);
            manager.addNewTag(testTag2);
            manager.tagItem(testTag, testItem);
            manager.tagItem(testTag2, testItem2);
            testWriter.write(manager);
            testWriter.close();
            ArrayList<MediaList> lists = Reader.readListFile(TEST_LIST_FILE);
            assertEquals(2, lists.size());
            MediaList listReturned = lists.get(0);
            assertEquals("To watch List", listReturned.getName());
            ArrayList<Tag> tags = Reader.readTagFile(TEST_TAG_FILE);
            assertEquals(2, tags.size());
            Tag tagsReturned = tags.get(0);
            assertEquals("action", tagsReturned.getTagName());
            ArrayList<ReadUserItem> items = Reader.readItemFile(TEST_ITEM_FILE);
            ReadUserItem itemReturned = items.get(0);
            assertEquals(testItem.getItemInfo("Title"), itemReturned.getTitle());
            assertEquals(2, items.size());
        } catch (NullPointerException | IOException |
                EmptyStringException | DataExistAlreadyException | ItemNotFoundException e) {
            fail("Exception should not have been thrown");
        }
    }
}
