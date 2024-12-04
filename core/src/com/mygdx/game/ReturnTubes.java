package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReturnTubes {
    ShapeDrawer shapeDrawer;
    static double piValue = Math.PI;
    Texture texture;
    static float tubeHeight;
    static float tubeWidth;
    static float arcRadius;

    public float tubeLipLength;


//    public void load(SpriteBatch batch) {
//
//
//        //shapedrawer
//        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
//        pixmap.setColor(Color.WHITE);
//        pixmap.drawPixel(0, 0);
//        texture = new Texture(pixmap); //remember to dispose of later
//        pixmap.dispose();
//        TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
//        shapeDrawer = new ShapeDrawer(batch, region);
//
//    }

    public static float[] tubeDimensions() {
        float[] tubeSizes = new float[3];

        tubeHeight = 300f;
        tubeWidth = 70;
        arcRadius = 35;

        tubeSizes[0] = tubeHeight;
        tubeSizes[1] = tubeWidth;
        tubeSizes[2] = arcRadius;

        return tubeSizes;
    }


    public static TubeDrawer renderTube(float centreX, float centreY, int tubeNumber, Stack<String> tubeStack) {
        TubeDrawer tubeDrawer = new TubeDrawer();

        tubeDrawer.tubeNumber = tubeNumber;

        float line1_x1 = centreX - (tubeDimensions()[1] / 2);
        float line1_x2 = centreX - (tubeDimensions()[1] / 2);
        float line1_y1 = centreY - ((tubeDimensions()[0] / 2) - tubeDimensions()[2]);
        float line1_y2 = centreY + ((tubeDimensions()[0] / 2));

        float line2_x1 = centreX + (tubeDimensions()[1] / 2);
        float line2_x2 = centreX + (tubeDimensions()[1] / 2);
        float line2_y1 = centreY - ((tubeDimensions()[0] / 2) - tubeDimensions()[2]);
        float line2_y2 = centreY + ((tubeDimensions()[0] / 2));

        float arcX = centreX;
        float arcY = centreY - ((tubeDimensions()[0] / 2) - tubeDimensions()[2]);

        Stack<String> tubeStackCopy;
        // Deep copy each individual stack
        float radsForArc = (float) ((tubeDimensions()[2] * piValue) / tubeDimensions()[2]);
        tubeDrawer.addLine(line1_x1, line1_y1, line1_x2, line1_y2);
        tubeDrawer.addLine(line2_x1, line2_y1, line2_x2, line2_y2);
        tubeDrawer.addArc(arcX, arcY, tubeDimensions()[2], (float) piValue, radsForArc);

        // TODO apply the correct colour to the tube based on the poc text game
        //
        if (!tubeStack.isEmpty()) {
            tubeStackCopy = (Stack<String>) tubeStack.clone();
            float[][] segCols = new float[4][4];
            int numSegs = 0;
            if (tubeStack.size() >= 4) {
                segCols[3] = getColours(tubeStackCopy.pop());
                numSegs += 1;
            }
            if (tubeStack.size() >= 3) {
                segCols[2] = getColours(tubeStackCopy.pop());
                numSegs += 1;
            }
            if (tubeStack.size() >= 2) {
                segCols[1] = getColours(tubeStackCopy.pop());
                numSegs += 1;
            }
            if (!tubeStack.isEmpty()) {
                segCols[0] = getColours(tubeStackCopy.pop());
                numSegs += 1;
            }
            float segmentHeight = tubeDimensions()[0] / (4); // Deduct 1 for the arc segment
            for (int i = 0; i < numSegs; i++) {
                if (i == 0) {
//                float startY = line1_y1 + (i - 1) * segmentHeight; // Start from 2nd segment (excluding arc)
                    tubeDrawer.fillSegment(segCols[i][0], segCols[i][1], segCols[i][2], segCols[i][3],
                            line1_x1, line1_y1, tubeDimensions()[1], segmentHeight - tubeDimensions()[2],
                            arcX, arcY, tubeDimensions()[2]);
//                    tubeDrawer.addFilledCircle(arcX, arcY, tubeDimensions()[2]);
//                    tubeDrawer.addFilledRectangle(line1_x1, line1_y1, tubeDimensions()[1], segmentHeight - tubeDimensions()[2]);
                } else {
                    float startY = (line1_y1 + (segmentHeight * (i))) - tubeDimensions()[2]; // Start from 2nd segment (excluding arc)
                    tubeDrawer.fillSegment(segCols[i][0], segCols[i][1], segCols[i][2], segCols[i][3],
                            line1_x1, startY, tubeDimensions()[1], segmentHeight);
//                    tubeDrawer.addFilledRectangle(line1_x1, startY, tubeDimensions()[1], segmentHeight);
                }

            }
        }
//        System.out.println(tubeDrawer);
        return tubeDrawer;
    }

    public static class TubeDrawer {
        //        private ShapeDrawer shapeDrawer;
//        private List<float[]> filledCircles;
        private final List<float[]> filledRectangles;
        private final List<float[]> lines;
        public int tubeNumber;
        private float[] arc;
        private final List<float[]> setColours;
        private float[] filledCircles;


        public TubeDrawer() {
//            filledCircles = new ArrayList<>();
            filledRectangles = new ArrayList<>();
            lines = new ArrayList<>();
//            arc = new ArrayList<>();
            setColours = new ArrayList<>();
        }


        public void addFilledCircle(float x, float y, float radius) {
            filledCircles = new float[]{x, y, radius};
        }

        public void addFilledRectangle(float x, float y, float width, float height) {
            filledRectangles.add(new float[]{x, y, width, height});
        }

        public void addLine(float x1, float y1, float x2, float y2) {
            lines.add(new float[]{x1, y1, x2, y2});
        }

        public void addArc(float x, float y, float radius, float start, float degrees) {
            arc = new float[]{x, y, radius, start, degrees};
        }

        public void setSegColour(float r, float g, float b, float a) {
            setColours.add(new float[]{r, g, b, a});
        }

        public void fillSegment(float r, float g, float b, float a,
                                float x, float y, float width, float height) {
            filledRectangles.add(new float[]{x, y, width, height});
            setColours.add(new float[]{r, g, b, a});
        }

        public void fillSegment(float r, float g, float b, float a,
                                float x, float y, float width, float height,
                                float cx, float cy, float radius) {

            filledRectangles.add(new float[]{x, y, width, height});
            setColours.add(new float[]{r, g, b, a});
            filledCircles = new float[]{cx, cy, radius};
        }


        public void draw(ShapeDrawer shapeDrawer) {

            if (!lines.isEmpty()) {
                shapeDrawer.setColor(Color.BLACK);
                for (float[] line : lines) {
                    shapeDrawer.line(line[0], line[1], line[2], line[3]);
                }
            }
            if (arc != null) {
                shapeDrawer.setColor(Color.BLACK);
                shapeDrawer.arc(arc[0], arc[1], arc[2], arc[3], arc[4]);

            }
//            if (setColours != null) {
//                for (float[] setColour : setColours) {
//                    shapeDrawer.setColor(setColour[0], setColour[1], setColour[2], setColour[3]);
//                }
//            }
//            if (filledCircles != null) {
//                int i = 0;
//                for (float[] filledCircle : filledCircles) {
//                    shapeDrawer.filledCircle(filledCircle[0], filledCircle[1], filledCircle[2]);
//                }
//            }
//            if (filledRectangles != null) {
//                for (float[] filledRectangles : filledRectangles){
//                    shapeDrawer.filledRectangle(filledRectangles[0], filledRectangles[1], filledRectangles[2], filledRectangles[3]);
//                }
//            }
            if (!setColours.isEmpty()) {
                for (int i = 0; i < setColours.size(); i++) {
                    shapeDrawer.setColor(setColours.get(i)[0], setColours.get(i)[1], setColours.get(i)[2], setColours.get(i)[3]);
                    if (filledCircles != null && i == 0) {
                        shapeDrawer.filledCircle(filledCircles[0], filledCircles[1], filledCircles[2]);
                    }
                    shapeDrawer.filledRectangle(filledRectangles.get(i)[0], filledRectangles.get(i)[1], filledRectangles.get(i)[2], filledRectangles.get(i)[3]);
                }
            }

        }

        public boolean contains(float x, float y) {
            float[][] tubeCol = new float[2][4];
            for (int i = 0; i < lines.size(); i++) {
                tubeCol[i] = lines.get(i);
            }
            float x1 = tubeCol[0][0];
            float y1 = tubeCol[0][1];
            float x2 = tubeCol[0][2];
            float y2 = tubeCol[0][3];
            float x3 = tubeCol[1][0];
            float y3 = tubeCol[1][1];
            float x4 = tubeCol[1][2];
            float y4 = tubeCol[1][3];

            return (x > x1 && x > x2 && y > y1 && y < y2) && (x < x3 && x < x4 && y > y3 && y < y4);

            // Check if the point is inside the rectangle using the cross product approach
//            return crossProduct(x, y, x1, y1, x2, y2) > 0 &&
//                    crossProduct(x, y, x2, y2, x3, y3) > 0 &&
//                    crossProduct(x, y, x3, y3, x4, y4) > 0 &&
//                    crossProduct(x, y, x4, y4, x1, y1) > 0;
        }

//        {x1, y1, x2, y2});
        public boolean containsgpt(float x, float y) {
            float[][] tubeCol = new float[2][4];
            for (int i = 0; i < lines.size(); i++) {
                tubeCol[i] = lines.get(i);
            }
            float x1 = tubeCol[0][0];
            float y1 = tubeCol[0][1] - arc[2];
            float x2 = tubeCol[0][2];
            float y2 = tubeCol[0][3];
            float x3 = tubeCol[1][0];
            float y3 = tubeCol[1][1] - arc[2];
            float x4 = tubeCol[1][2];
            float y4 = tubeCol[1][3];

            // Check if the point is inside the rectangle using the cross product approach
            return crossProduct(x, y, x1, y1, x2, y2) > 0 &&
                    crossProduct(x, y, x2, y2, x3, y3) > 0 &&
                    crossProduct(x, y, x3, y3, x4, y4) > 0 &&
                    crossProduct(x, y, x4, y4, x1, y1) > 0;
        }

        // Helper method to calculate cross product of vectors (AB, AP)
        private static float crossProduct(float Ax, float Ay, float Bx, float By, float Px, float Py) {
            return (Bx - Ax) * (Py - Ay) - (By - Ay) * (Px - Ax);
        }

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
                return new float[]{0.5f, 0.5f, 0.5f, 1};
        }
    }

    //TODO neeed to fix the dyanmic tube co ords and then ask bard to re - write it
    public float[][] getTubeCoOrds(int tubeCount) {
        float x = 0;
        float y = 0;
        float x1 = 0;
//        float x2;

//        float[] xy = new float[2];
        float[][] tubeCoOrds = new float[tubeCount][2];
        switch (tubeCount) {
            case 3:
                y = (float) Constants.SCREEN_HEIGHT / 2;
//                    y =  (float) 500;
                x = (float) Constants.SCREEN_WIDTH / (tubeCount + 1);
                for (int i = 0; i < tubeCount; i++) {
                    tubeCoOrds[i][0] = x * (i + 1);
                    tubeCoOrds[i][1] = y;
                }
                break;
            case 4:
                y = (float) Constants.SCREEN_HEIGHT / 2;
//                    y =  (float) 500;
                x = (float) Constants.SCREEN_WIDTH / (tubeCount + 1);
                for (int i = 0; i < tubeCount; i++) {
                    tubeCoOrds[i][0] = x * (i + 1);
                    tubeCoOrds[i][1] = y;
                }
                break;
            case 5:
                y = (float) Constants.SCREEN_HEIGHT / 3;
                x = (float) Constants.SCREEN_WIDTH / 4;
                x1 = (float) Constants.SCREEN_WIDTH / 3;
                for (int i = 0; i < tubeCount; i++) {
                    if (i < 3) {
                        tubeCoOrds[i][0] = x * (i + 1);
                        tubeCoOrds[i][1] = y;
                    } else {
                        tubeCoOrds[i][0] = x1 * (i + 1 - 3);
                        tubeCoOrds[i][1] = y * 2;
                    }
                }
                break;
            case 6:
                y = (float) Constants.SCREEN_HEIGHT / 3;
                x = (float) Constants.SCREEN_WIDTH / 4;
                for (int i = 0; i < tubeCount; i++) {
                    if (i < 3) {
                        tubeCoOrds[i][0] = x * (i + 1);
                        tubeCoOrds[i][1] = y;
                    } else {
                        tubeCoOrds[i][0] = x * (i + 1 - 3);
                        tubeCoOrds[i][1] = y * 2;
                    }
                }
                break;
        }
        return tubeCoOrds;
    }


    public float[][] getTubeCoOrds3(int tubeCount) {
        float x = 0;
        float y = 0;
        float x1 = 0;
//        float x2;

//        float[] xy = new float[2];
        float[][] tubeCoOrds = new float[tubeCount][2];
        switch (tubeCount) {
            case 3:
                y = (float) Constants.SCREEN_HEIGHT / 2;
//                    y =  (float) 500;
                x = (float) Constants.SCREEN_WIDTH / (tubeCount + 1);
                for (int i = 0; i < tubeCount; i++) {
                    tubeCoOrds[i][0] = x * (i + 1);
                    tubeCoOrds[i][1] = y;
                }
                break;
            case 5:
                y = (float) Constants.SCREEN_HEIGHT / 3;
                x = (float) Constants.SCREEN_WIDTH / 4;
                x1 = (float) Constants.SCREEN_WIDTH / 3;
                for (int i = 0; i < tubeCount; i++) {
                    if (i < 2) {
                        tubeCoOrds[i][0] = x * (i + 1);
                        tubeCoOrds[i][1] = y;
                    } else {
                        tubeCoOrds[i][0] = x1 * (i + 1);
                        tubeCoOrds[i][1] = y * 2;
                    }
                }
                break;
            case 6:
                y = (float) Constants.SCREEN_HEIGHT / 3;
                x = (float) Constants.SCREEN_WIDTH / 4;
                for (int i = 0; i < tubeCount; i++) {
                    if (i < 3) {
                        tubeCoOrds[i][0] = x * (i + 1);
                        tubeCoOrds[i][1] = y;
                    } else {
                        tubeCoOrds[i][0] = x * (i + 1 - 3);
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


