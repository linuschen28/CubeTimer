package ui;

import model.CubeTimer;
import model.Event;
import model.EventLog;
import model.SolveTime;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CubeTimerApp {
    private static final String JSON_STORE = "./data/cubeTimer.json";
    private CubeTimer cubeTimer;
    private Scanner input;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: runs the cube timer application
    public CubeTimerApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        cubeTimer = new CubeTimer("My cube timer");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCubeTimer();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCubeTimer() {
        boolean keepGoing = true;
        String command;

        initialize();

        System.out.println("Enter your name: ");
        String name = input.next();
        cubeTimer = new CubeTimer(name);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Hey! Get back here.");

        for (Event el : EventLog.getInstance()) {
            System.out.println(el.getDescription());
        }
    }

    // EFFECTS: processes command of user
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("d")) {
            doDelete();
        } else if (command.equals("m")) {
            showAo5();
        } else if (command.equals("g")) {
            doScramble();
        } else if (command.equals("t")) {
            doSolve();
        } else if (command.equals("s")) {
            saveCubeTimer();
        } else if (command.equals("l")) {
            loadCubeTimer();
        } else {
            System.out.println("Please try again...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a CubeTimer
    private void initialize() {
        cubeTimer = new CubeTimer("My cube timer");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add a new time");
        System.out.println("\td -> delete your last solve");
        System.out.println("\tm -> see your most recent ao5");
        System.out.println("\tg -> get a new scramble");
        System.out.println("\tt -> time your solve");
        System.out.println("\ts -> save time list to file");
        System.out.println("\tl -> load time list from file");
        System.out.println("\tq -> quit application");
    }

    // MODIFIES: this
    // EFFECTS: adds your time to the list
    private void doAdd() {
        System.out.println("Enter your time: ");
        double amount = input.nextDouble();

        if (amount >= 0.0) {
            cubeTimer.addTime(new SolveTime(amount));
        } else {
            System.out.println("Time cannot be negative\n");
        }

        printTimeList(cubeTimer);
    }

    // MODIFIES: this
    // EFFECTS: deletes the last solve in the list
    private void doDelete() {
        cubeTimer.deleteLastSolve();

        printTimeList(cubeTimer);
    }

    // EFFECTS: returns most recent ao5
    private void showAo5() {
        printAo5(cubeTimer);
    }

    // EFFECTS: returns randomly generated 3x3 cube scramble
    private void doScramble() {
        printScramble(cubeTimer);
    }

    // EFFECTS: brings up manually operated cube timer that adds the time to the list after timer is stopped
    private void doSolve() {
        Scanner scanner = new Scanner(System.in);
        long start;
        long end;
        double time;

        System.out.println("Type any character to start the cube timer");
        char s = scanner.next().charAt(0);
        start = System.currentTimeMillis();

        System.out.println("Type any character to stop the cube timer");
        char m = scanner.next().charAt(0);
        end = System.currentTimeMillis();

        time = (end - start) / 1000.0;
        System.out.println(time);

        cubeTimer.addTime(new SolveTime(time));

        printTimeList(cubeTimer);
    }

    private void printTimeList(CubeTimer curr) {
        ArrayList<Double> solveTimeList = new ArrayList<>();
        for (SolveTime time : curr.getTimeList()) {
            solveTimeList.add(time.getSolveTime());
        }
        System.out.println(solveTimeList);
    }

    private void printAo5(CubeTimer curr) {
        System.out.println(curr.getAverageOfFive());
    }

    private void printScramble(CubeTimer curr) {
        System.out.println(curr.getScramble());
    }

    // EFFECTS: saves the cube timer to file
    private void saveCubeTimer() {
        try {
            jsonWriter.open();
            jsonWriter.write(cubeTimer);
            jsonWriter.close();
            System.out.println("Saved " + cubeTimer.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads cube timer from file
    private void loadCubeTimer() {
        try {
            cubeTimer = jsonReader.read();
            System.out.println("Loaded " + cubeTimer.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
