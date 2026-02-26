package persistence;

import model.Resort;
import model.Trail;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
// Code adapted from JsonSerializationDemo
@ExcludeFromJacocoGeneratedReport
public class JsonReaderTest extends JsonTest{

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyResort() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyResort.json");
        try {
            Resort r = reader.read();
            assertEquals("Empty resort", r.getName());
            assertEquals("none", r.getRegion());
            assertTrue(r.getTrails().isEmpty());
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }
    
    @Test
    public void testReaderGeneralResort() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralResort.json");
        try {
            Resort r = reader.read();
            assertEquals("Resort", r.getName());
            assertEquals("Region", r.getRegion());
            List<Trail> trails = r.getTrails();
            assertEquals(2, trails.size());
            List<String> notes = new ArrayList<>();
            List<Trail> downhill = new ArrayList<>();
            notes.add("note");
            downhill.add(r.getTrails().get(1));
            checkTrail("name", "difficulty", "location", "features", notes, true, true, true, new ArrayList<Trail>(), trails.get(0));
            checkTrail("name2", "difficulty2", "location2", "features2", new ArrayList<>(), false, false, false, downhill, trails.get(1));
        }   catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
