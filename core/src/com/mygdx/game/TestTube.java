package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class TestTube {

    ShapeDrawer shapeDrawer;
    double piValue = Math.PI;
    Texture texture;

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

    public void drawTube (float centreX, float centreY) {

        float line1_x1 = centreX - 50;
        float line1_x2 = centreX - 50;
        float line1_y1 = centreY - 150;
        float line1_y2 = centreY + 200;

        float line2_x1 = centreX + 50;
        float line2_x2 = centreX + 50;
        float line2_y1 = centreY - 150;
        float line2_y2 = centreY + 200;

        float arcX = centreX;
        float arcY = centreY - 150;


        float radsForArc = (float) ((50 * piValue) / 50);
        shapeDrawer.line(line1_x1, line1_y1, line1_x2, line1_y2);
        shapeDrawer.line(line2_x1, line2_y1, line2_x2, line2_y2);
        shapeDrawer.arc(arcX, arcY, 50, (float) piValue, radsForArc);
        // TODO apply the correct colour to the tube based on the poc text game
        //
        float segmentHeight = 400f / (4); // Deduct 1 for the arc segment
        for (int i = 0; i < 4; i++) {
            switch (i % 4) { // Alternate colors for segments
                case 0:
                    shapeDrawer.setColor(0.8f, 0.1f, 0.1f, 1); // Red
                    break;
                case 1:
                    shapeDrawer.setColor(0.1f, 0.8f, 0.1f, 1); // Green
                    break;
                case 2:
                    shapeDrawer.setColor(0.1f, 0.1f, 0.8f, 1); // Blue
                    break;
                case 3:
                    shapeDrawer.setColor(0.8f, 0.8f, 0.1f, 1); // Yellow
                    break;
            }
            if (i == 0) {
                float startY = line1_y1 + (i - 1) * segmentHeight; // Start from 2nd segment (excluding arc)
                shapeDrawer.filledCircle(arcX, arcY, 50);
                shapeDrawer.filledRectangle(line1_x1, line1_y1, 100, segmentHeight-50);
            }
            else {
                float startY = (line1_y1 + (segmentHeight * (i))) -50; // Start from 2nd segment (excluding arc)
                shapeDrawer.filledRectangle(line1_x1, startY, 100, segmentHeight);
            }


        }

//    public ArrayList<Float> tubeCoOrds (float tubeCentreX, float tubeCentreY) {
//
//
//        }


    }}

//    public ArrayList


