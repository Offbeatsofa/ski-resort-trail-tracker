package model;

import java.util.List;

import model.Exceptions.DuplicateTrailException;
import model.Exceptions.TrailLoopException;

import java.util.ArrayList;

// Represent a skiing trail, with name, difficulty, mountain area, 
// notes, ridden status, favorite status, and branching trails
public class Trail {

    private String name;
    private String difficulty;
    private String location;
    private String features; 
    private List<String> notes;
    private boolean ridden;
    private boolean open;
    private boolean favorite;
    private List<Trail> downhillTrails;
    
    /* EFFECTS: creates a new trail, with given name, difficulty, location, 
     * and characteristics, and with no notes, unridden, closed, and 
     * unfavorited status, and no branching trails. */
    public Trail(String name, String difficulty, String location, String features) {
        this.name = name;
        this.difficulty = difficulty;
        this.location = location;
        this.features = features;
        this.notes = new ArrayList<String>();
        this.ridden = false;
        this.open = false;
        this.favorite = false;
        this.downhillTrails = new ArrayList<Trail>();
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getLocation() {
        return location;
    }

    public String getFeatures() {
        return features;
    }

    public boolean isRidden() {
        return ridden;
    }

    public boolean isOpen() {
        return open; 
    }

    public boolean isFavorite() {
        return favorite;
    }

    public List<String> getNotes() {
        return notes;
    }

    public List<Trail> getDownhillTrails() {
        return downhillTrails;
    }

    // MODIFIES: this
    // EFFECTS: changes favorite status and returns new status
    public boolean favorite() {
        favorite = !favorite;
        return favorite;
    }

    // MODIFIES: this
    // EFFECTS: changes ridden status and returns new status
    public boolean ride() {
        ridden = !ridden;
        return ridden;
    }

    // MODIFIES: this
    // EFFECTS: changes open status and returns new status
    public boolean open() {
        open = !open;
        return open;
    }

    // MODIFIES: this
    // EFFECTS: adds the given string as a new note
    public void addNote(String note) {
        notes.add(note);
    }

    // REQUIRES: given index is within the range of notes, and notes are not empty
    // EFFECTS: returns the note at the given index
    public String findNote(int index) throws IndexOutOfBoundsException {
        return notes.get(index);
    }

    // REQUIRES: given index is within the range of notes, and notes are not empty
    // MODIFIES: this
    // EFFECTS: changes the note at the given index to the given string
    public void editNote(int index, String newNote) throws IndexOutOfBoundsException {
        notes.set(index, newNote);
    }

    // REQUIRES: trail is not already in list, added trail doesn't loop back to this one
    // MODIFIES: this
    // EFFECTS: adds trail to list of trails
    public void addDownhillTrail(Trail t) throws DuplicateTrailException, TrailLoopException {
        if (!downhillTrails.contains(t) && !t.getDownhillTrails().contains(this)) {
            downhillTrails.add(t);
        } else if (t.getDownhillTrails().contains(this)) {
            throw new TrailLoopException();
        } else {
            throw new DuplicateTrailException();
        } // TODO double check implementation is complete, commit
    }
}
