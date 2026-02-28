package persistence;

import model.Resort;
import model.Trail;
import model.exceptions.NoSuchTrailException;

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
    public Resort read() throws IOException, NoSuchTrailException {
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
    private Resort parseResort(JSONObject jsonResort) throws NoSuchTrailException {
        String name = jsonResort.getString("name");
        String region = jsonResort.getString("region");
        Resort r = new Resort(name, region);
        addTrails(r, jsonResort);
        return r;
    }

    // MODIFIES: r
    // EFFECTS: parses trails from JSON object and adds them to resort
    private void addTrails(Resort r, JSONObject jsonResort) throws NoSuchTrailException {
        JSONArray jsonArray = jsonResort.getJSONArray("trails");
        for (Object json : jsonArray) {
            JSONObject nextTrail = (JSONObject) json;
            r.addTrail(getTrail(nextTrail, jsonArray));
        }
    }

    // MODIFIES: r
    // EFFECTS: parses trail from JSON object and creates a trail object
    // TODO ask TA if method length is suppressable
    private Trail getTrail(JSONObject jsonTrail, JSONArray trailArray) throws NoSuchTrailException {
        String name = jsonTrail.getString("name");
        String difficulty = jsonTrail.getString("difficulty");
        String location = jsonTrail.getString("location");
        String features = jsonTrail.getString("features");
        List<String> notes = getList(jsonTrail, "notes");
        Boolean ridden = jsonTrail.getBoolean("ridden");
        Boolean open = jsonTrail.getBoolean("open");
        Boolean favorite = jsonTrail.getBoolean("favorite");
        List<Trail> downhillTrails = getDownhillTrails(jsonTrail, trailArray);
        Trail trail = new Trail(name, difficulty, location, features);
        for (String note : notes) {
            trail.addNote(note);
        }
        for (Trail t : downhillTrails) {
            try {
                trail.addDownhillTrail(t);
            } catch (Exception e) { /* no trail added */ }
        }
        if (ridden) {
            trail.ride();
        }
        if (open)  {
            trail.open(); 
        }
        if (favorite) {
            trail.favorite();
        }
        return trail;
    }

    // EFFECTS: parses jsonarray into list of string
    private List<String> getList(JSONObject jsonObject, String key) {
        JSONArray list = jsonObject.getJSONArray(key);
        List<String> returnList = new ArrayList<>();
        for (Object string : list) {
            String nextItem = string.toString();
            returnList.add(nextItem);
        }
        return returnList;
    }   

    // EFFECTS: returns list of downhill trails in trail object
    private List<Trail> getDownhillTrails(JSONObject jsonTrail, JSONArray resortTrails) throws NoSuchTrailException {
        List<Trail> returnList = new ArrayList<>();
        List<String> trailNames = getList(jsonTrail, "downhillTrails");
        nameLoop:
        for (String trailName : trailNames) {
            for (Object item : resortTrails) {
                JSONObject trail = (JSONObject) item;
                if (trail.getString("name").equals(trailName)) {
                    returnList.add(getTrail(trail, resortTrails));
                    continue nameLoop;
                }
            }
            throw new NoSuchTrailException();
        }
        return returnList;
    }   
}
