package com.mygdx.drop.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.UI.GameUI;

import java.util.Iterator;

import static com.mygdx.drop.Audio.dropSound;
import static com.mygdx.drop.Audio.rainMusic;

public class GameScreen implements Screen {

    //Camera Variable
    private OrthographicCamera cam;
    private final DropGame dropGame;
    private Viewport viewport;

    //Texture Variable
    private Texture dropImage;
    private Texture bucketImage;

    //Bucket and Raindrops Variable
    private Rectangle bucket;
    private Array<Rectangle> raindrops;

    private long lastDropTime;
    public static int fallSpeed = 200;
    public static int time = 1000000000;

    //UI Variables
    private GameUI gameUI;

    public GameScreen(final DropGame dropGame) {
        this.dropGame = dropGame;

        //define SpriteBatch and Cam
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);

        //Load Texture
        dropImage = new Texture("Texture/drop.png");
        bucketImage = new Texture("Texture/bucket.png");

        //Load Audio

        //set music play and loop
        rainMusic.setLooping(true);
        rainMusic.play();

        //Define Bucket
        bucket = new Rectangle();
        bucket.x = 800/2 - 64/2;
        bucket.y = 20;
        bucket.width = 64;
        bucket.height = 64;

        //Define Drop and spawn it random
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

        //Define GameUI
        gameUI = new GameUI(dropGame);

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public void show() {

    }

    @Override
    public void render (float dt) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        this.update(dt);
        gameUI.update(dt);

        //Draw Bucket and RainDrops
        dropGame.batch.setProjectionMatrix(cam.combined);
        dropGame.batch.begin();
        dropGame.batch.draw(bucketImage, bucket.x, bucket.y);
        for(Rectangle raindrop: raindrops) {
            dropGame.batch.draw(dropImage, raindrop.x, raindrop.y);
        }
        dropGame.batch.end();

        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPos);
            bucket.x = touchPos.x - 64 / 2;
        }

        gameUI.stage.act(dt);
        gameUI.stage.draw();

        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
            Rectangle raindrop = iter.next();
            raindrop.y -= fallSpeed * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0){
                iter.remove();
                gameUI.lostHP();
            }
            if(raindrop.overlaps(bucket)) {
                dropSound.play();
                gameUI.addScore();
                iter.remove();
            }
        }

    }

    public void update(float dt){
        if(TimeUtils.nanoTime() - lastDropTime > time){
            spawnRaindrop();
        }

        cam.update();
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

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        dropImage.dispose();
        bucketImage.dispose();
        rainMusic.dispose();
        dropSound.dispose();
    }
}
