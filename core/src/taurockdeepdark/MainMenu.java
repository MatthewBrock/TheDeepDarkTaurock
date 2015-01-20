package taurockdeepdark;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Matthew Brock on 07/01/2015.
 */
public class MainMenu extends Game {
    Loader loader;
    ScreenControl screenControl;
    SpriteBatch sbBatch;
    Texture tBack;
    Stage stage;
    BitmapFont font;
    TextButton tbNewGame,tbLoadGame,tbOptions,tbCredits;
    TextButton.TextButtonStyle tbsNewGame;
    Skin skNewGame;
    TextureAtlas taNewGame;
    int nSHeight, nSWidth;


    @Override
    public void render() {
        Gdx.input.setInputProcessor(stage);
        sbBatch.begin();
        sbBatch.draw(tBack, 0, 0, nSWidth, nSHeight);
        sbBatch.end();
        stage.draw();

    }
    public void setScreenControl(ScreenControl screenControl_){
        screenControl = screenControl_;
    }
    public void setLoader(Loader loader_){
        loader = loader_;
    }

    @Override
    public void create() {
        loader = new Loader();
        nSHeight = Gdx.graphics.getHeight();
        nSWidth = Gdx.graphics.getWidth();
        sbBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setScale(nSWidth * 3 / 1794, nSHeight * 3 / 1080);
        stage = new Stage(){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    Gdx.app.exit();
                }
                return super.keyDown(keyCode);
            }
        };
        tBack = new Texture(Gdx.files.internal("MenuBack.png"));

        skNewGame = new Skin(); //setting up the button
        taNewGame = new TextureAtlas(Gdx.files.internal("MenuButton.pack"));
        skNewGame.addRegions(taNewGame);
        tbsNewGame = new TextButton.TextButtonStyle();
        tbsNewGame.font = font;
        tbsNewGame.up = skNewGame.getDrawable("MenuButtonUp");
        tbsNewGame.down = skNewGame.getDrawable("MenuButtonUp");
        //tbsNewGame.checked = skNewGame.getDrawable("MenuButtonUp");
        tbNewGame = new TextButton("New Game", tbsNewGame);
        tbNewGame.setSize(nSWidth * 500 / 1794, nSHeight * 100 / 1080);
        tbNewGame.setPosition(nSWidth / 2 - tbNewGame.getWidth() / 2, nSHeight / 2+nSHeight * 50 / 1080);
        tbNewGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    screenControl.setnScreen(2);

                return true;
            }
        });
        stage.addActor(tbNewGame);


        tbLoadGame = new TextButton("Load Game", tbsNewGame);
        tbLoadGame.setSize(nSWidth * 500 / 1794, nSHeight * 100 / 1080);
        tbLoadGame.setPosition(nSWidth / 2 - tbNewGame.getWidth() / 2, nSHeight / 2- nSHeight * 100 / 1080);
        tbLoadGame.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                loader.load();
                screenControl.setnScreen(3);
                return true;
            }
        });
        stage.addActor(tbLoadGame);
        tbOptions = new TextButton("Options", tbsNewGame);
        tbOptions.setSize(nSWidth * 500 / 1794, nSHeight * 100 / 1080);
        tbOptions.setPosition(nSWidth / 2 - tbNewGame.getWidth() / 2, nSHeight / 2- nSHeight * 250 / 1080);
        tbOptions.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });
        stage.addActor(tbOptions);

        tbCredits = new TextButton("Credits", tbsNewGame);
        tbCredits.setSize(nSWidth * 500 / 1794, nSHeight * 100 / 1080);
        tbCredits.setPosition(nSWidth / 2 - tbNewGame.getWidth() / 2, nSHeight / 2- nSHeight * 400 / 1080);
        tbCredits.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }
        });
        stage.addActor(tbCredits);



    }
}
