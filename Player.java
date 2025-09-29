package com.panagiotis.nevmareborn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player {

    private Array<Rectangle> platforms;

    private enum State { IDLE, WALKING }
    private State currentState = State.IDLE;

    private final float MOVE_SPEED = 150f;
    private final float scale = 1.0f;

    private Vector2 position, velocity;
    private Rectangle bounds;

    private Animation<TextureRegion> idleAnim, walkAnim;
    private TextureRegion currentFrame;

    private Texture idleSheet, walkSheet;

    private float frameTime;

    public Player(Array<Rectangle> platforms) {
        this.platforms = platforms;

        position = new Vector2(100, 100);
        velocity = new Vector2(0, 0);
        bounds = new Rectangle(position.x, position.y, 64, 64);

        // Φορτώνει από τον φάκελο sprites/
        idleSheet = new Texture(Gdx.files.internal("sprites/idle.png"));
        walkSheet = new Texture(Gdx.files.internal("sprites/walk.png"));

        idleAnim = createAnimation(idleSheet, 3, 0.2f);
        walkAnim = createAnimation(walkSheet, 3, 0.1f);
    }

    private Animation<TextureRegion> createAnimation(Texture sheet, int frameCount, float frameDuration) {
        TextureRegion[][] tmp = TextureRegion.split(sheet, 64, 64);
        TextureRegion[] frames = new TextureRegion[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = tmp[0][i];
        }
        return new Animation<>(frameDuration, frames);
    }

    public void update(float delta) {
        handleInput();
        frameTime += delta;

        if (Math.abs(velocity.x) > 0) {
            currentState = State.WALKING;
        } else {
            currentState = State.IDLE;
        }

        position.x += velocity.x * delta;
        bounds.setPosition(position);
    }

    private void handleInput() {
        velocity.x = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            velocity.x = -MOVE_SPEED;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            velocity.x = MOVE_SPEED;
        }
    }

    public void render(SpriteBatch batch) {
        switch (currentState) {
            case WALKING:
                currentFrame = walkAnim.getKeyFrame(frameTime, true);
                break;
            case IDLE:
            default:
                currentFrame = idleAnim.getKeyFrame(frameTime, true);
                break;
        }
        batch.draw(currentFrame, position.x, position.y, 64 * scale, 64 * scale);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        idleSheet.dispose();
        walkSheet.dispose();
    }
}
