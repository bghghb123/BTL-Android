package com.mygdx.drop.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.UI.GameOverUI;

public class GameOverScreen implements Screen {
    private DropGame dropGame;
    //Camera and Viewport Variables
    private OrthographicCamera cam;
    private Viewport viewport;

    private GameOverUI gameOverUI;
    public GameOverScreen(DropGame dropGame) {
        this.dropGame = dropGame;

        cam = new OrthographicCamera();
        viewport = new FillViewport(800,480,cam);

        gameOverUI = new GameOverUI(dropGame);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        cam.update();
        dropGame.batch.setProjectionMatrix(cam.combined);

        gameOverUI.stage.act(delta);
        gameOverUI.stage.draw();

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
    public void dispose() {

    }
}
