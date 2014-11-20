package com.yarik.game.screen;


import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.yarik.game.tween.ActorAccessor;
import com.yarik.game.tween.SpriteAccessor;

public class Splash implements Screen {

    private TweenManager tweenManager;
    SpriteBatch SplashBatch;
    BitmapFont font;
    int WIDHT,HEIGHT;
    Texture splashTexture;
    Sprite splash;


    @Override
    public void show() {
        SplashBatch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        WIDHT = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        splashTexture = new Texture(Gdx.files.internal("yarik.png"));
        splash = new Sprite(splashTexture);
        splash.setSize(WIDHT/5,HEIGHT/5);
        splash.setPosition(WIDHT/2-(WIDHT/5)/2,HEIGHT/2-(HEIGHT/5)/2);

        Tween.set(splash,SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1,.5f).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        }).start(tweenManager);

    }

    @Override
    public void render(float delta) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            tweenManager.update(delta);

            SplashBatch.begin();
                splash.draw(SplashBatch);
            SplashBatch.end();

            if (Gdx.input.isTouched()) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                dispose();
            }
    }


    @Override
    public void resize(int width, int height) {

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
        SplashBatch.dispose();
        splash.getTexture().dispose();
    }


}
