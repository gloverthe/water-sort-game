package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Stack;

public class MainGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	private OrthographicCamera camera;
	private ExtendViewport viewport;
	private Vector3 lastTouchPos = new Vector3(); // Store the last processed touch position

	private ShapeRenderer shapes;
	public final static float SCALE = 32f;
	public final static float INV_SCALE = 1.f/SCALE;
	// this is our "target" resolution, note that the window can be any size, it is not bound to this one
	public final static float VP_WIDTH = Constants.SCREEN_WIDTH * INV_SCALE;
	public final static float VP_HEIGHT = Constants.SCREEN_HEIGHT * INV_SCALE;
	Texture img;
	Texture texture;
	BitmapFont font;
	ShapeRenderer shapeRenderer;
	ShapeDrawer shapeDrawer;
	Array<ReturnTubes.TubeDrawer> tubesToDraw;
	ReturnTubes returnTubes;
	DrawTube drawTube;

	public static Sound pouringFX;
	//ints to capture which tube
	private int tubePressed1 = 99;
	private int tubePressed2 = 99;


//	TestTubeSegments testTubeSegments;

	double piValue = Math.PI;


	public Stack<String>[] currentLevel;

	
	@Override
	public void create () {

		camera = new OrthographicCamera();
		camera.setToOrtho(false,  Constants.SCREEN_WIDTH , Constants.SCREEN_HEIGHT);


		// pick a viewport that suits your thing, ExtendViewport is a good start
		viewport = new ExtendViewport(VP_WIDTH, VP_HEIGHT, camera);
		// ShapeRenderer so we can see our touch point
		shapes = new ShapeRenderer();
		Gdx.input.setInputProcessor(this);


		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Montserrat-Regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size =50;
		font = generator.generateFont(parameter);
		generator.dispose();

		//SFX
		pouringFX = Gdx.audio.newSound(Gdx.files.internal("sfx/short_pouring_sound.mp3"));

		//shapedrawer
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.drawPixel(0, 0);
		texture = new Texture(pixmap); //remember to dispose of later
		pixmap.dispose();
		TextureRegion region = new TextureRegion(texture, 0, 0, 1, 1);
		shapeDrawer = new ShapeDrawer(batch, region);
		returnTubes = new ReturnTubes();



		System.out.println("testTubeSegments loaded");
		currentLevel = LevelReader.getPuzzle("level3");
//		System.out.println(currentLevel[0]);
		for (int i = 0; currentLevel.length > i; i++) {
			System.out.println("Tube " + i + " Centre X:" + returnTubes.getTubeCoOrds(currentLevel.length)[i][0] + " Centre Y:" + returnTubes.getTubeCoOrds(currentLevel.length)[i][1]);
			System.out.println("Tube " + i + " : " + currentLevel[i]);;
		}

		//returns is initialised to generate the tube co-ordinates



		//initialise the array of tubes to draw to prevent null pointer exception
//		tubesToDraw = new Array<>();



	}


	@Override
	public void render () {
		// this array needs to be initialised here so in so it gets recreated every frame
		tubesToDraw = new Array<>();
		int raiseTube = 0;

		ScreenUtils.clear(1, 0, 0, 1);
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1); // Set RGBA values (0.5f, 0.5f, 0.5f) for a gray color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);




		shapeDrawer.update();
		for (int i = 0; currentLevel.length > i; i++) {
			if (i == tubePressed1) {
				raiseTube = 30;
			} else {
				raiseTube = 0;
			}
			ReturnTubes.TubeDrawer tube = ReturnTubes.renderTube(returnTubes.getTubeCoOrds(currentLevel.length)[i][0],
					returnTubes.getTubeCoOrds(currentLevel.length)[i][1] + raiseTube,
					i,
					currentLevel[i]);
			tubesToDraw.add(tube);
		}
		batch.begin();


//		batch.draw(img, 0, 0);
		font.draw(batch , "Level : 1" ,10, 1090);
