package com.mygdx.game;

// Java program to demonstrate the accessing
// of the elements from the stack

import java.util.Random;
import java.util.Stack;


// USEFUL https://jenkov.com/tutorials/java-collections/stack.html - iterators for stacks
public class WaterSortPuzzle {

//    public static final int TUBE_COUNT = 4;
    public static final int TUBE_SIZE = 4;
//    public static final int COLOUR_COUNT = 6;
    private static final Random RANDOM = new Random();


//    public static void puzzleLoop(Stack<String>[] allTubes, Scanner scanner) {
//        while (!checkIfAllTubesAreEmpty(allTubes)) {
//            printAllTubes(allTubes);
//            int tubeCount = allTubes.length;
//            System.out.println("Enter source tube and destination tube (0 to " + (tubeCount - 1) + "), separated by space:");
//            int sourceTube = scanner.nextInt();
//            int destinationTube = scanner.nextInt();
//            if (isValidMove(allTubes, sourceTube, destinationTube)) {
////                System.out.println("Move is valid");
//                pourTube(allTubes, sourceTube, destinationTube);
//
////                System.out.println("Array 1: All elements are the same: " + canTubeBeEmpty(allTubes, destinationTube));
//                if (areAllElementsSame(allTubes[destinationTube])) {
//                    emptyTube(allTubes[destinationTube]);
//                    System.out.println("Tube " + destinationTube + " has been emptied");
//                }
//
//            }
//        }
//        System.out.println("Well done! You solved the puzzle!");
//    }


    public static void emptyTube(Stack<String> tube) {
        while (!(tube.isEmpty())) {
            tube.pop();
        }
    }

    public static boolean checkIfAllTubesAreEmpty(Stack<String>[] tubes) {
        // checks if all the Stacks in the Array Stack are empty
        for (Stack<String> tube : tubes) {
            if (!(tube.isEmpty())) {
                return false;
            }
        }
        return true;

    }

    public static void printAllTubes(Stack<String>[] tubeArray) {
        for (int i = 0; i < tubeArray.length; i++) {
            System.out.println("Tube number " + i + " : " + tubeArray[i]);
        }
    }


    public static boolean canTubeBeEmpty(Stack<String>[] tube, int tubeToCheck) {
//        boolean returnVar = new boolean;
        Stack<String> checkedTube = (Stack<String>) tube[tubeToCheck].clone();
        String[] tubeCols = new String[tube[tubeToCheck].size()];
        if (!(tube[tubeToCheck].isEmpty())) {
            for (int checkTubeI = 0; checkTubeI <= tube.length; checkTubeI++) {
                tubeCols[checkTubeI] = checkedTube.pop();
            }
            String firstElement = tubeCols[0];
            for (String element : tubeCols) {
                if (!element.equals(firstElement)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static void showTube(Stack<String> viewTube) {
//        if(viewTube != null) {
        Stack<String> showTube = (Stack<String>) viewTube.clone();
//        }
//        viewTube.copyInto(showTube);
        String tmpColour;
        System.out.println("Colours in tube: ");
        System.out.print("[ ");
        while (!(showTube.isEmpty())) {
            tmpColour = showTube.pop();
            System.out.print(tmpColour + " | ");
        }
        System.out.println(" ]");
    }

    public static boolean areAllElementsSame(Stack<String> stack) {
        if (stack.size() != TUBE_SIZE) {
            return false; // An empty stack or null stack is considered to have the same elements.
        }
        if (stack == null || stack.isEmpty()) {
            return true; // An empty stack or null stack is considered to have the same elements.
        }

        String firstElement = stack.peek(); // Get the first element without removing it from the stack.
        for (String element : stack) {
            if (!element.equals(firstElement)) {
                return false;
            }
        }
        return true;
    }

    public static void pourTube(Stack<String>[] tubes, int firstTube, int secondTube) {
        String tmpColor;
        System.out.println("Pouring from tube " + firstTube );
        tmpColor = tubes[firstTube].pop();
        System.out.println("Pouring " + tmpColor + " into tube " + secondTube);
        tubes[secondTube].push(tmpColor);
//        if (tube[firstTube].isEmpty()) {
//            System.out.println("Second tube is Empty - pouring...");
//            tmpColor = tube[firstTube].pop();
//            tube[secondTube].push(tmpColor);
//        }
//        else if (tube[secondTube].size() == TUBE_SIZE) {
//            System.out.println("Second tube is full! Try again.");
//        }
//        else if (!(tube[firstTube].peek().equals(tube[secondTube].peek()))) {
//            System.out.println("Colours ! Try again.");
//        }
//        else if (tube[firstTube].peek().equals(tube[secondTube].peek())) {
//            System.out.println(tube[firstTube].peek() + " = " + tube[secondTube].peek());
//            System.out.println(" Pouring from tube...");
//            tmpColor = tube[firstTube].pop();
//            tube[secondTube].push(tmpColor);
//        }
//        else {
//            System.out.println(" Some else has happened");
//        }
    }

    public static boolean isValidMove(Stack<String>[] tubes, int sourceTube, int destinationTube) {
        // Check if the move is valid
        if (sourceTube < 0 || sourceTube >= tubes.length || destinationTube < 0 || destinationTube >= tubes.length) {
            System.out.println("Tube does not exist!");
            return false;
        }

        if (sourceTube == destinationTube) {
            System.out.println("Source and destination tube cannot be the same");
        }

        if (tubes[destinationTube].size() == TUBE_SIZE) {
            System.out.println("Destination tube is full!");
            return false;
        }


        if (tubes[sourceTube].isEmpty()) {
            System.out.println("Nothing in the source tube!");
            return false;
        }

        if (tubes[destinationTube].isEmpty()) {
            return true;
        }

        String sourceTop = tubes[sourceTube].peek();
        String destinationTop = tubes[destinationTube].peek();

        if (sourceTop.equals(destinationTop)) {
            System.out.println("Move is valid!");
            return true;
        } else {
            System.out.println("Move is INVALID!");
            return false;
        }
    }
}

