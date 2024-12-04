package com.mygdx.game;

import com.google.gson.Gson;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Map;

public class ReadInLevels {
    public static void main(String[] args) {
        Gson gson = new Gson();
        try {
            // Read the JSON file
            FileReader reader = new FileReader("/Users/game_/Library/CloudStorage/OneDrive-Personal/Documents/dev/Java/SonjasWaterSort/assets/levels/levels.json");

            // Convert JSON to a Map
            Map<String, Map<String, String[]>> levels = gson.fromJson(reader, Map.class);

//            Map<String, Map<String, String[]>> outerMap = levels.entrySet(); // Get the outer map
//            Map<String, String[]> innerMap = outerMap.get("key1"); // Get the inner map with key "key1"

            for (Map.Entry<String, Map<String, String[]>> entry : levels.entrySet()) {
                String levelName = entry.getKey();
                System.out.println(levelName);
            }

            // takes an argument of the level name and returns the level

//            Map<String, Map<String, String[]>> outerMap = levels.entrySet(); // Get the outer map
            Map<String, String[]> innerMap = levels.get("level1"); // Get the inner map with key "key1"
            System.out.println(innerMap);
            System.out.println(innerMap.keySet());
            System.out.println(innerMap.values());

            String[] tube1 = innerMap.get("tube1");
//            System.out.println(Arrays.toString(tube1));

            System.out.println(innerMap.size());

            Struct<String>[] allTubes = new Struct[innerMap.size()];
            for (int y = 1; y <= innerMap.size(); y++) {
                String tubeStr = "tube" + y;
                if (innerMap.containsKey(tubeStr)) {
                    System.out.println("Key found");

                }
                for (String innerKey : innerMap.keySet()) {
                    System.out.println(innerKey);
                }
//                String[] colours = innerMap.get(tubeStr);
//                allTubes[y] = new Struct<>(tubeStr, colours);
            }




//            for (Map.Entry<String, String[]> entry : innerMap.entrySet()) {
//                String tubeName = entry.getKey();
//                System.out.println(tubeName);
//
//            }


//            for (String outerKey : outerMap.keySet()) {
//                Map<String, String[]> innerMap = outerMap.get(outerKey);
//                // Process inner map...
//            }



            // Convert Map to an array of Struct<String>[]
//            Struct<String>[] levelArray = new Struct[levels.size()];
//            int i = 0;
//            for (Map.Entry<String, Map<String, String[]>> entry : levels.entrySet()) {
//                String levelName = entry.getKey();
//                Map<String, String[]> tubes = entry.getValue();
//                Struct<String>[] tubeArray = new Struct[tubes.size()];
//                int j = 0;
//                for (Map.Entry<String, String[]> tubeEntry : tubes.entrySet()) {
//                    String tubeName = tubeEntry.getKey();
//                    String[] colors = tubeEntry.getValue();
//                    tubeArray[j] = new Struct<>(tubeName, colors);
//                    j++;
//                }
//                levelArray[i] = new Struct<String>(tubeArray);
//                i++;
//            }
//
//            // Print the array of Struct<String>[]
//            for (Struct<String> level : levelArray) {
//                System.out.println(level);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Struct<T> {
        String name;
        T[] values;

        public Struct(String name, T[] values) {
            this.name = name;
            this.values = values;
        }

        @Override
        public String toString() {
            return "Struct{" +
                    "name='" + name + '\'' +
                    ", values=" + Arrays.toString(values) +
                    '}';
        }
    }
}
