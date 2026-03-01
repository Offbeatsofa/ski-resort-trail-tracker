package persistence;
import model.Resort;
import model.Trail;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of resort to file
// code adapted from JsonSerializationDemo
public class JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {

    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {

    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of resort to file
    public void write(Resort r) {

    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {

    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
    
    }
}
