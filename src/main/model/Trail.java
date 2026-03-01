package model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;
import model.exceptions.DuplicateTrailException;
import model.exceptions.TrailLoopException;
import persistence.Writable;

import java.util.ArrayList;

// Represent a skiing trail, with name, difficulty, mountain area, 
// notes, ridden status, favorite status, and branching trails
public class Trail implements Writable {

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
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("difficulty", difficulty);
        json.put("location", location);
        json.put("features", features);
        json.put("notes", notesToJson(notes));
        json.put("ridden", ridden);
        json.put("open", open);
        json.put("favorite", favorite);
        json.put("downhillTrails", downhillToJson(downhillTrails));
        return json;
    }

    private JSONArray notesToJson(List<String> notes) {
        JSONArray array = new JSONArray();
        for (String s : notes) {
            array.put(s);
        }
        return array;
    }

    private JSONArray downhillToJson(List<Trail> trails) {
        JSONArray array = new JSONArray();
        for (Trail t : trails) {
            array.put(t.getName());
        }
        return array;
    }

    @Override
    @ExcludeFromJacocoGeneratedReport
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((features == null) ? 0 : features.hashCode());
        result = prime * result + ((notes == null) ? 0 : notes.hashCode());
        result = prime * result + (ridden ? 1231 : 1237);
        result = prime * result + (open ? 1231 : 1237);
        result = prime * result + (favorite ? 1231 : 1237);
        result = prime * result + ((downhillTrails == null) ? 0 : downhillTrails.hashCode());
        return result;
    }

    @Override
    @ExcludeFromJacocoGeneratedReport
    @SuppressWarnings("methodlength")
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
        Trail other = (Trail) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (difficulty == null) {
            if (other.difficulty != null) {
                return false;
            }
        } else if (!difficulty.equals(other.difficulty)) {
            return false;
        }
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        if (features == null) {
            if (other.features != null) {
                return false;
            }
        } else if (!features.equals(other.features)) {
            return false;
        }
        if (notes == null) {
            if (other.notes != null) {
                return false;
            }
        } else if (!notes.equals(other.notes)) {
            return false;
        }
        if (ridden != other.ridden) {
            return false;
        }
        if (open != other.open) {
            return false;
        }
        if (favorite != other.favorite) {
            return false;
        }
        if (downhillTrails == null) {
            if (other.downhillTrails != null) {
                return false;
            }
        } else if (!downhillTrails.equals(other.downhillTrails)) {
            return false;
        }
        return true;
    }
}
