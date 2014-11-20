package com.yarik.game;

/**
 * Created by Ярослав on 13.11.2014.
 */

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.graphics.Color.WHITE;


public class GameScreen implements Screen{

    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;

    private OrthographicCamera cam;
    private SpriteBatch batch;
    private SpriteBatch batch2;

    private Sprite mapSprite;
    private float rotationSpeed;

    Texture player;
    float PLAYER_X = WORLD_WIDTH/2;
    float PLAYER_Y = WORLD_HEIGHT/2;

    float move = (float) 0.3;

    float w;
    float h;

    Array<Rectangle> coin = new Array<Rectangle>();
    Texture coinTexture;

    Rectangle bucket;

    BitmapFont font;
    BitmapFont fontButton;

    float fontX = 10;
    float fontY;

    int LEFT_COIN = 10;

    TextButton up, down, left, right;
    Stage stage;

    boolean isUP, isDOWN, isLEFT, isRIGHT = false;
    int buutonSize = 80;
    int buutonMargin = 10;

    public GameScreen() {
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        font = new BitmapFont();
        fontButton = font;
        font.setColor(WHITE);
        fontButton.setScale(1);
        fontY = h;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        TextureAtlas atlas = new TextureAtlas("ui/buutons.pack");
        Skin skin = new Skin(atlas);
        TextButton.TextButtonStyle tattle = new TextButton.TextButtonStyle();
        tattle.up = skin.getDrawable("button");
        tattle.down = skin.getDrawable("button_up");
        tattle.pressedOffsetX = 1;
        tattle.pressedOffsetY = -1;
        tattle.font = fontButton;

        up = new TextButton("U",tattle);
        up.setPosition(buutonSize+buutonMargin,buutonSize+buutonMargin);
        up.setSize(buutonSize, buutonSize);
        up.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button){
                isUP = false;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
                isUP = true;
                return true;
            }
        });

        down = new TextButton("D",tattle);
        down.setPosition(buutonSize+buutonMargin,buutonMargin);
        down.setSize(buutonSize, buutonSize);
        down.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button){
                isDOWN = false;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
                isDOWN = true;
                return true;
            }
        });

        left = new TextButton("L",tattle);
        left.setPosition(buutonMargin,buutonSize/2+buutonMargin);
        left.setSize(buutonSize, buutonSize);
        left.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button){
                isLEFT = false;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
                isLEFT = true;
                return true;
            }
        });

        right = new TextButton("R",tattle);
        right.setPosition(buutonSize*2+buutonMargin,buutonSize/2+buutonMargin);
        right.setSize(buutonSize, buutonSize);
        right.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button){
                isRIGHT = false;
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
                isRIGHT = true;
                return true;
            }
        });

        stage.addActor(up);
        stage.addActor(down);
        stage.addActor(left);
        stage.addActor(right);


        rotationSpeed = 0.5f;

        mapSprite = new Sprite(new Texture(Gdx.files.internal("sc_map.png")));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);
        cam = new OrthographicCamera(30, 30 * (h / w));
        cam.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2, 0);

        cam.update();

        batch = new SpriteBatch();

        font.setScale((float)2.5);

        Pixmap pixmap = new Pixmap( 64, 64, Pixmap.Format.RGBA8888 );
        pixmap.setColor( 0, 1, 0, 0.75f );
        pixmap.fillRectangle(0, 0, 64, 64);
        player = new Texture( pixmap );


        bucket = new Rectangle();
        bucket.x = WORLD_WIDTH/2;
        bucket.y = WORLD_HEIGHT/2;
        bucket.width = 1;
        bucket.height = 1;

        Pixmap coinmap = new Pixmap( 64, 64, Pixmap.Format.RGBA8888 );
        coinmap.setColor( 0, 1, 0, 1 );
        coinmap.fillCircle( 32, 32, 32 );
        coinTexture = new Texture( coinmap );


        for(int i=0;i<10;i++){
            Rectangle raindrop = new Rectangle();
            raindrop.x = MathUtils.random(16, WORLD_WIDTH - 16);
            raindrop.y = MathUtils.random(16, WORLD_HEIGHT-16);
            raindrop.width = 1;
            raindrop.height = 1;
            coin.add(raindrop);
        }

        batch2 = new SpriteBatch();

    }


    @Override
    public void render(float delta) {

        bucket.x = PLAYER_X;
        bucket.y = PLAYER_Y;

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
         mapSprite.draw(batch);
        batch.end();

        batch.begin();
           batch.draw(player,PLAYER_X,PLAYER_Y,1,1);
        batch.end();


        stage.act(delta);
        stage.draw();

        for(int i = 0; i < coin.size; i++){
            batch.begin();
               batch.draw(coinTexture,coin.get(i).x,coin.get(i).y,1,1);
            batch.end();

        }

        for(int i = 0; i < coin.size; i++){

            Rectangle raindrop = coin.get(i);
            if(raindrop.overlaps(bucket)) {
                LEFT_COIN--;
                coin.removeIndex(i);
            }

        }


        if(LEFT_COIN != 0){
            handleInput();
            batch2.begin();
              font.draw(batch2, "Left: "+LEFT_COIN, fontX, fontY);
            batch2.end();
        }else{
            font.setScale(3);
            fontX = w/2-w/9;
            fontY = h/2+(h/4)/2;
            batch2.begin();
              font.draw(batch2, "YOU WIN!", fontX, fontY);
            batch2.end();
        }

    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            cam.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || isLEFT) {
            cam.translate(-move, 0, 0);
            if(PLAYER_X > 15){
                PLAYER_X -= move;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || isRIGHT) {
            cam.translate(move, 0, 0);
            if(PLAYER_X < WORLD_WIDTH-15){
                PLAYER_X += move;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || isDOWN) {
            cam.translate(0, -move, 0);
            if(PLAYER_Y > 30 * (h / w)/2){
                PLAYER_Y -= move;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || isUP) {
            cam.translate(0, move, 0);
            if(PLAYER_Y < WORLD_HEIGHT-30 * (h / w)/2){
                PLAYER_Y += move;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.rotate(-rotationSpeed, 0, 0, 1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            cam.rotate(rotationSpeed, 0, 0, 1);
        }


        float effectiveViewportWidth = cam.viewportWidth * cam.zoom;
        float effectiveViewportHeight = cam.viewportHeight * cam.zoom;

        cam.zoom = MathUtils.clamp(cam.zoom, 0.1f, 100/cam.viewportWidth);
        cam.position.x = MathUtils.clamp(cam.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
        cam.position.y = MathUtils.clamp(cam.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);

    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        mapSprite.getTexture().dispose();
        batch.dispose();
    }

    @Override
    public void pause() {
    }


}