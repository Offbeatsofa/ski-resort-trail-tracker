package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;

public class TrailTest {

    Trail testTrail;
    Trail testTrail2;
    List<String> testNotes;
    List<Trail> trailList;

    @BeforeEach
    public void runBefore() {
        testTrail = new Trail("test", "Black", "Peak", "Cliffs");
        testNotes = new ArrayList<String>();
    }

    @Test
    public void testConstructor() {
        assertEquals("test", testTrail.getName());
        assertEquals("Green", testTrail.getDifficulty());
        assertEquals("Peak", testTrail.getLocation());
        assertEquals("Moguls", testTrail.getCharacteristics());
        assertFalse(testTrail.isRidden());
        assertFalse(testTrail.isOpen());
        assertFalse(testTrail.isFavorite());
        assertTrue(testTrail.getNotes().isEmpty());
        assertTrue(testTrail.getBranchingTrails().isEmpty());
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
            assertFalse(true);
        } catch (Exception e) {  // TODO specify exception, look at testing abstractions
            assertTrue(true);
        }
    }

    @Test
    public void testFindNoteOOB() {
        try {
            testTrail.findNote(100);
            assertFalse(true);
        } catch (Exception e) {
            assertTrue(true);
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
            assertFalse(true);
        } catch (Exception e) {
            assertTrue(true);
        }        
    }

    @Test
    public void testEditNoteOOB() {
        testTrail.addNote("index 0");
        try {
            testTrail.editNote(5, ":( not enough notes");
            assertFalse(true);
        } catch (Exception e) {
            assertTrue(true);
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
    public void testAddBranchingTrail() {
        testTrail2 = new Trail("test2", "Green", "Base", "Groomed");
        trailList = new ArrayList<Trail>();
        trailList.add(testTrail2);
        testTrail.addBranchingTrail(testTrail2);
        assertEquals(trailList, testTrail.getBranchingTrails());
    }
}
