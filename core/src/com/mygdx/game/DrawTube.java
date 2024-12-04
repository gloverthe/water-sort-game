package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Stack;

public class DrawTube {

    ShapeDrawer shapeDrawer;
    double piValue = Math.PI;
    Texture texture;

    public float tubeHeight;
    public float tubeWidth;
    public float arcRadius;
    public float tubeLipLength;



    public void load (SpriteBatch batch){


        //shapedrawer
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        texture = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
        TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(batch, region);

    }

    public float[] tubeDimensions() {
        float[] tubeSizes = new float[3];
        tubeHeight = 300f;
        tubeWidth = 70;
        arcRadius = 35;

        tubeSizes[0] = tubeHeight;
        tubeSizes[1] = tubeWidth;
        tubeSizes[2] = arcRadius;

        return tubeSizes;
    }


    public void renderTubes (float centreX, float centreY, int tubeNumber, Stack<String> tubeStack) {



        float line1_x1 = centreX - (tubeDimensions()[1]/2);
        float line1_x2 = centreX - (tubeDimensions()[1]/2);
        float line1_y1 = centreY - ((tubeDimensions()[0]/2)-tubeDimensions()[2]);
        float line1_y2 = centreY + ((tubeDimensions()[0]/2));

        float line2_x1 = centreX + (tubeDimensions()[1]/2);;
        float line2_x2 = centreX + (tubeDimensions()[1]/2);;
        float line2_y1 = centreY - ((tubeDimensions()[0]/2)-tubeDimensions()[2]);
        float line2_y2 = centreY + ((tubeDimensions()[0]/2));

        float arcX = centreX;
        float arcY = centreY - ((tubeDimensions()[0]/2)-tubeDimensions()[2]);

        Stack<String> tubeStackCopy;
         // Deep copy each individual stack





        float radsForArc = (float) ((tubeDimensions()[2] * piValue) / tubeDimensions()[2]);
        shapeDrawer.line(line1_x1, line1_y1, line1_x2, line1_y2);
        shapeDrawer.line(line2_x1, line2_y1, line2_x2, line2_y2);
        shapeDrawer.arc(arcX, arcY, tubeDimensions()[2], (float) piValue, radsForArc);
        // TODO apply the correct colour to the tube based on the poc text game
        //
        if (!tubeStack.isEmpty()) {
            tubeStackCopy = (Stack<String>) tubeStack.clone();
           float[] seg3 = getColours(tubeStackCopy.pop());
           float[] seg2 = getColours(tubeStackCopy.pop());
           float[] seg1 = getColours(tubeStackCopy.pop());
           float[] seg0 = getColours(tubeStackCopy.pop());
        float segmentHeight = tubeDimensions()[0] / (4); // Deduct 1 for the arc segment
        for (int i = 0; i < 4; i++) {
            switch (i % 4) { // Alternate colors for segments
                case 0:
                    shapeDrawer.setColor(seg0[0], seg0[1], seg0[2], seg0[3]);
                    break;
                case 1:
                    shapeDrawer.setColor(seg1[0], seg1[1], seg1[2], seg1[3]);
                    break;
                case 2:
                    shapeDrawer.setColor(seg2[0], seg2[1], seg2[2], seg2[3]);
                    break;
                case 3:
                    shapeDrawer.setColor(seg3[0], seg3[1], seg3[2], seg3[3]);
                    break;
            }
            if (i == 0) {
//                float startY = line1_y1 + (i - 1) * segmentHeight; // Start from 2nd segment (excluding arc)
                shapeDrawer.filledCircle(arcX, arcY, tubeDimensions()[2]);
                shapeDrawer.filledRectangle(line1_x1, line1_y1, tubeDimensions()[1], segmentHeight - tubeDimensions()[2]);
            } else {
                float startY = (line1_y1 + (segmentHeight * (i))) - tubeDimensions()[2]; // Start from 2nd segment (excluding arc)
                shapeDrawer.filledRectangle(line1_x1, startY, tubeDimensions()[1], segmentHeight);
            }

        }
        }



//    public ArrayList<Float> tubeCoOrds (float tubeCentreX, float tubeCentreY) {
//
//
//        }


    }



