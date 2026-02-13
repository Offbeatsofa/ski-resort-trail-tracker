package model;

import java.util.List;
import java.util.ArrayList;

// Represents a ski resort, with name, trails, lifts, restaurants, areas, and region
public class Resort {

    // EFFECTS: creates a new instance of the resort class, with name set to
    // resortName, region set to region, and no trails, lifts, restaurants, or areas
    public Resort(String name, String region) {
        // stub
    }

    public String getName() {
        return null; // stub
    }

    public void setName() {
        //stub
    }

    public String getRegion() {
        return null; // stub
    }

    public void setRegion(String newRegion) {
        // stub
    }

    public List<Trail> getTrails() {
        return null; // stub
    }

    //REQUIRES: filterType is one of "difficulty", "location", or "features"
    //EFFECTS: returns trails that have the same <filterType> as filterValue, or null if none
    public List<Trail> getTrails(String filterType, String filterValue) {
        return null; //stub
    }

    public List<ResortItem> getLifts() {
        return null; // stub
    }

    public List<ResortItem> getApres() { // restaurants and shops
        return null; // stub
    }

    public List<ResortItem> getAreas() {
        return null; // stub
    }

    
    /* REQUIRES: t is not already in resort
     * MODIFIES: this
     * EFFECTS: adds t to list of resort's trails */
    public void addTrail(Trail t) {
        //stub
    }
    
    /* REQUIRES: item is not already in resort
     * MODIFIES: this
     * EFFECTS: adds the given item to the resort */
    public void addItem(ResortItem item) {
        //stub
    }

    /* REQUIRES: t is in resort
     * EFFECTS: generates a list starting from one run that leads 
     * into a random trailing run until the run has no downhill runs */
    public List<Trail> generateRun(Trail t) {
        return null; //stub
    }

    // EFFECTS: returns seed for testing purposes
    public long getSeed() { 
        return 0;
    }
}
