package persistence;

import model.CubeTimer;
import model.SolveTime;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Citation: JsonReader class referenced from JsonSerializationDemo example

// Represents a reader that reads cube-timer from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads cube-timer from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CubeTimer read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCubeTimer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses cube-timer from JSON object and returns it
    private CubeTimer parseCubeTimer(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        CubeTimer ct = new CubeTimer(name);
        addSolveTimes(ct, jsonObject);
        return ct;
    }

    // MODIFIES: ct
    // EFFECTS: parses solveTimes from JSON object and adds them to cube-timer
    private void addSolveTimes(CubeTimer ct, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("time-list");
        for (Object json : jsonArray) {
            JSONObject nextSolveTime = (JSONObject) json;
            addSolveTime(ct, nextSolveTime);
        }
    }

    // MODIFIES: ct
    // EFFECTS: parses solveTime from JSON object and adds it to cube-timer
    private void addSolveTime(CubeTimer ct, JSONObject jsonObject) {
        double time = jsonObject.getDouble("time");
        SolveTime solveTime = new SolveTime(time);
        ct.addTime(solveTime);
    }
}
