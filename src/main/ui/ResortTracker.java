package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Resort;
import model.Trail;
import persistence.JsonReader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// GUI ski resort trail tracking application
public class ResortTracker implements ActionListener, ListSelectionListener {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final String DATA_PATH = "./data/";

    private JFrame frame;
    private JPanel cards;

    private int selectedIndex;
    private JList<String> currentList;
    private int fileIndex;

    // EFFECTS: initializes the application
    public ResortTracker() {
        frame = new JFrame("Ski Resort Trail Tracker");
        initializeGraphics();
    }

    // EFFECTS: renders windows
    private void initializeGraphics() {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        createCards();
        frame.getContentPane().add(cards);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates new card layout and sets cards
    private void createCards() {
        JPanel fileCard = filePanel();
        cards = new JPanel(new CardLayout());
        cards.add(fileCard, "file");
        try {
            cards.add(mapPanel(), "map");
        } catch (Exception e) {
            System.out.println("Map panel could not be displayed: " + e.getMessage());
        }
    }

    // EFFECTS: creates a panel to view files
    private JPanel filePanel() {
        JList<String> fileList;
        try {
            fileList = listFiles();
        } catch (IOException e) {
            fileList = new JList<>(new DefaultListModel<String>());
        }
        currentList = fileList;
        JScrollPane listScrollPane = new JScrollPane(fileList);
        listScrollPane.setSize(WIDTH, ((int)(HEIGHT * 0.7)));

        JButton openButton = new JButton("Open");
        openButton.addActionListener(this);
        openButton.setActionCommand("file");

        JButton mapButton = new JButton("Map");
        mapButton.addActionListener(this);
        mapButton.setActionCommand("map");

        JPanel returnPanel = new JPanel();
        returnPanel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        returnPanel.add(listScrollPane, BorderLayout.PAGE_START);
        returnPanel.add(openButton, BorderLayout.EAST);
        returnPanel.add(mapButton, BorderLayout.EAST);
        return returnPanel;
    }

    // EFFECTS: creates a panel that shows a map of a resort
    private JPanel mapPanel() throws FileNotFoundException, IOException {
        File mapFile = new File(".\\data\\whistler.png");
        BufferedImage mapImage = ImageIO.read(mapFile);
        JLabel mapLabel = new JLabel(new ImageIcon(mapImage));

        JPanel returnPanel = new JPanel();
        returnPanel.setSize((int)(WIDTH * 0.8), (int)(HEIGHT * 0.8));
        returnPanel.add(mapLabel);
        return returnPanel;
    }

    // EFFECTS: returns a panel with a list of trails 
    private JPanel trailPanel(Boolean filter, int filterIndex) {

        JList<String> trailList = readTrails(filter, filterIndex);
        trailList.addListSelectionListener(this);
        currentList = trailList;
        JScrollPane trailScrollPane = new JScrollPane(trailList);
        trailScrollPane.setSize(WIDTH, ((int)(HEIGHT * 0.7)));
        
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(this);
        filterButton.setActionCommand("filter"); 

        JButton downhillButton = new JButton("Downhill Trails");
        downhillButton.addActionListener(this);
        downhillButton.setActionCommand("downhill"); 

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save"); 

        JPanel returnPanel = new JPanel();
        returnPanel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        returnPanel.add(trailScrollPane, BorderLayout.PAGE_START);
        returnPanel.add(filterButton, BorderLayout.SOUTH);
        returnPanel.add(downhillButton, BorderLayout.SOUTH);
        returnPanel.add(saveButton, BorderLayout.SOUTH);
        return returnPanel;
    }

    // MODIFIES: /data
    // EFFECTS: saves file to current location
    private void saveFile() {
        try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
            List<Path> pathList = entries.collect(Collectors.toList());
            JOptionPane.showMessageDialog(null, "File saved to " + pathList.get(fileIndex).toString());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: file not saved.");
        }
    }

    // EFFECTS: returns a list of trails using the selected index, and filtering if necessary
    private JList<String> readTrails(Boolean filter, int filterIndex) {
        DefaultListModel<String> m = new DefaultListModel<>();
        try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
            List<Path> pathList = entries.collect(Collectors.toList());
            JsonReader reader = new JsonReader(pathList.get(fileIndex).toString());
            Resort r = reader.read();
            if (!filter) {
                for (Trail t : r.getTrails()) {
                    m.addElement(t.getName());
                }
            } else {
                switch (filterIndex) {
                    case 0: m.addElement(r.getTrails().get(0).getName());
                        break;
                    case 1: m.addElement(r.getTrails().get(1).getName());
                        break;
                    case 2: m.addElement(r.getTrails().get(0).getName());  
                        m.addElement(r.getTrails().get(1).getName());
                        break;
                }
            }
            
        } catch (IOException e) { /* non empty catch block */ }
        JList<String> returnList = new JList<>(m);
        return returnList;
    }

    // EFFECTS: shows filter dialogue box and returns result
    private int filterBox() {
        String filterIndex = JOptionPane.showInputDialog(
                null, "Filter type? difficulty - 0, location - 1, features - 2");
        return Integer.parseInt(filterIndex);
    }

    // EFFECTS: creates a JList of file names in data path
    private JList<String> listFiles() throws IOException {
        DefaultListModel<String> fileListModel = new DefaultListModel<>();
        try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
            List<Path> pathList = entries.collect(Collectors.toList());
            for (Path path : pathList) {
                fileListModel.addElement(path.toString().substring(7,path.toString().length() - 5));
            }            
        }
        fileListModel.addElement("New Resort");
        JList<String> returnList = new JList<>(fileListModel);
        returnList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        returnList.setSelectedIndex(0);
        returnList.addListSelectionListener(this);
        returnList.setVisibleRowCount(5);
        return returnList;
    }

    // EFFECTS: lists downhill trails from selected trail
    private void downhillTrails() {
        if (selectedIndex == 0) {
            JOptionPane.showMessageDialog(null, "No downhill trails :(");
        } else if (selectedIndex == 1) {
            JOptionPane.showMessageDialog(null, "Downhill trails: name");
        } else {
            JOptionPane.showMessageDialog(null, "No Trail Selected!");
        }
    }

    // EFFECTS: controls action when button is pressed
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        if (e.getActionCommand().equals("file")) {
            fileIndex = selectedIndex;
            cards.add(trailPanel(false, -1), "trail");
            cl.show(cards, "trail");
            selectedIndex = -1;
        } else if (e.getActionCommand().equals("map")) {
            cl.show(cards, "map");
        } else if (e.getActionCommand().equals("filter")) {
            cards.add(trailPanel(true, filterBox()), "filter");
            cl.show(cards, "filter");
        } else if (e.getActionCommand().equals("downhill")) {
            downhillTrails();
        } else if (e.getActionCommand().equals("save")) {
            saveFile();
        }
    }

    // Changes selected index when a list has a new value selected
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            if (currentList.getSelectedIndex() == -1) {
                // no value selected
            } else {
                selectedIndex = currentList.getSelectedIndex();
            }
        }
    }

    public static void main(String[] args) {
        new ResortTracker();
    }
}