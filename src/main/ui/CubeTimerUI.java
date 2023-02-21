package ui;

import model.CubeTimer;
import model.Event;
import model.EventLog;
import model.SolveTime;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CubeTimerUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/cubeTimer.json";
    private CubeTimer ct;
    private JDesktopPane desktop;
    private JPanel controls;
    private JPanel display;
    private JTextField textBox;
    private JLabel ao5;
    private JLabel scramble;
    private JLabel solveGraphic;
    private JLabel imageGraphic;
    private JList<SolveTime> list;
    private DefaultListModel<SolveTime> myList = new DefaultListModel<>();
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader  jsonReader = new JsonReader(JSON_STORE);

    // EFFECTS: constructs a cube timer GUI
    public CubeTimerUI() {
        ct = new CubeTimer("Cube Timer");
        desktop = new JDesktopPane();
        controls = new JPanel();
        display = new JPanel();
        controls.setLayout(new BorderLayout());
        display.setLayout(new BoxLayout(display, BoxLayout.PAGE_AXIS));

        setContentPane(desktop);
        setTitle("Cube Timer");
        setSize(WIDTH, HEIGHT);

        addTextBox();
        addAo5Box();
        addTimeList();
        addButtons();
        addDisplay();

        placeControlsPanel();
        placeDisplayPanel();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);

        printLogToConsole();
    }

    // MODIFIES: desktop
    // EFFECTS: creates a text box to manually enter times
    public void addTextBox() {
        textBox = new JTextField();
        desktop.add(textBox);
        textBox.setBounds(35, 130, 200, 35);
    }

    // MODIFIES: desktop
    // EFFECTS: creates a label that displays the ao5 from when the "ao5" button was pressed
    public void addAo5Box() {
        ao5 = new JLabel();
        desktop.add(ao5);
        ao5.setText("ao5: " + ct.getAverageOfFive());
        ao5.setBounds(100, 165, 80, 35);
        ao5.setFont(new Font("Calibri", Font.BOLD, 14));
    }

    // MODIFIES: desktop
    // EFFECTS: creates a scrollable list of times
    public void addTimeList() {
        list = new JList<>(myList);
        JScrollPane scrollableList = new JScrollPane(list);
        desktop.add(scrollableList);
        scrollableList.setBounds(17, 200, WIDTH / 3 - 25, 350);
        scrollableList.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

    // MODIFIES: controls
    // EFFECTS: adds buttons with corresponding functions to controls panel
    public void addButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.setBackground(Color.GREEN);
        buttonPanel.add(new JButton(new SaveTimeListAction()));
        buttonPanel.add(new JButton(new LoadTimeListAction()));
        buttonPanel.add(new JButton(new DeleteTimeAction()));
        buttonPanel.add(new JButton(new AddTimeAction()));

        controls.add(buttonPanel, BorderLayout.NORTH);
    }

    // MODIFIES: display
    // EFFECTS: adds scramble, button for next scramble, and the visual component to display panel
    public void addDisplay() {
        JButton nextScramble = new JButton(new ScrambleAction());
        display.add(nextScramble);
        nextScramble.setAlignmentX(Component.CENTER_ALIGNMENT);

        scramble = new JLabel();
        display.add(scramble);
        scramble.setText(ct.getScramble());
        scramble.setFont(new Font("Serif", Font.PLAIN, 20));
        scramble.setAlignmentX(Component.CENTER_ALIGNMENT);
        display.add(Box.createRigidArea(new Dimension(0, HEIGHT / 3)));

        solveGraphic = new JLabel();
        display.add(solveGraphic);
        solveGraphic.setText("SOLVE");
        solveGraphic.setFont(new Font("Serif", Font.BOLD, 50));
        solveGraphic.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon image = new ImageIcon(getClass().getResource("cube.PNG"));
        imageGraphic = new JLabel(image);
        display.add(imageGraphic);
        imageGraphic.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    // MODIFIES: desktop
    // EFFECTS: adds controls panel to desktop
    public void placeControlsPanel() {
        controls.setVisible(true);
        desktop.add(controls, BorderLayout.WEST);
        controls.setBounds(10, 10, WIDTH / 3 - 10, HEIGHT - 50);
        controls.setBackground(Color.GREEN);
        controls.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    // MODIFIES: desktop
    // EFFECTS: adds display panel to desktop
    public void placeDisplayPanel() {
        display.setVisible(true);
        desktop.add(display, BorderLayout.EAST);
        display.setBounds(WIDTH / 3 + 10, 10, (2 * WIDTH) / 3 - 20, HEIGHT - 50);
        display.setBackground(Color.LIGHT_GRAY);
        display.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    // MODIFIES: myList
    // EFFECTS: clears myList
    public void clearList() {
        myList.clear();
    }

    // MODIFIES: myList
    // EFFECTS: adds all solveTimes in cube-timer time list to myList
    public void refreshList() {
        for (SolveTime st : ct.getTimeList()) {
            myList.addElement(st);
        }
    }

    public void printLogToConsole() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next.toString());
                }
                System.exit(0);
            }
        });
    }

    // implements the add time button
    private class AddTimeAction extends AbstractAction {

        AddTimeAction() {
            super("Add Time");
        }

        // MODIFIES: ao5, list
        // EFFECTS: adds given time to list, resets textBox to empty, and updates ao5
        @Override
        public void actionPerformed(ActionEvent evt) {
            SolveTime solveTime = new SolveTime(Double.parseDouble(textBox.getText()));
            ct.addTime(solveTime);
            clearList();
            refreshList();
            textBox.setText("");
            ao5.setText("ao5: " + ct.getAverageOfFive());
        }
    }

    // implements the delete time button
    private class DeleteTimeAction extends AbstractAction {

        DeleteTimeAction() {
            super("Delete last solve");
        }

        // MODIFIES: list
        // EFFECTS: deletes most recent time from list
        @Override
        public void actionPerformed(ActionEvent evt) {
            ct.deleteLastSolve();
            clearList();
            refreshList();
            ao5.setText("ao5: " + ct.getAverageOfFive());
        }
    }

    // implements the load time list button
    private class LoadTimeListAction extends AbstractAction {

        LoadTimeListAction() {
            super("Load time list");
        }

        // MODIFIES: list
        // EFFECTS: if a prior list was saved to file, loads list from file
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                ct = jsonReader.read();
                clearList();
                refreshList();
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
            ao5.setText("ao5: " + ct.getAverageOfFive());
        }
    }

    // implements the save time list button
    private class SaveTimeListAction extends AbstractAction {

        SaveTimeListAction() {
            super("Save time list");
        }

        // EFFECTS: saves current list to file
        @Override
        public void actionPerformed(ActionEvent evt) {
            try {
                jsonWriter.open();
                jsonWriter.write(ct);
                jsonWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // implements the next scramble button
    private class ScrambleAction extends AbstractAction {

        ScrambleAction() {
            super("next scramble");
        }

        // MODIFIES: scramble
        // EFFECTS: generates new scramble
        @Override
        public void actionPerformed(ActionEvent evt) {
            scramble.setText(ct.getScramble());
        }
    }

    // starts the application
    public static void main(String[] args) {
        new CubeTimerUI();
    }
}
