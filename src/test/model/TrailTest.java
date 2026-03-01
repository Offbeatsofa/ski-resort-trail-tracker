package model;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import model.exceptions.DuplicateTrailException;
import model.exceptions.TrailLoopException;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class TrailTest {

    Trail testTrail;
    Trail testTrail2;
    List<String> testNotes;
    List<Trail> trailList;

    @BeforeEach
    public void runBefore() {
        testTrail = new Trail("test", "Black", "Peak", "Cliffs");
        testTrail2 = new Trail("test2", "Green", "Base", "Groomed");
        trailList = new ArrayList<>();
        testNotes = new ArrayList<String>();
    }

    @Test
    public void testConstructor() {
        assertEquals("test", testTrail.getName());
        assertEquals("Black", testTrail.getDifficulty());
        assertEquals("Peak", testTrail.getLocation());
        assertEquals("Cliffs", testTrail.getFeatures());
        assertFalse(testTrail.isRidden());
        assertFalse(testTrail.isOpen());
        assertFalse(testTrail.isFavorite());
        assertTrue(testTrail.getNotes().isEmpty());
        assertTrue(testTrail.getDownhillTrails().isEmpty());
    }

    @Test
    public void testFavoriteTrue() {
        assertTrue(testTrail.favorite());
        assertTrue(testTrail.isFavorite());
    }

    @Test
    public void testFavoriteFalse() {
        testTrail.favorite();
        assertFalse(testTrail.favorite());
        assertFalse(testTrail.isFavorite());
    }

    @Test
    public void testRideTrue() {
        assertTrue(testTrail.ride());
        assertTrue(testTrail.isRidden());
    }

    @Test
    public void testRideFalse() {
        testTrail.ride();
        assertFalse(testTrail.ride());
        assertFalse(testTrail.isRidden());
    }

    @Test
    public void testOpenTrue() {
        assertTrue(testTrail.open());
        assertTrue(testTrail.isOpen());
    }

    @Test
    public void testOpenFalse() {
        testTrail.open();
        assertFalse(testTrail.open());
        assertFalse(testTrail.isOpen());
    }

    @Test
    public void testAddNote() {
        testTrail.addNote("test note!");
        testNotes.add("test note!");
        assertEquals(testTrail.getNotes(), testNotes);
    }

    @Test
    public void testFindNoteEmpty() {
        try {
            testTrail.findNote(0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            // pass
        }
    }

    @Test
    public void testFindNoteOOB() {
        try {
            testTrail.findNote(100);
            fail();
        } catch (Exception e) {
            // pass
        }
    }

    @Test
    public void testFindNoteZero() {
        testTrail.addNote("test note");
        assertEquals("test note", testTrail.findNote(0));
    }

    @Test
    public void testFindNoteOne() {
        testTrail.addNote("NOT test note");
        testTrail.addNote("test note");
        assertEquals("test note", testTrail.findNote(1));
    }

    @Test
    public void testEditNoteEmpty() {
        try {
            testTrail.editNote(0, ":( no notes");
            fail();
        } catch (Exception e) {
            // pass
        }        
    }

    @Test
    public void testEditNoteOOB() {
        testTrail.addNote("index 0");
        try {
            testTrail.editNote(5, ":( not enough notes");
            fail();
        } catch (Exception e) { 
            // pass
        }     
    }

    @Test
    public void testEditNoteZero() {
        testTrail.addNote("old note 0");
        testTrail.editNote(0, "new note 0");
        assertEquals("new note 0", testTrail.findNote(0));
    }

    @Test
    public void testEditNoteOne() {
        testTrail.addNote("old note 0");
        testTrail.addNote("old note 1");
        testTrail.editNote(1, "new note 1");
        assertEquals("old note 0", testTrail.findNote(0));
        assertEquals("new note 1", testTrail.findNote(1));
    }

    @Test
    public void testAddDownhillTrailSucceed() {     
        trailList.add(testTrail2);
        try {
            testTrail.addDownhillTrail(testTrail2);
        } catch (Exception e) {
            fail();
        }
        assertEquals(trailList, testTrail.getDownhillTrails());
    }

    @Test
    public void testAddDownhillTrailLoop() {
        try {
            testTrail2.addDownhillTrail(testTrail);
        } catch (Exception e) { 
            fail();
        }
        try {
            testTrail.addDownhillTrail(testTrail2);
            fail();
        } catch (TrailLoopException e) {
            // pass
        } catch (DuplicateTrailException e) {
            fail();
        }
    }

    @Test
    public void testAddDownhillTrailContains() {
        try {
            testTrail.addDownhillTrail(testTrail2);
        } catch (Exception e) { 
            fail();
        }
        try {
            testTrail.addDownhillTrail(testTrail2);
            fail();
        } catch (TrailLoopException e) {
            fail();
        } catch (DuplicateTrailException e) {
            // pass
        }
    }

    @Test
    public void testToJson() {
        JSONObject trailJson = testTrail.toJson();
        assertEquals("test", trailJson.get("name"));
        assertEquals("Black", trailJson.get("difficulty"));
        assertEquals("Peak", trailJson.get("location"));
        assertEquals("Cliffs", trailJson.get("features"));
        assertFalse((Boolean) trailJson.get("ridden"));
        assertFalse((Boolean) trailJson.get("open"));
        assertFalse((Boolean) trailJson.get("favorite"));
        assertTrue(trailJson.getJSONArray("notes").isEmpty());
        assertTrue(trailJson.getJSONArray("downhillTrails").isEmpty());
    }
}