    private static float[] getColours(String colour) {
        switch (colour) {
            case "red":
                return new float[]{0.8f, 0.1f, 0.1f, 1};
            case "green":
                return new float[]{0.1f, 0.8f, 0.1f, 1};
            case "blue":
                return new float[]{0.1f, 0.1f, 0.8f, 1};
            case "yellow":
                return new float[]{0.8f, 0.8f, 0.1f, 1};
            case "purple":
                return new float[]{0.8f, 0.1f, 0.8f, 1};
            case "turquoise":
                return new float[]{0.1f, 0.8f, 0.8f, 1};
            default:
                return new float[]{0.8f, 0.1f, 0.1f, 1};
        }
    }
    //TODO neeed to fix the dyanmic tube co ords and then ask bard to re - write it
    public float[][] getTubeCoOrds (int tubeCount) {
        float x = 0;
        float y = 0;
        float x1 = 0;
//        float x2;

//        float[] xy = new float[2];
        float[][] tubeCoOrds = new float[tubeCount][2];
            switch (tubeCount) {
                case 3:
                   y =  (float) Constants.SCREEN_HEIGHT / 2;
//                    y =  (float) 500;
                    x =  (float) Constants.SCREEN_WIDTH / (tubeCount+1);
                    for (int i = 0; i < tubeCount; i++) {
                        tubeCoOrds[i][0] = x * (i+1);
                        tubeCoOrds[i][1] = y ;
                    }
                    break;
                case 4:
                    y =  (float) Constants.SCREEN_HEIGHT / 2;
//                    y =  (float) 500;
                    x =  (float) Constants.SCREEN_WIDTH / (tubeCount+1);
                    for (int i = 0; i < tubeCount; i++) {
                        tubeCoOrds[i][0] = x * (i+1);
                        tubeCoOrds[i][1] = y ;
                    }
                    break;
                case 5:
                    y =  (float) Constants.SCREEN_HEIGHT / 3;
                    x =  (float) Constants.SCREEN_WIDTH / 4;
                    x1 =  (float) Constants.SCREEN_WIDTH / 3;
                    for(int i = 0; i < tubeCount; i++) {
                        if (i < 3) {
                            tubeCoOrds[i][0] = x* (i+1);
                            tubeCoOrds[i][1] = y ;
                        }
                        else {
                            tubeCoOrds[i][0] = x1 * (i+1-3);
                            tubeCoOrds[i][1] = y * 2;
                        }
                    }
                    break;
                case 6:
                    y =  (float) Constants.SCREEN_HEIGHT / 3;
                    x =  (float) Constants.SCREEN_WIDTH / 4;
                    for(int i = 0; i < tubeCount; i++) {
                        if (i < 3) {
                            tubeCoOrds[i][0] = x* (i+1);
                            tubeCoOrds[i][1] = y ;
                        }
                        else {
                            tubeCoOrds[i][0] = x * (i+1-3);
                            tubeCoOrds[i][1] = y * 2;
                        }
                    }
                    break;
            }
        return tubeCoOrds;
    }


    public float[][] getTubeCoOrds3 (int tubeCount) {
        float x = 0;
        float y = 0;
        float x1 = 0;
//        float x2;

//        float[] xy = new float[2];
        float[][] tubeCoOrds = new float[tubeCount][2];
        switch (tubeCount) {
            case 3:
                y =  (float) Constants.SCREEN_HEIGHT / 2;
//                    y =  (float) 500;
                x =  (float) Constants.SCREEN_WIDTH / (tubeCount+1);
                for (int i = 0; i < tubeCount; i++) {
                    tubeCoOrds[i][0] = x * (i+1);
                    tubeCoOrds[i][1] = y ;
                }
                break;
            case 5:
                y =  (float) Constants.SCREEN_HEIGHT / 3;
                x =  (float) Constants.SCREEN_WIDTH / 4;
                x1 =  (float) Constants.SCREEN_WIDTH / 3;
                for(int i = 0; i < tubeCount; i++) {
                    if (i < 2) {
                        tubeCoOrds[i][0] = x* (i+1);
                        tubeCoOrds[i][1] = y ;
                    }
                    else {
                        tubeCoOrds[i][0] = x1 * (i+1);
                        tubeCoOrds[i][1] = y * 2;
                    }
                }
                break;
            case 6:
                y =  (float) Constants.SCREEN_HEIGHT / 3;
                x =  (float) Constants.SCREEN_WIDTH / 4;
                for(int i = 0; i < tubeCount; i++) {
                    if (i < 3) {
                        tubeCoOrds[i][0] = x* (i+1);
                        tubeCoOrds[i][1] = y ;
                    }
                    else {
                        tubeCoOrds[i][0] = x * (i+1-3);
                        tubeCoOrds[i][1] = y * 2;
                    }
                }
                break;
        }
        return tubeCoOrds;
    }

    public float[][] getTubeCoOrds_gpt(int tubeCount) {
        float[][] tubeCoOrds = new float[tubeCount][2];
        float x = 0;
        float y = 0;

        switch (tubeCount) {
            case 3:
                y = (float) Constants.SCREEN_HEIGHT / 2;
                x = (float) Constants.SCREEN_WIDTH / (tubeCount + 1);
                break;
            case 5:
                y = (float) Constants.SCREEN_HEIGHT / 3;
                x = (float) Constants.SCREEN_WIDTH / 4;
                break;
            case 6:
                y = (float) Constants.SCREEN_HEIGHT / 3;
                x = (float) Constants.SCREEN_WIDTH / 4;
                break;
            default:
                // Handle other tube counts if needed
                break;
        }

        int upperLimit = (tubeCount == 5) ? 2 : 3;
        int yOffset = (int) ((tubeCount == 5) ? y * 2 : y);

        for (int i = 0; i < tubeCount; i++) {
            if (i < upperLimit) {
                tubeCoOrds[i][0] = x * (i + 1);
                tubeCoOrds[i][1] = y;
            } else {
                tubeCoOrds[i][0] = x * (i + 1 - upperLimit);
                tubeCoOrds[i][1] = yOffset;
            }
        }

        return tubeCoOrds;
    }

    public void dispose() {
        texture.dispose();
    }

}

//    public ArrayList


