package model;

import java.util.List;
import java.util.ArrayList;

// Represents a ski resort, with name, trails, lifts, restaurants, areas, and region
public class Resort {

    // EFFECTS: creates a new instance of the resort class, with name set to
    // resortName, region set to region, and no trails, lifts, restaurants, or areas
    public Resort() {
        // stub
    }

    public String getName() {
        return ""; // stub
    }

    public void setName() {
        //stub
    }

    public String getRegion() {
        return ""; // stub
    }

    public void setRegion() {
        // stub
    }

    public List<Trail> getTrails() {
        return new ArrayList<Trail>(); // stub
    }

    public List<Trail> getTrails(String filter) {
        return new ArrayList<Trail>(); //stub
    }

    public List<ResortItem> getLifts() {
        return new ArrayList<ResortItem>(); // stub
    }

    public List<ResortItem> getApres() { // restaurants and shops
        return new ArrayList<ResortItem>(); // stub
    }

    public List<ResortItem> getAreas() {
        return new ArrayList<ResortItem>(); // stub
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
     * into a random trailing run until you get to the bottom */
    public List<Trail> generateRun(Trail t) {
        return new ArrayList<Trail>(); //stub
    }
}
