package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
public class ffsTubes {

    public static void main(String[] args) {
//        String filePath = Gdx.files.internal("fonts/Montserrat-Regular.ttf");

        try {
            // Read the JSON file
            JsonObject jsonObject = JsonParser.parseReader(new FileReader(String.valueOf(Gdx.files.internal("levels/levels.json")))).getAsJsonObject();

            // Create an array of Stacks to store the JSON data
            Stack<String>[] stacks = new Stack[jsonObject.size()];

            int index = 0;
            for (String key : jsonObject.keySet()) {
                JsonObject levelObject = jsonObject.getAsJsonObject(key);
                Stack<String> stack = new Stack<>();

                for (String tubeKey : levelObject.keySet()) {
                    // Push each element in the tube to the stack
                    for (JsonElement item : levelObject.getAsJsonArray(tubeKey)) {
                        stack.push(item.getAsString());
                    }
                }

                stacks[index++] = stack;
            }

            // Print the contents of the stacks
            for (Stack<String> stack : stacks) {
                System.out.println(stack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

