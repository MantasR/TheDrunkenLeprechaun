package com.NapierDevSoc.TheDrunkenLeprechaun;

import java.util.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class DrunkenLeprechaun implements ApplicationListener {
	
	public static final int GAME_STATE_PLAY = 0;
	public static final int GAME_STATE_PAUSE = 1;
	public static final int GAME_STATE_ANIMATE = 2;
	
	private Rectangle[][] pavement;
	private Texture pavementTexture;
	
	public Screen screen;
	
	private OrthographicCamera camera;
	private SpriteBatch batch;
	
	
	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		int pavementSlabSize = 100;
		pavement = new Rectangle[3][(int)(w/pavementSlabSize + 1)];
		pavementTexture = new Texture(Gdx.files.internal("data/sidewalk_block_128x128.png"));
		
		for (int y=0; y < 3; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				pavement[y][x] = new Rectangle();
				pavement[y][x].width = pavementSlabSize;
				pavement[y][x].height = pavementSlabSize;
				pavement[y][x].x = pavementSlabSize * x;
				pavement[y][x].y = pavementSlabSize * y;
			}
		}
		
		camera = new OrthographicCamera(1, h/w);
		
		batch = new SpriteBatch();
		
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		animate_pavement();
		
		batch.end();
	}
	
	private void animate_pavement() {
		for (int y=0; y < pavement.length; y++) {
			for (int x=0; x < pavement[y].length; x++) {
				// Move pavement slabs left
				pavement[y][x].x -= 100 * Gdx.graphics.getDeltaTime();
				
				
				// If the fist slab is off the screen, change the x value to the end.
				if (pavement[y][x].x + pavement[y][x].width <= 0) {
					int max_x = 0;
					for (int xx=0; xx <  pavement[y].length; xx++) {
						if (pavement[y][max_x].x < pavement[y][xx].x)
							max_x = xx;
					}
					
					pavement[y][x].x = pavement[y][max_x].x + pavement[y][max_x].width + (x == 0 ? -2 : 0);
				}
				
				
				// Render the pavement slabs
        		batch.draw(pavementTexture,
        				pavement[y][x].x,
        				pavement[y][x].y,
        				pavement[y][x].width,
        				pavement[y][x].height);
			}
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}