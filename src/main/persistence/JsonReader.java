package persistence;

import model.Resort;
import model.Trail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// Code adapted from JsonSerializationDemo
public class JsonReader {
    
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {

    }

    // EFFECTS: reads resort from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Resort read() throws IOException {
        return null;
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return null;
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Resort parseResort(JSONObject jsonObject) {
        return null;
    }

    // MODIFIES: r
    // EFFECTS: parses trails from JSON object and adds them to resort
    private void addTrails(Resort r, JSONObject jsonObject) {

    }

    // MODIFIES: r
    // EFFECTS: parses trail from JSON object and adds it to resort
    private void addTrail(Resort r, JSONObject jsonObject) {

    }
}
