package persistence;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.MediaList;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;


// A reader that can read list data from file. Modeled after Teller App
public class Reader {

    private Reader() {}

    // EFFECTS: returns a list of General data read from file;
    // throws IOException if an exception is raised when opening / reading from file
    // Modeled from https://futurestud.io/tutorials/gson-mapping-of-arrays-and-lists-of-objects
    public static ArrayList<MediaList> readFile(String filePath) throws IOException {
        Gson gson = new Gson();
        ArrayList<MediaList> infoRead = null;
        Type mediaListType = new TypeToken<ArrayList<MediaList>>() {
        }.getType();
        try {
            System.out.println("Reading from a file");
            System.out.println("----------------------------");
            FileReader fileReader = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fileReader);

            //convert the json string back to object
            infoRead = gson.fromJson(br, mediaListType);
        } catch (FileNotFoundException e) {
            System.out.println("There are no data files!");
            File file = new File(filePath);
            file.createNewFile();
            System.out.println("Created new file");
        }
        return infoRead;
    }

}
