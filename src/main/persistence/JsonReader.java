package persistence;

import model.Resort;
import model.Trail;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// Code adapted from JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads resort from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Resort read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseResort(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Resort parseResort(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String region = jsonObject.getString("region");
        Resort r = new Resort(name, region);
        addTrails(r, jsonObject);
        return r;
    }

    // MODIFIES: r
    // EFFECTS: parses trails from JSON object and adds them to resort
    private void addTrails(Resort r, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("trails");
        for (Object json : jsonArray) {
            JSONObject nextTrail = (JSONObject) json;
            addTrail(r, nextTrail);
        }
    }

    // MODIFIES: r
    // EFFECTS: parses trail from JSON object and adds it to resort
    private void addTrail(Resort r, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String difficulty = jsonObject.getString("difficulty");
        String location = jsonObject.getString("location");
        String features = jsonObject.getString("features");
        List<String> notes = getNotes(jsonObject);
        Boolean ridden = jsonObject.getBoolean("ridden");
        Boolean open = jsonObject.getBoolean("open");
        Boolean favorite = jsonObject.getBoolean("favorite");
        List<Trail> downhillTrails = getTrails(r, jsonObject);
        Trail trail = new Trail(name, difficulty, location, features);
        
    }

    // EFFECTS: parses jsonarray into list of string
    private List<String> getNotes(JSONObject jsonObject) {
        JSONArray notes = jsonObject.getJSONArray("notes");
        List<String> returnList = new ArrayList<>();
        for (Object note : notes) {
            String nextNote = note.toString();
            returnList.add(nextNote);
        }
        return returnList;
    }   

    // EFFECTS: parses jsonarray into list of string
    private List<Trail> getTrails(Resort r, JSONObject jsonObject) {
        JSONArray trails = jsonObject.getJSONArray("trails");
        List<Trail> returnList = new ArrayList<>();
        for (Object trail : trails) {
            String trailName = trail.toString();
            returnList.add(r.findTrail(trailName));
        }
        return returnList;
    }   
}
