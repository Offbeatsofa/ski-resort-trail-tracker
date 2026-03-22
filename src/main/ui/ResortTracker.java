package ui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import ca.ubc.cs.ExcludeFromJacocoGeneratedReport;

import model.Resort;
import model.Trail;
import persistence.JsonReader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Starts and runs the application window
@ExcludeFromJacocoGeneratedReport
public class ResortTracker extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final String DATA_PATH = "./data/";

    private Resort resort;
    private int selectIndex;

    // EFFECTS: initializes the application
    public ResortTracker() {
        super("Ski Resort Trail Tracker");
        initializeGraphics();
    }

    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        addComponents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates bottom panel and adds button components
    private void addComponents() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(0, 1));
        bottomPanel.setSize(new Dimension(0,0));
        add(bottomPanel, BorderLayout.SOUTH);

        //JList<String> trailList = listResorts("./data/testReaderGeneralResort.json");
        //JScrollPane listScrollPane = new JScrollPane(trailList);
        //add(listScrollPane);

        try {
            JList<String> trailList = listFiles();
            JScrollPane listScrollPane = new JScrollPane(trailList);
            add(listScrollPane);
        } catch (IOException e) {/* uhh fuck */}
        

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new EditButtonActionListener());
        editButton.setActionCommand("trail");
        bottomPanel.add(editButton);

    }

    private JList<String> listFiles() throws IOException {
        DefaultListModel<String> fileListModel = new DefaultListModel<>();
        try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
            List<Path> pathList = entries.collect(Collectors.toList());
            for (Path path : pathList) {
                fileListModel.addElement(path.toString());
            }            
        }
        fileListModel.addElement("New Resort");
        JList<String> returnList = new JList<>(fileListModel);
        returnList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        returnList.setSelectedIndex(0);
        returnList.addListSelectionListener(new SkiListSelectionListener(returnList));
        returnList.setVisibleRowCount(5);
        return returnList;
    }

    // EFFECTS: loads resort from file into application
    private Resort readResort(String path) {
        JsonReader reader = new JsonReader(path);
        try {
            return reader.read();
        } catch (IOException e) {
            return null; // TODO throw exception 
        }
        
    }

    // EFFECTS: returns a JList of Trails from a path
    private JList<String> listTrails(String path) {
        DefaultListModel<String> trailListModel = new DefaultListModel<>();
        JsonReader reader = new JsonReader(path);
        try {
            Resort r = reader.read();
            for (Trail t : r.getTrails()) {
                trailListModel.addElement(t.getName());
            }
        } catch (IOException e) {
            
        }
        JList<String> returnList = new JList<>(trailListModel);
        returnList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        returnList.setSelectedIndex(0);
        returnList.addListSelectionListener(new SkiListSelectionListener(returnList));
        returnList.setVisibleRowCount(5);
        return returnList;
    }

    // EFFECTS: returns a JList of all aspects of a resort in the application
    private JList<String> trailList(Trail t) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement(t.getName());
        listModel.addElement(t.getDifficulty());
        listModel.addElement(t.getLocation());
        listModel.addElement(t.getFeatures());
        listModel.addElement("Notes");
        listModel.addElement("Favorite? " + (t.isFavorite() ? "true" : "false"));
        listModel.addElement("Ridden? " + (t.isRidden() ? "true" : "false"));
        listModel.addElement("Open? " + (t.isOpen() ? "true" : "false"));
        listModel.addElement("Trails");
        JList<String> returnList = new JList<>(listModel);
        returnList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        returnList.setSelectedIndex(0);
        returnList.addListSelectionListener(new SkiListSelectionListener(returnList));
        returnList.setVisibleRowCount(5);
        return returnList;
    }

    // EFFECTS: displays menu for editing a trail, given by resort and index
    private void editTrail(int index) {
        JList<String> trail1List = trailList(resort.getTrails().get(index));
        JScrollPane trailScrollPane = new JScrollPane(trail1List);
        add(trailScrollPane);
    }
    public static void main(String args[]) {
        new ResortTracker();
    }

    private class EditButtonActionListener implements ActionListener {
        // EFFECTS: does something when the edit button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand() == "trail") {
                editTrail(selectIndex);
            }
        }
    }

    private class SkiListSelectionListener implements ListSelectionListener {
        JList<String> list;

        public SkiListSelectionListener(JList<String> list) {
            this.list = list;
        }

        // EFFECTS: changes indices when the list selection is changed
        public void valueChanged(ListSelectionEvent l) {
            if (!l.getValueIsAdjusting()) {
                if (list.getSelectedIndex() == -1) {
                    // no item selected, probably disable button
                } else {
                    selectIndex = list.getSelectedIndex();
                }
            }
        }
    }
}
