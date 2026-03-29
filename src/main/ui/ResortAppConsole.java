package ui;

import model.Resort;
import model.Trail;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
// Resort trail tracking application
// Code based on TellerApp lecture lab
public class ResortAppConsole {

    private List<Resort> resorts;
    private Scanner input;
    private int resortIndex;
    private int trailIndex;
    private static final String DATA_PATH = "./data/";

    

    // EFFECTS: runs the resort application
    public ResortAppConsole() {
        resorts = new ArrayList<>();
        resortIndex = 0;
        trailIndex = 0;
        input = new Scanner(System.in);
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runApp() {
        String command = null;

        while (true) {
            displayMainMenu();
            command = input.nextLine();
            command = command.toLowerCase();

            if (command.equals("e")) {
                break;
            } else {
                processMainCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command in main menu
    private void processMainCommand(String command) {
        if (command.equals("a")) {
            doAddResort();
        } else if (command.equals("s")) {
            if (resorts.isEmpty()) {
                System.out.println("No resorts to show!");
            } else {
                doSelectResort();
                resortMenu();
            }
        } else if (command.equals("f")) {
            doSaveResort();
        } else if (command.equals("l")) {
            doLoadResort();
        } else {
            System.out.println("Invalid main command.");
        }
    }
    
    // MODIFIES: this
    // EFFECTS: processes user command in the resort menu
    private void processResortCommand(String command) {    
        if (command.equals("a")) {
            doAddTrail(resorts.get(resortIndex));
        } else if (command.equals("s")) {
            if (!resorts.get(resortIndex).getTrails().isEmpty()) {
                doSelectTrail(resorts.get(resortIndex));
            } else { 
                System.out.println("No trails to show!");
            }
        } else {
            System.out.println("Invalid resort command.");
        }    
    }

    // MODIFIES: this
    // EFFECTS: processes user command in the trail menu
    private void processTrailCommand(String command) {
        Trail t = resorts.get(resortIndex).getTrails().get(trailIndex);
        switch (command) {
            case "f" : 
                System.out.println("Favorite set to " + String.valueOf(t.favorite()));
                break;
            case "r" : 
                System.out.println("Ridden set to " + String.valueOf(t.ride()));
                break;
            case "o" : 
                System.out.println("Open set to " + String.valueOf(t.open()));
                break;
            case "a" : 
                doAddNote();
                break;
            case "n" : 
                doEditNotes();
                break;
            case "d" : 
                doDownhillTrail();
                break;
            default : 
                System.out.println("Invalid trail editing option.");
        }
    }

    // MODIFIES: this, newResort
    // EFFECTS: creates a new resort based on user inputs
    private void doAddResort() {
        System.out.println("Name: \n");
        String name = input.nextLine();
        System.out.println("Region: \n");
        String region = input.nextLine();
        resorts.add(new Resort(name, region));
    }

    // REQUIRES: resorts is not empty
    // EFFECTS: selects an added resort
    private void doSelectResort() {
        showResorts();
        loopUntilCorrect:
        while (true) {
            System.out.println("Select resort: \n");
            String index = input.nextLine();
            for (int i = 0; i < resorts.size(); i++) {
                if (index.equals(String.valueOf(i + 1))) {
                    resortIndex = i;
                    break loopUntilCorrect;
                }
            }
            System.out.println("Invalid resort selection");
        }
    }

    // EFFECTS: displays resort menu and processes user command  
    private void resortMenu() {
        while (true) {
            displayResortMenu();
            String command = input.nextLine();
            command = command.toLowerCase();
            if (command.equals("e")) {
                //runApp();
                break;
            } else {
                processResortCommand(command);
            }
        }
    }

    // EFFECTS: prints out resorts to console
    private void showResorts() {
        for (Resort r : resorts) {
            System.out.println("Resort " + String.valueOf(resorts.indexOf(r) + 1) + ": " + r.getName());
        }
    }

    //MODIFIES: this, newTrail
    //EFFECTS: creates a new trail based on user inputs
    private void doAddTrail(Resort r) {
        System.out.println("Name: \n");
        String name = input.nextLine();
        System.out.println("Difficulty: \n");
        String difficulty = input.nextLine();
        System.out.println("Location: \n");
        String location = input.nextLine();
        System.out.println("Features: \n");
        String features = input.nextLine();
        r.addTrail(new Trail(name, difficulty, location, features));
    }

    //REQUIRES: resort's trails are not empty
    //EFFECTS: selects an added trail
    private void doSelectTrail(Resort r) {
        showTrails(r);
        loopUntilCorrect:
        while (true) {
            System.out.println("Select trail: \n");
            String index = input.nextLine();
            for (int i = 0; i < r.getTrails().size(); i++) {
                if (index.equals(String.valueOf(i + 1))) {
                    trailIndex = i;
                    break loopUntilCorrect;
                }
                
            }
            System.out.println("Invalid trail selection.");            
        }
        trailMenu();
    }
    
    // EFFECTS: displays resort menu and processes user command
    private void trailMenu() {
        while (true) {
            displayTrailMenu();
            System.out.println();
            String command = input.nextLine();
            command = command.toLowerCase();
            if (command.equals("e")) {
                break;
            } else {
                processTrailCommand(command);
            }
        }
    }

    // EFFECTS: prints out a resort's trails to console
    private void showTrails(Resort r) {
        for (Trail t : r.getTrails()) {
            System.out.println("Trail " + String.valueOf(r.getTrails().indexOf(t) + 1) + ": " + t.getName());
        }
    }

    //MODIFIES: t
    //EFFECTS: adds user input as note to t
    private void doAddNote() {
        System.out.println("Note: \n");
        resorts.get(resortIndex).getTrails().get(trailIndex).addNote(input.nextLine());
    }

    //MODIFIES: t
    //EFFECTS: uses user input to modify the notes of t
    private void doEditNotes() {
        Trail t = resorts.get(resortIndex).getTrails().get(trailIndex);
        for (String n : t.getNotes()) {
            System.out.println("Note " + String.valueOf(t.getNotes().indexOf(n) + 1) + ": " + n);
            System.out.println("Edit note? y/n");
            if (input.nextLine().equals("y")) {
                System.out.println("New note: ");
                String newNote = input.nextLine();
                t.editNote(t.getNotes().indexOf(n), newNote);
                break;
            }
        }
    }

    // MODIFIES: trail at current trailIndex
    // EFFECTS: uses user input to add one trail to another
    private void doDownhillTrail() {
        Trail initTrail = resorts.get(resortIndex).getTrails().get(trailIndex);
        System.out.println("Which trail would you like to add?");
        showTrails(resorts.get(resortIndex));
        System.out.println("Trail #: ");
        String index = input.next();
        for (int i = 0; i < resorts.get(resortIndex).getTrails().size(); i++) {
            if (index.equals(String.valueOf(i + 1))) {
                try {
                    initTrail.addDownhillTrail(resorts.get(resortIndex).getTrails().get(i));
                    System.out.println("Trail add successful.");
                } catch (Exception e) {
                    System.out.println("Trail add failed.");
                }
                return;
            }
        }
        System.out.println("Invalid trail number.");
    }

    // EFFECTS: saves user selected resort to file
    private void doSaveResort() {
        System.out.println("Which resort would you like to save?");
        doSelectResort();
        Resort r = resorts.get(resortIndex);
        String path = (DATA_PATH + r.getName().replaceAll("\\s+", "") + ".json");
        JsonWriter writer = new JsonWriter(path);
        try {
            writer.open();
            writer.write(r);
            writer.close();
            System.out.println("Saved " + r.getName() + " to " + path);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file " + path);
        }
    }

    // EFFECTS: loads user selected resort from file
    private void doLoadResort() {
        try {
            try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
                List<Path> pathList = entries.collect(Collectors.toList());
                if (pathList.isEmpty()) {
                    System.out.println("No resorts to load!");
                } else {
                    System.out.println("Which file would you like to load? \n");
                    loopUntilCorrect: while (true) {
                        displayFiles();              
                        String index = input.next();
                        for (int i = 0; i < pathList.size(); i++) {
                            if (index.equals(String.valueOf(i + 1))) {
                                JsonReader reader = new JsonReader(pathList.get(i).toString());
                                resorts.add(reader.read());
                                break loopUntilCorrect;
                            }
                        }
                    }    
                }
            }
        } catch (IOException e) {
            System.out.println("IO Exception when reading file");
        }
    }
    
    // EFFECTS: prints file names in ./Data 
    private void displayFiles() throws IOException {
        try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
            List<Path> pathList = entries.collect(Collectors.toList());
            for (Path path : pathList) {
                System.out.println("File " + (pathList.indexOf(path) + 1) + ": " + path.toString());
            }            
        }
    }
    
    // EFFECTS: displays options: add new resort, select/show resorts, or exit
    private void displayMainMenu() {
        System.out.println();
        System.out.println("a - add new resort");
        System.out.println("s - select a resort to edit");
        System.out.println("f - save resort to file");
        System.out.println("l - load resort from file");
        System.out.println("e - exit application");
    }

    // EFFECTS: displays options: add trail, select/show trails, or go back
    private void displayResortMenu() {
        System.out.println();
        System.out.println("a - add new trail to resort");
        System.out.println("s - select a trail to edit");
        System.out.println("e - go back to previous menu");
    }

    // EFFECTS: displays options to modify a trail
    private void displayTrailMenu() {
        System.out.println();
        System.out.println(resorts.get(resortIndex).getTrails().get(trailIndex).getName());
        System.out.println("f - favorite/unfavorite trail");
        System.out.println("r - ride/unride trail");
        System.out.println("o - open/close trail");
        System.out.println("a - add note to trail");
        System.out.println("n - show/edit trail notes");
        System.out.println("d - add downhill trail");
        System.out.println("e - go back to previous menu");
    }
}
