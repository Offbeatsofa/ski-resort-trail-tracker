package persistence;

import model.Resort;
import model.Trail;
import org.junit.jupiter.api.Test;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// code adapted from JsonSerializationDemo
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testWriterEmptyResort() {
        try {
            Resort r = new Resort("Empty Resort", "none");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(r);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            r = reader.read();
            assertEquals("EmptyResort", r.getName());
            assertEquals("none", r.getRegion());
            assertTrue(r.getTrails().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralResort() {
        try {
            Resort r = new Resort("Resort", "Region");
            Trail name = new Trail("name", "difficulty", "location", "features");
            name.addNote("note");
            name.ride();
            name.open();
            name.favorite();
            Trail name2 = new Trail("name2", "difficulty2", "location2", "features2");
            try {
                name2.addDownhillTrail(name);
            } catch (Exception e) {
                fail("Downhill trail add failed");
            }
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(r);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            Resort result = reader.read();
            assertEquals(r, result);
        } catch (IOException e) {
            fail("should not have thrown exception");
        }
    }
}