//		if tubePressed2 != 99 {
//			font.draw(batch , "Tube 1 : " + tubePressed1 + ", Tube 2 : " + tubePressed2 ,10, 10);
//		}
//		if tubePressed1 != 99 {
//			font.draw(batch , "Tube 1 : " + tubePressed1 ,10, 40);
//		}



		for (ReturnTubes.TubeDrawer tube : tubesToDraw) {
			tube.draw(shapeDrawer);
		}


		batch.end();
		if (Gdx.input.isTouched()) {
//			Vector3 touchPos = new Vector3();
			tp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//			camera.unproject(tp);
			if (!tp.equals(lastTouchPos)) {
				// Print the coordinates only if they are different from the last frame
				System.out.println("Touch Position: " + tp.x + ", " + tp.y);

				// Draw a circle at the touch position
				batch.begin();
				float circleRadius = 20;
				shapeDrawer.setColor(0, 0, 1, 1); // Blue color
				shapeDrawer.filledCircle(tp.x, Constants.SCREEN_HEIGHT- tp.y, circleRadius);
				batch.end();
				for (ReturnTubes.TubeDrawer tube : tubesToDraw) {
					if (tube.contains(tp.x, (Constants.SCREEN_HEIGHT - tp.y))) {
						System.out.println("Tube " + tube.tubeNumber + " has been touched");
						if (tubePressed1 == 99) {
							tubePressed1 = tube.tubeNumber;
							System.out.println("Tube 1 pressed");
						} else if (tubePressed2 == 99 & tubePressed1 != tube.tubeNumber) {
							tubePressed2 = tube.tubeNumber;
							System.out.println("Tube 2 pressed");
						}
					}
				}
					if (tubePressed1 != 99 & tubePressed2 != 99) {
						System.out.println("Tube 1 = " + tubePressed1 + ", Tube 2 = " + tubePressed2);
						if (tubePressed1 != tubePressed2) {
							if (WaterSortPuzzle.isValidMove(currentLevel, tubePressed1, tubePressed2)) {
								System.out.println("Move is valid");
								WaterSortPuzzle.pourTube(currentLevel, tubePressed1, tubePressed2);
								pouringFX.play();
								if (WaterSortPuzzle.areAllElementsSame(currentLevel[tubePressed2])) {
									WaterSortPuzzle.emptyTube(currentLevel[tubePressed2]);
									System.out.println("Tube " + tubePressed2 + " has been emptied");
								}
								Gdx.graphics.requestRendering();
							}
						}
						tubePressed1 = 99;
						tubePressed2 = 99;
					}

				// Update the lastTouchPos
				lastTouchPos.set(tp);


//			}


//		if (Gdx.input.isTouched()) {
//			Vector3 touchPos = new Vector3();
//			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//			System.out.println("X coord = " + touchPos.x + ", Y coord = " + (Constants.SCREEN_HEIGHT- touchPos.y));
//			camera.unproject(touchPos);
//			for (ReturnTubes.TubeDrawer tube : tubesToDraw) {
//				if (tube.contains(touchPos.x, (Constants.SCREEN_HEIGHT- touchPos.y))) {
//					System.out.println("Tube " + tube.tubeNumber  + " has been touched");
//				}
//			}
		}



//			System.out.println("X coord = " + tp.x + ", Y coord = " + (tp.y));


		}


//		font.draw(batch , "x: " + tp.x + ", y: " + tp.y ,10, 10);


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		font.dispose();
		shapeRenderer.dispose();
		texture.dispose();
		drawTube.dispose();
		returnTubes.dispose();
		shapes.dispose();




	}
	Vector3 tp = new Vector3();
	boolean dragging;

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// ignore if its not left mouse button or first touch pointer
//		if (button != Input.Buttons.LEFT || pointer > 0) return false;
		camera.unproject(tp.set(screenX, screenY, 0));
		dragging = true;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
		return false;
	}
//	@Override public void resize (int width, int height) {
//		// viewport must be updated for it to work properly
//		viewport.update(width, height, true);
//	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
