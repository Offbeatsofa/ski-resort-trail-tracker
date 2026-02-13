package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class ResortTest {

    Resort testResort;
    Trail testTrail;
    Trail testTrail2;
    Trail downhillTrail1;
    Trail downhillTrail2;
    List<Trail> testList;
    ResortItem item;
    Random rnd;

    @BeforeEach
    public void runBefore() {
        testResort = new Resort("test", "rockies");
        testTrail = new Trail("test1", "green", "lodge", "sidehits");
        testTrail2 = new Trail("test2", "green", "peak", "couloir");
        downhillTrail1 = new Trail ("", "", "", "");
        downhillTrail2 = new Trail ("", "", "", "");
        testList = new ArrayList<>();
        rnd = new Random(testResort.getSeed());
    }

    @Test
    public void testConstructor() {
        assertEquals("test", testResort.getName());
        assertEquals("rockies", testResort.getRegion());
        assertTrue(testResort.getTrails().isEmpty());
        assertTrue(testResort.getLifts().isEmpty());
        assertTrue(testResort.getApres().isEmpty());
        assertTrue(testResort.getAreas().isEmpty());
    }

    @Test
    public void testGetTrailsFilterBoth() {
        testList.add(testTrail);
        testList.add(testTrail2);
        assertEquals(testList, testResort.getTrails("difficulty", "green"));
    }

    @Test
    public void testGetTrailsFilterLocation() {
        testList.add(testTrail);
        assertEquals(testList, testResort.getTrails("location", "lodge"));
    }

    @Test
    public void testGetTrailsFilterFeatures() {
        testList.add(testTrail2);
        assertEquals(testList, testResort.getTrails("features", "couloir"));
    }

    @Test
    public void testGetTrailsFilterNone() {
        assertNull(testResort.getTrails("difficulty", "black"));
    }

    @Test
    public void testAddTrail() {
        testList.add(testTrail);
        testResort.addTrail(testTrail);
        assertEquals(testList, testResort.getTrails());
    }

    @Test
    public void testAddTrailContains() {
        testList.add(testTrail);
        testResort.addTrail(testTrail);
        testResort.addTrail(testTrail);
        assertEquals(testList, testResort.getTrails());
    }

    @Test
    public void testAddItem() {
        fail(); // TODO specify resortItem subclasses
    }

    @Test
    public void testAddItemContains() {
        fail();
    }

    @Test
    public void testGenerateRunEmpty() { 
        testList.add(testTrail);
        assertEquals(testList, testResort.generateRun(testTrail));
    }

    @Test
    public void testGenerateRunOneOption() { 
        try {
            testTrail.addDownhillTrail(downhillTrail1);
        } catch (Exception e) {
            fail();
        }
        testList.add(testTrail);
        testList.add(downhillTrail1);
        assertEquals(testList, testResort.generateRun(testTrail));
    }

    @Test
    public void testGenerateRunMultiple() {
        try {
            testTrail.addDownhillTrail(downhillTrail1);
            testTrail.addDownhillTrail(downhillTrail2);
        } catch (Exception e) {
            fail();
        }
        testList.add(testTrail);
        if (rnd.nextInt() % 2 == 0) {
            testList.add(downhillTrail1);
            assertEquals(testList, testResort.generateRun(testTrail));
        } else {
            testList.add(downhillTrail1);
            assertEquals(testList, testResort.generateRun(testTrail));
        }
    }

    @Test
    public void testGenerateRunMultipleDownhill() {
        try {
            testTrail.addDownhillTrail(testTrail2);
            testTrail2.addDownhillTrail(downhillTrail1);
            downhillTrail1.addDownhillTrail(downhillTrail2);
        } catch (Exception e) {
            fail();
        }
        testList.add(testTrail);
        testList.add(testTrail2);
        testList.add(downhillTrail1);
        testList.add(downhillTrail2);
        assertEquals(testList, testResort.generateRun(testTrail));
    }
}
