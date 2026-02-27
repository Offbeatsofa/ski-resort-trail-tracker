package persistence;

import model.Trail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class JsonTest {
    protected void checkTrail(String name, String difficulty, String location, String features, List<String> notes,
            Boolean ridden, Boolean open, Boolean favorite, List<Trail> downhillTrails, Trail t) {
        assertEquals(name, t.getName());
        assertEquals(difficulty, t.getDifficulty());
        assertEquals(location, t.getLocation());
        assertEquals(features, t.getFeatures());
        assertEquals(notes, t.getNotes());
        assertEquals(ridden, t.isRidden());
        assertEquals(open, t.isOpen());
        assertEquals(favorite, t.isFavorite());
        assertEquals(downhillTrails, t.getDownhillTrails());
    }   
}
