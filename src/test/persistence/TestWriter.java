package persistence;

import model.ListManager;
import model.MediaItem;
import model.MediaList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestWriter {
    private static final String TEST_FILE = "./data/testWriteList.json";
    private Writer testWriter;
    private ListManager manager;

    @BeforeEach
    void runBefore() throws IOException {
        testWriter = new Writer(new File(TEST_FILE));
        manager = new ListManager();
    }

    @Test
    void testWriteEmptyLists() {
        testWriter.write(manager);
        try {
            ArrayList<MediaList> lists = Reader.readFile(TEST_FILE);
            assertNull(lists);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriteLists() throws IOException {
        MediaList testList = new MediaList("To watch List");
        MediaItem testItem = new MediaItem("Avengers");
        testList.addMedia(testItem);
        manager.addToColl(testList);
        testWriter.write(manager);
        testWriter.close();
        try {
            ArrayList<MediaList> lists = Reader.readFile(TEST_FILE);
            assertEquals(1, lists.size());
            MediaList listReturned = lists.get(0);
            assertEquals("To watch List", listReturned.getName());
            assertEquals(1, listReturned.getList().size());
            MediaItem itemReturned = listReturned.getList().get(0);
            assertEquals(testItem.getName(), itemReturned.getName());
            assertEquals(testItem.getWatchStatus(), itemReturned.getWatchStatus());
        } catch (NullPointerException e) {
            fail("NullPointerException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
