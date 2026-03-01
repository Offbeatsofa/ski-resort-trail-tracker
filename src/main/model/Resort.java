package model;

import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.exceptions.BadFilterException;

import java.util.ArrayList;

// Represents a ski resort, with name, trails, and region
public class Resort {

    private String name;
    private String region;
    private List<Trail> trails;
    private Random rnd;
    private int rndSeed;

    // EFFECTS: creates a new instance of the resort class, with name set to
    // resortName, region set to region, and no trails, lifts, restaurants, or areas
    public Resort(String name, String region) {
        this.name = name;
        this.region = region;
        this.trails = new ArrayList<>();
        rnd = new Random();
        rndSeed = rnd.nextInt();
        rnd.setSeed(rndSeed);
    }

    public String getName() {
        return name; // stub
    }

    public String getRegion() {
        return region; // stub
    }

    public List<Trail> getTrails() {
        return trails; // stub
    }

    //REQUIRES: filterType is one of "difficulty", "location", or "features"
    //EFFECTS: returns trails that have the same <filterType> as filterValue, or null if none
    public List<Trail> getTrails(String filterType, String filterValue) throws BadFilterException {
        List<Trail> returnList = new ArrayList<>();
        if (filterType.equals("difficulty")) {
            for (Trail t : trails) {
                if (t.getDifficulty().equals(filterValue)) {
                    returnList.add(t);
                }
            }
        } else if (filterType.equals("location")) {
            for (Trail t : trails) {
                if (t.getLocation().equals(filterValue)) {
                    returnList.add(t);
                }
            }
        } else if (filterType.equals("features")) {
            for (Trail t : trails) {
                if (t.getFeatures().equals(filterValue)) {
                    returnList.add(t);
                }
            }
        } else {
            throw new BadFilterException();
        }
        return returnList.isEmpty() ? null : returnList;
    }
    
    /* REQUIRES: t is not already in resort
     * MODIFIES: this
     * EFFECTS: adds t to list of resort's trails */
    public void addTrail(Trail t) {
        if (!trails.contains(t)) {
            trails.add(t);
        }
    }

    /* REQUIRES: t is in resort
     * EFFECTS: generates a list starting from one run that leads 
     * into a random trailing run until the run has no downhill runs */
    public List<Trail> generateRun(Trail t) {
        List<Trail> run = new ArrayList<Trail>();
        run.add(t);
        if (t.getDownhillTrails().isEmpty()) {
            return run;
        } else {
            int trailNum = Math.abs(rnd.nextInt() % t.getDownhillTrails().size());
            run.addAll(generateRun(t.getDownhillTrails().get(trailNum)));
            return run;
        }
    }

    // EFFECTS: returns seed for testing purposes
    public long getSeed() { 
        return rndSeed;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("region", region);
        json.put("trails", trailsToJson());
        return json;
    }

    private JSONArray trailsToJson() {
        JSONArray array = new JSONArray();
        for (Trail t : trails) {
            array.put(t.toJson());
        }
        return array;
    }

    @Override
    @ExcludeFromJacocoGeneratedReport
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((region == null) ? 0 : region.hashCode());
        result = prime * result + ((trails == null) ? 0 : trails.hashCode());
        return result;
    }

    @Override
    @ExcludeFromJacocoGeneratedReport
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Resort other = (Resort) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (region == null) {
            if (other.region != null) {
                return false;
            }
        } else if (!region.equals(other.region)) {
            return false;
        }
        if (trails == null) {
            if (other.trails != null) {
                return false;
            }
        } else if (!trails.equals(other.trails)) {
            return false;
        }
        return true;
    }

}
