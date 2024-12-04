package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class TestTubeSegments extends ApplicationAdapter {
//    private ShapeRenderer shapeRenderer;
    private float tubeWidth = 100; // Tube width
    private float tubeHeight = 400; // Tube height
    private float centerX; // Center X coordinate
    private float bottomY; // Bottom Y coordinate
    private int segments = 4; // Number of segments

//    @Override
    public void load(ShapeRenderer shapeRenderer) {
//        shapeRenderer = new ShapeRenderer();
        centerX = Gdx.graphics.getWidth() / 2f;
        bottomY = 100;
    }

//    @Override
    public void drawTestTubeSegments(ShapeRenderer shapeRenderer) {
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        shapeRenderer.begin(ShapeType.Filled);

        // Draw test tube body
        shapeRenderer.setColor(0.7f, 0.7f, 0.7f, 1);
        shapeRenderer.rect(centerX - tubeWidth / 2f, bottomY, tubeWidth, tubeHeight);

        // Draw the red arc (bottom of the tube)
        shapeRenderer.setColor(0.8f, 0.1f, 0.1f, 1); // Red
        shapeRenderer.arc(centerX, bottomY, tubeWidth / 2f, 0, -180, 50);

        // Fill the test tube with segments of different colors
        float segmentHeight = tubeHeight / (segments - 1); // Deduct 1 for the arc segment
        for (int i = 1; i < segments; i++) {
            switch (i % 4) { // Alternate colors for segments
                case 0:
                    shapeRenderer.setColor(0.8f, 0.1f, 0.1f, 1); // Red
                    break;
                case 1:
                    shapeRenderer.setColor(0.1f, 0.8f, 0.1f, 1); // Green
                    break;
                case 2:
                    shapeRenderer.setColor(0.1f, 0.1f, 0.8f, 1); // Blue
                    break;
                case 3:
                    shapeRenderer.setColor(0.8f, 0.8f, 0.1f, 1); // Yellow
                    break;
            }
            float startY = bottomY + (i - 1) * segmentHeight; // Start from 2nd segment (excluding arc)
            shapeRenderer.rect(centerX - tubeWidth / 2f, startY, tubeWidth, segmentHeight);

        }

//        shapeRenderer.end();
    }

    @Override
    public void dispose() {
//        shapeRenderer.dispose();
    }
}
