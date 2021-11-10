package com.mygdx.drop.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.UI.MainMenuUI;

public class MainMenuScreen implements Screen {

    private final DropGame dropGame;

    //Camera and Viewport Variables
    private OrthographicCamera cam;
    private Viewport viewport;

    private MainMenuUI mainMenuUI;

    public MainMenuScreen(DropGame dropGame) {
        this.dropGame = dropGame;

        cam = new OrthographicCamera();
        viewport = new FillViewport(800,480,cam);

        mainMenuUI = new MainMenuUI(dropGame);
    }

    @Override
    public void show() {
        
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        cam.update();
        dropGame.batch.setProjectionMatrix(cam.combined);

        mainMenuUI.stage.act(delta);
        mainMenuUI.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        mainMenuUI.stage.dispose();
    }
}
