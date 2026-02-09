package model;

import java.util.List;
// import java.util.ArrayList;

// Represent a skiing trail, with name, difficulty, mountain area, 
// notes, ridden status, favorite status, and branching trails
public class Trail {
    
    /* EFFECTS: creates a new trail, with given name, difficulty, location, 
     * and characteristics, and with no notes, unridden, closed, and 
     * unfavorited status, and no branching trails. */
    public Trail() {
        //stub
    }

    public String getName() {
        return null;
    }

    public String getDifficulty() {
        return null;
    }

    public boolean isRidden() {
        return false;
    }

    public boolean isOpen() {
        return false; 
    }

    public boolean isFavorite() {
        return false;
    }

    public List<String> getNotes() {
        return null;
    }

    // MODIFIES: this
    // EFFECTS: changes favorite status and returns new status
    public boolean favorite() {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: changes ridden status and returns new status
    public boolean ride() {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: changes open status and returns new status
    public boolean open() {
        return false;
    }

    // MODIFIES: this
    // EFFECTS: adds the given string as a new note
    public void addNote() {
        // stub
    }

    // REQUIRES: given index is within the range of notes
    // EFFECTS: returns the note at the given index
    public String findNote() {
        return null;
    }

    // REQUIRES: given index is within the range of notes
    // MODIFIES: this
    // EFFECTS: changes the note at the given index to the given string
    public void editNote() {
        // stub
    }

    // REQUIRES: trail is not already in list
    // MODIFIES: this
    // EFFECTS: adds trail to list of trails
    public void addBranchingTrail() {
        // stub
    }
}
