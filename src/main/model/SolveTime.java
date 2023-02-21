package model;

import org.json.JSONObject;
import persistence.Writable;

public class SolveTime implements Writable {
    private double time;

    // EFFECTS: initializes a SolveTime with a time
    public SolveTime(double time) {
        this.time = time;
    }

    public double getSolveTime() {
        return this.time;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("time", time);
        return json;
    }

    public String toString() {
        return String.valueOf(this.time);
    }
}
