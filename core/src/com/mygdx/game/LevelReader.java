package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class LevelReader {
    public static Stack<String>[] getPuzzle(String levelName) {
//        String levelName = "level3"; // Pass in the desired level name here

        String filename = String.valueOf(Gdx.files.internal("levels/levels.json"));

        String relativePath = "assets/levels/levels.json";

        Stack<String>[] allTubes = new Stack[0];
        try {
            // Read the JSON file
            JsonParser parser = new JsonParser();
            JsonObject json = parser.parse(new FileReader((relativePath))).getAsJsonObject();

            // Get the level object from the JSON
            JsonObject levelObject = json.getAsJsonObject(levelName);

            // Create a list to store the tube data
            List<Struct<String>> tubes = new ArrayList<>();

            // Iterate over the tubes in the level
            for (String tubeName : levelObject.keySet()) {
                // Get the tube data from the JSON
                String[] tubeData = new Gson().fromJson(levelObject.get(tubeName), String[].class);

                // Create a Struct object and add it to the list
                Struct<String> tube = new Struct<>(tubeName, tubeData);
                tubes.add(tube);
            }


            // Convert the list to an array
            Struct<String>[] levelData = tubes.toArray(new Struct[0]);

            allTubes = new Stack[levelData.length];

            // Print the level data
            int j = 0;
            for (Struct<String> tube : levelData) {
//                System.out.println("Tube: " + tube.getName());
//                System.out.println("Colors: " + String.join(", ", tube.getData()));
//                System.out.println(tube.getData().length);
                allTubes[j] = new Stack<>();
                for (int i = 0; i < tube.getData().length; i++) {

                    System.out.println(tube.getData()[i]);
                    allTubes[j].push(tube.getData()[i]);
                    System.out.println(allTubes[j].peek());
                }
                j++;
                System.out.println();
            }

            for (int i = 0; i < allTubes.length; i++) {
                System.out.println("Tube number " + i + " : " + allTubes[i]);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return allTubes;
    }

    // Struct class to store tube data
    static class Struct<T> {
        private final String name;
        private final T[] data;

        public Struct(String name, T[] data) {
            this.name = name;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public T[] getData() {
            return data;
        }
    }
}
