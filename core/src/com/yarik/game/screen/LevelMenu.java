package com.yarik.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

/**
 * Created by Ярослав on 18.11.2014.
 */
public class LevelMenu implements Screen {

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private List list;
    private ScrollPane scrollPane;
    private TextButton play,back;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        atlas = new TextureAtlas(Gdx.files.internal("ui/atlas.pack"));
        skin = new Skin(Gdx.files.internal("ui/menuSkin.json"),atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        list = new List(skin);
        list.setItems(new String[]{"one","two","three"});

        play = new TextButton("PLAY",skin);
        play.pad(15);
        back = new TextButton("BACK",skin);
        back.pad(10);

        scrollPane = new ScrollPane(list,skin);

        table.add().width(table.getWidth() / 3);
        table.add("SELECT LEVEL").width(table.getWidth()/3);
        table.add().width(table.getWidth()/3).row();
        table.add(scrollPane);
        table.add(play);
        table.add(back);
        stage.addActor(table);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
        skin.dispose();
    }
}
