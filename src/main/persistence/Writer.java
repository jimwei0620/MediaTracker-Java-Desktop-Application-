package persistence;

import java.io.*;

//A writer that can write list data to a file. Modeled after Teller App
public class Writer {
    private FileWriter file;

    // EFFECTS: constructs a writer that will write data to file
    public Writer(File file) throws IOException {
        this.file = new FileWriter(file);
    }

    // MODIFIES: this
    // EFFECTS: writes SaveAble to file
    public void write(SaveAble saveable) {
        saveable.save(file);
    }

    // MODIFIES: this
    // EFFECTS: close print writer
    // NOTE: you MUST call this method when you are done writing data!
    public void close() throws IOException {
        file.flush();
        file.close();
    }
}
