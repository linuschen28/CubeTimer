package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;

public class CubeTimer implements Writable {
    private ArrayList<SolveTime> timeList;
    private String ao5 = "N/A";
    private String scramble = "";
    private String name;

    // EFFECTS: creates a Timer with a name and timeList
    public CubeTimer(String name) {
        this.name = name;
        this.timeList = new ArrayList<SolveTime>();
    }

    // MODIFIES: this
    // EFFECTS: produces a valid cube scramble 19-21 moves long
    public void scrambleCube() {
        this.scramble =  "";
        ArrayList<String> moves = new ArrayList<>();
        moves.addAll(Arrays.asList("R ", "R' ", "R2 ", "L ", "L' ", "L2 ", "U ", "U' ", "U2 ", "D ", "D' ", "D2 ",
                "F ", "F' ", "F2 ", "B ", "B' ", "B2 "));
        int length = Math.round((float) Math.random() * 2);
        int randomMove;
        int prev = -1;
        for (int x = 0; x < 19 + length; x++) {
            randomMove = Math.round((float) Math.random() * 17);
            while (!accept(prev, randomMove)) {
                randomMove = Math.round((float) Math.random() * 17);
            }
            prev = randomMove;
            this.scramble += moves.get(randomMove);
        }

        EventLog.getInstance().logEvent(new Event("Generated new scramble: " + scramble));
    }

    // REQUIRES: curr must be >= 0 and <= 17
    // EFFECTS: produces true when the current move is valid given the previous move; a valid current move must turn a
    //          different face of the cube from the previous move
    public boolean accept(int prev, int curr) {
        if (prev == 0 || prev == 1 || prev == 2) {
            return !(curr == 0 || curr == 1 || curr == 2);
        } else if (prev == 3 || prev == 4 || prev == 5) {
            return !(curr == 3 || curr == 4 || curr == 5);
        } else if (prev == 6 || prev == 7 || prev == 8) {
            return !(curr == 6 || curr == 7 || curr == 8);
        } else if (prev == 9 || prev == 10 || prev == 11) {
            return !(curr == 9 || curr == 10 || curr == 11);
        } else if (prev == 12 || prev == 13 || prev == 14) {
            return !(curr == 12 || curr == 13 || curr == 14);
        } else if (prev == 15 || prev == 16 || prev == 17) {
            return !(curr == 15 || curr == 16 || curr == 17);
        } else {
            return true;
        }
    }

    // REQUIRES: time entered must be to two decimal places
    // MODIFIES: this
    // EFFECTS: adds a new time to the timeList
    public void addTime(SolveTime solveTime) {
        if (solveTime.getSolveTime() >= 0.0) {
            this.timeList.add(solveTime);
        } else {
            System.out.println("Time cannot be negative");
        }

        EventLog.getInstance().logEvent(new Event("Added time: " + solveTime.getSolveTime()));
    }

    // REQUIRES: timeList must not be empty
    // MODIFIES: this
    // EFFECTS: removes the last time added from the timeList
    public void deleteLastSolve() {
        if (timeList.size() == 0) {
            System.out.println("No times in list");
        } else {
            this.timeList.remove(timeList.size() - 1);
        }

        EventLog.getInstance().logEvent(new Event("Deleted most recent time"));
    }

    // REQUIRES: list size must be >= 5
    // MODIFIES: this
    // EFFECTS: takes mean of last five solve times
    public String getAverageOfFive() {
        if (timeList.size() >= 5) {
            ao5 = String.format("%.2f",
                    (((timeList.get(timeList.size() - 1).getSolveTime()
                            + timeList.get(timeList.size() - 2).getSolveTime()
                            + timeList.get(timeList.size() - 3).getSolveTime()
                            + timeList.get(timeList.size() - 4).getSolveTime()
                            + timeList.get(timeList.size() - 5).getSolveTime()) / 5)));
        } else {
            ao5 = "N/A";
        }

        EventLog.getInstance().logEvent(new Event("Generated current ao5: " + ao5));
        return ao5;
    }

    public ArrayList<SolveTime> getTimeList() {
        return this.timeList;
    }

    public String getScramble() {
        scrambleCube();
        return this.scramble;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time-list", timesToJson());
        return json;
    }

    // EFFECTS: returns times in this time-list as a JSON array
    private JSONArray timesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SolveTime solveTime : timeList) {
            jsonArray.put(solveTime.toJson());
        }

        return jsonArray;
    }
}
