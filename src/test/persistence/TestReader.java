package persistence;


import model.MediaItem;
import model.MediaList;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestReader {
    @Test
    void testReadTestListFile() {
        try {
            ArrayList<MediaList> listsRead = Reader.readFile("./data/ReadList.json");
            assertEquals(3, listsRead.size());

            MediaList listReturned1 = listsRead.get(0);
            MediaItem itemReturned1 = listReturned1.getList().get(0);
            assertEquals("To watch List", listReturned1.getName());
            assertEquals(1, listReturned1.getList().size());
            assertEquals("Avengers", itemReturned1.getName());
            assertEquals("NOT WATCHED", itemReturned1.getWatchStatus());

            MediaList listReturned2 = listsRead.get(1);
            MediaItem itemReturned2 = listReturned2.getList().get(0);
            assertEquals("Watched", listReturned2.getName());
            assertEquals(1, listReturned2.getList().size());
            assertEquals("Endgame", itemReturned2.getName());
            assertEquals("WATCHED", itemReturned2.getWatchStatus());

            MediaList listReturned3 = listsRead.get(2);
            MediaItem itemReturned3 = listReturned3.getList().get(0);
            assertEquals("Watching", listReturned3.getName());
            assertEquals(1, listReturned3.getList().size());
            assertEquals("Batman", itemReturned3.getName());
            assertEquals("IN PROGRESS", itemReturned3.getWatchStatus());


        } catch (IOException e) {
            fail("Should not have thrown IOException");
        }
    }


    @Test
    void testFileNotFound() throws IOException {
        try {
            ArrayList<MediaList> listRead = Reader.readFile("./data/donotexist.json");
            File file = new File("./data/donotexist.json");
            assertTrue(file.delete());
        } catch (IOException e) {
            fail("Should not have found a exception. Exception should've been caught");
        }
    }

    @Test
    void testIOException() throws IOException {
        try {
            ArrayList<MediaList> listRead = Reader.readFile("./data/do/not/exist/path/why.json");
            fail("Should've caught exception");
        } catch (IOException e) {
            // expected
        }
    }
}
