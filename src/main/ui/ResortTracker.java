package ui;

import javax.imageio.ImageIO;
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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResortTracker implements ActionListener, ListSelectionListener {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    public static final String DATA_PATH = "./data/";

    private JFrame frame;
    private JPanel cards;

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
        JPanel mapCard = mapPanel();

        cards = new JPanel(new CardLayout());
        cards.add(fileCard, "file");
        cards.add(mapCard, "map");
    }

    private JPanel mapPanel() {
        ImageIcon map = new ImageIcon("./data/Whister-Blackcomb-FB.jpg");
        JLabel mapLabel = new JLabel(map);

        JPanel returnPanel = new JPanel();
        returnPanel.setSize((int)(WIDTH*0.8), (int)(HEIGHT*0.8));
        returnPanel.add(mapLabel);
        return returnPanel;
    }

    // EFFECTS: creates a panel to view files
    private JPanel filePanel() {
        JList<String> fileList;
        try {
            fileList = listFiles();
        } catch (IOException e) {
            fileList = new JList<>(new DefaultListModel<String>());
        }
        JScrollPane listScrollPane = new JScrollPane(fileList);
        listScrollPane.setSize(WIDTH, ((int)(HEIGHT*0.7)));

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

    // EFFECTS: creates a jlist of file names in data path
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

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("file")) {

        } else if (e.getActionCommand().equals("map")) {
            CardLayout cl = (CardLayout)(cards.getLayout());
            cl.show(cards, "map");
        }
    }

    public void valueChanged(ListSelectionEvent e) {

    }

    public static void main(String args[]) {
        new ResortTracker();
    }
}
