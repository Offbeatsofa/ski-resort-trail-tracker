package persistence;

import model.Trail;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
public class JsonTest {
    protected void checkTrail(String name, String difficulty, String location, String features, List<String> notes,
            Boolean ridden, Boolean open, Boolean favorite, List<Trail> downhillTrails, Trail t) {
        Trail trail = new Trail(name, difficulty, location, features);
        for (String note : notes) {
            trail.addNote(note);
        }
        for (Trail d : downhillTrails) {
            try {
                trail.addDownhillTrail(d);
            } catch (Exception e) {
                fail("No exception expected");
            }
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
        assertEquals(trail, t);
    }   
}
