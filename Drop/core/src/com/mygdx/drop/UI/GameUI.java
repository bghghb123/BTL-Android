package com.mygdx.drop.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.drop.Audio;
import com.mygdx.drop.DropGame;
import com.mygdx.drop.Screen.GameOverScreen;

import static com.mygdx.drop.SaveGame.preferences;
import static com.mygdx.drop.Screen.GameScreen.fallSpeed;
import static com.mygdx.drop.Screen.GameScreen.time;

public class GameUI {

    //Stage and Viewport Variables
    public Stage stage;
    private Viewport viewport;
    private DropGame dropGame;

    private Table table;

    private Label textScore;
    private Label textHP;
    private int score = 0;
    private int HP = 3;
    private int checkpoint = 10;

    public GameUI(DropGame dropGame){
        this.dropGame = dropGame;
        //Create new viewport with new camera for UI
        viewport = new ScreenViewport(new OrthographicCamera());

        //Define new Stage and set Input Processor for Handle input event
        stage = new Stage(viewport,dropGame.batch);
        Gdx.input.setInputProcessor(stage);

        textScore = new Label(String.format("Score: %d",score),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("StardewValley.fnt"),false), Color.WHITE));
        textHP = new Label(String.format("Life: %d", HP),new Label.LabelStyle(new BitmapFont(Gdx.files.internal("StardewValley.fnt"),false), Color.WHITE));
        textScore.setFontScale(2);
        textHP.setFontScale(2);

        table = new Table();
        table.setFillParent(true);
        table.top().padTop(10);
        table.add(textScore).expandX();
        table.add(textHP).expandX();

        stage.addActor(table);
    }

    public void update(float delta){
        if(checkpoint != 1000){
            if(score > checkpoint){
                fallSpeed = fallSpeed+50;
                checkpoint = checkpoint + 10;
                time = time - time/10;
            }
        }
        if(HP == 0){
            if(preferences.getInteger("HighScore") < score){
                preferences.putInteger("HighScore",score).flush();
            }
            Audio.rainMusic.pause();
            fallSpeed = 200;
            time = 1000000000;

            dropGame.setScreen(new GameOverScreen(dropGame));
        }
    }

    public void addScore(){
        score = score+1;
        textScore.setText(String.format("Score: %d",score));
    }

    public void lostHP(){
        HP = HP - 1;
        textHP.setText(String.format("Life: %d", HP));
    }
}

