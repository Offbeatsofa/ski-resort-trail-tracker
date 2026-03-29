package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.EventLog;
import model.Resort;
import model.Trail;
import model.Resort.Filter;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// GUI ski resort trail tracking application
public class ResortTracker implements ActionListener, ListSelectionListener, WindowListener {

    public static final int WIDTH = (int)(1000 * 0.7);
    public static final int HEIGHT = (int)(700 * 0.7);
    public static final String DATA_PATH = "./data/";
    public static final String MAP_PHOTO = "./photos/whistler.png";

    private JFrame frame;
    private JPanel cards;

    private int selectedIndex;
    private JList<String> currentList;
    private int fileIndex;

    private Resort resort;

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
        frame.addWindowListener(this);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates new card layout and sets cards
    private void createCards() {
        JPanel fileCard = filePanel();
        cards = new JPanel(new CardLayout());
        cards.add(fileCard, "file");
        cards.add(mapPanel(), "map");
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
        listScrollPane.setPreferredSize(new Dimension((int)(WIDTH * 0.7), HEIGHT));

        JButton openButton = new JButton("Open");
        openButton.addActionListener(this);
        openButton.setActionCommand("load");

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
    private JPanel mapPanel() {
        JLabel mapLabel = getImageLabel(MAP_PHOTO);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(this);
        backButton.setActionCommand("file");

        JPanel returnPanel = new JPanel();
        returnPanel.setSize((int)(WIDTH * 0.8), (int)(HEIGHT * 0.8));
        returnPanel.add(mapLabel, BorderLayout.SOUTH);
        returnPanel.add(backButton, BorderLayout.NORTH);
        return returnPanel;
    }

    // EFFECTS: returns JLable with image found at path
    private JLabel getImageLabel(String path) {
        File mapFile = new File(path);
        BufferedImage mapImage;
        try {
            mapImage = ImageIO.read(mapFile);
        } catch (IOException e) {
            mapImage = null;
            System.out.println(e.getMessage());
        }
        Image scaledImage = mapImage.getScaledInstance((int)(WIDTH * 0.9), -1, Image.SCALE_SMOOTH);
        JLabel mapLabel = new JLabel(new ImageIcon(scaledImage));
        return mapLabel;
    }

    // EFFECTS: returns a panel with a list of trails 
    private JPanel trailPanel(Boolean filter, String filterIndex) {
        setResort();
        JScrollPane trailScrollPane = getTrailScrollPane(filter, filterIndex);
        JPanel buttonPanel = getButtonPanel();
        JPanel returnPanel = new JPanel();
        returnPanel.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        returnPanel.add(trailScrollPane, BorderLayout.PAGE_END);
        returnPanel.add(buttonPanel, BorderLayout.PAGE_START);
        return returnPanel;
    }

    // EFFECTS: returns a scroll pane with all of resort's trails
    private JScrollPane getTrailScrollPane(Boolean filter, String filterIndex) {
        JList<String> trailList = readTrails(filter, filterIndex);
        trailList.addListSelectionListener(this);
        currentList = trailList;
        JScrollPane trailScrollPane = new JScrollPane(trailList);
        trailScrollPane.setPreferredSize(new Dimension(((int)(WIDTH * 0.7)), (int)(HEIGHT * 0.7)));
        return trailScrollPane;
    }

    // EFFECTS: returns a jpanel with filter, downhill, and save buttons
    private JPanel getButtonPanel() {
        JButton filterButton = new JButton("Filter");
        filterButton.addActionListener(this);
        filterButton.setActionCommand("filter"); 

        JButton downhillButton = new JButton("Downhill Trails");
        downhillButton.addActionListener(this);
        downhillButton.setActionCommand("downhill"); 

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save"); 

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(filterButton, BorderLayout.WEST);
        buttonPanel.add(downhillButton, BorderLayout.CENTER);
        buttonPanel.add(saveButton, BorderLayout.EAST);
        return buttonPanel;
    }

    // MODIFIES: /data
    // EFFECTS: saves file to current location
    private void saveFile() {
        try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
            String filePath = entries.collect(Collectors.toList()).get(fileIndex).toString();
            JsonWriter writer = new JsonWriter(filePath);
            writer.open();
            writer.write(resort);
            writer.close();
            JOptionPane.showMessageDialog(null, "File saved to " + filePath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: file not saved.");
        }
    }

    // MODIFIES: this
    // EFFECTS: sets resort to the one located at current file index
    private void setResort() {
        try (Stream<Path> entries = Files.list(Paths.get(DATA_PATH))) {
            List<Path> pathList = entries.collect(Collectors.toList());
            JsonReader reader = new JsonReader(pathList.get(fileIndex).toString());
            this.resort = reader.read();
        } catch (IOException e) {
            this.resort = null;
            System.out.println("couldn't read resort.");
        }
    }

    // EFFECTS: returns a list of trails using the selected index, and filtering if necessary
    private JList<String> readTrails(Boolean filter, String filterIndex) {
        DefaultListModel<String> m = new DefaultListModel<>();
        if (!filter) {
            for (Trail t : resort.getTrails()) {
                m.addElement(t.getName());
            }
        } else {
            switch (filterIndex) {
                case "0": for (Trail t : resort.getTrails(Filter.DIFFICULTY, getFilter())) {
                        m.addElement(t.getName());
                    }
                    break;
                case "1": for (Trail t : resort.getTrails(Filter.LOCATION, getFilter())) {
                        m.addElement(t.getName());
                    }
                    break;
                case "2": for (Trail t : resort.getTrails(Filter.FEATURES, getFilter())) {
                        m.addElement(t.getName());
                    }
                    break;
                default:    
            }
        }
        return new JList<>(m);
    }

    private String getFilter() {
        return JOptionPane.showInputDialog("Filter?");
    }

    // EFFECTS: shows filter dialogue box and returns result
    private String filterBox() {
        return JOptionPane.showInputDialog(null, "Filter type? difficulty - 0, location - 1, features - 2");
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
        for (Trail t : resort.getTrails()) {
            if (resort.getTrails().indexOf(t) == selectedIndex) {
                List<Trail> run = resort.generateRun(t);
                StringBuilder trails = new StringBuilder("Run: ");
                for (Trail trail : run) {
                    trails.append(trail.getName() + ", ");
                }
                JOptionPane.showMessageDialog(null, trails.toString());   
            }
        }
    }

    // EFFECTS: controls action when button is pressed
    public void actionPerformed(ActionEvent e) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        switch (e.getActionCommand()) {
            case "load":
                fileIndex = selectedIndex;
                cards.add(trailPanel(false, ""), "trail");
                cl.show(cards, "trail");
                selectedIndex = -1;
                break;
            case "map": cl.show(cards, "map");
                break;
            case "file": cl.show(cards, "file");
                break;
            case "filter":
                cards.add(trailPanel(true, filterBox()), "filter");
                cl.show(cards, "filter");
                break;
            case "downhill": downhillTrails();
                break;
            case "save": saveFile();
                break;
            default:
                System.out.println("Button without implemented function!");
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

    @Override
    public void windowClosing(WindowEvent a) {
        for (model.Event e : EventLog.getInstance()) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void windowClosed(WindowEvent a) {
    
    }

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}