package com.panagiotis.nevmareborn;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MiningCaveScreen extends ScreenAdapter {

    private ShapeRenderer shapeRenderer;
    private Array<Rectangle> platforms;
    private Texture background;
    private Player player;
    private OrthographicCamera camera;
    private SpriteBatch batch;

    @Override
    public void show() {
        platforms = new Array<>();

        // Πλατφόρμες για τεστ
        platforms.add(new Rectangle(0, 100, 800, 20));     // έδαφος
        platforms.add(new Rectangle(200, 200, 150, 20));   // χαμηλή
        platforms.add(new Rectangle(450, 300, 200, 20));   // ψηλότερη
        platforms.add(new Rectangle(650, 380, 120, 20));   // ακόμα πιο ψηλά

        background = new Texture("cavebackground.png");
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        player = new Player(platforms);
    }

    @Override
    public void render(float delta) {
        // Καθαρισμός οθόνης
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        player.update(delta);

        // Ζωγράφισε background και player
        batch.begin();
        batch.draw(background, 0, 0, 800, 480);
        player.render(batch);
        batch.end();

        // Ζωγράφισε πλατφόρμες με ShapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        for (Rectangle platform : platforms) {
            shapeRenderer.rect(platform.x, platform.y, platform.width, platform.height);
        }
        shapeRenderer.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        background.dispose();
        shapeRenderer.dispose();
    }
}
