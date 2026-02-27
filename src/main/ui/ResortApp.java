package ui;

import model.Resort;
import model.Trail;
import java.util.ArrayList;
import java.util.Scanner;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

@ExcludeFromJacocoGeneratedReport
// Resort trail tracking application
public class ResortApp {

    private ArrayList<Resort> resorts;
    private Scanner input;
    private int resortIndex;
    private int trailIndex;

    //Code based on TellerApp lecture lab

    //EFFECTS: runs the resort application
    public ResortApp() {
        resorts = new ArrayList<>();
        resortIndex = 0;
        trailIndex = 0;
        input = new Scanner(System.in);
        runApp();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
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

    //MODIFIES: this
    //EFFECTS: processes user command in main menu
    private void processMainCommand(String command) {
        if (command.equals("a")) {
            doAddResort();
        } else if (command.equals("s")) {
            if (!resorts.isEmpty()) {
                doSelectResort();
            } else {
                System.out.println("No resorts to show!");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }
    
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
            System.out.println("Invalid option.");
        }    
    }

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
                System.out.println("Invalid option");
        }
    }

    //MODIFIES: this, newResort
    //EFFECTS: creates a new resort based on user inputs
    private void doAddResort() {
        System.out.println("Name: \n");
        String name = input.nextLine();
        System.out.println("Region: \n");
        String region = input.nextLine();
        resorts.add(new Resort(name, region));
        runApp();
    }

    //REQUIRES: resorts is not empty
    //EFFECTS: selects an added resort
    private void doSelectResort() {
        showResorts();
        while (true) {
            System.out.println("Select resort: \n");
            String index = input.nextLine();
            for (int i = 0; i < resorts.size(); i++) {
                if (index.equals(String.valueOf(i + 1))) {
                    resortIndex = i;
                    resortMenu();
                    break;
                }
            }
            System.out.println("Invalid, try again.");
        }
    }

    private void resortMenu() {
        while (true) {
            displayResortMenu();
            String command = input.nextLine();
            command = command.toLowerCase();
            if (command.equals("e")) {
                //runApp();
                return;
            } else {
                processResortCommand(command);
            }
        }
    }

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
        displayResortMenu();
        String command = input.nextLine();
        command = command.toLowerCase();
        processResortCommand(command);
    }

    //REQUIRES: resort's trails are not empty
    //EFFECTS: selects an added trail
    private void doSelectTrail(Resort r) {
        showTrails(r);
        while (true) {
            System.out.println("Select trail: \n");
            String index = input.nextLine();
            for (int i = 0; i < r.getTrails().size(); i++) {
                if (index.equals(String.valueOf(i + 1))) {
                    trailIndex = i;
                    trailMenu();
                    break;
                }
            }
            System.out.println("Invalid, try again.");
        }
    }
    
    private void trailMenu() {
        while (true) {
            displayTrailMenu();
            System.out.println();
            String command = input.nextLine();
            command = command.substring(0,1).toLowerCase();
            if (command.equals("e")) {
                //resortMenu();
                break;
            } else {
                processTrailCommand(command);
            }
        }
    }

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
    
    //EFFECTS: displays options: add new resort, select/show resorts, or exit
    private void displayMainMenu() {
        System.out.println("a - add new resort");
        System.out.println("s - select a resort to edit");
        System.out.println("e - exit application");
    }

    //EFFECTS: displays options: add trail, select/show trails, or go back
    private void displayResortMenu() {
        System.out.println("a - add new trail to resort");
        System.out.println("s - select a trail to edit");
        System.out.println("e - go back to previous menu");
    }

    //EFFECTS: displays options: change favorite, change ridden, change open, add note, edit note, or add downhill trail
    private void displayTrailMenu() {
        System.out.println(resorts.get(resortIndex).getTrails().get(trailIndex).getName() + "\n");
        System.out.println("f - favorite/unfavorite trail");
        System.out.println("r - ride/unride trail");
        System.out.println("o - open/close trail");
        System.out.println("a - add note to trail");
        System.out.println("n - show/edit trail notes");
        System.out.println("d - add downhill trail");
        System.out.println("e - go back to previous menu");
    }
}
