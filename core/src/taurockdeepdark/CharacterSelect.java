package taurockdeepdark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
 * Created by Matthew Brock on 08/01/2015.
 */
public class CharacterSelect extends Game {
    GameScreen gameScreen;
    ScreenControl screenControl;
    SpriteBatch sbBatch;
    Texture tBack, tRiffy, tBadLuck;
    Stage stage;
    BitmapFont font;
    TextButton tbRiffy, tbBadLuck;
    TextButton.TextButtonStyle tbsRiffy;
    Skin skRiffy;
    TextureAtlas taRiffy;
    int nSHeight, nSWidth;

    public void setScreenControl(ScreenControl screenControl_) {
        screenControl = screenControl_;
    }



    public void setGameScreen(GameScreen gameScreen_) {
        gameScreen = gameScreen_;
    }

    @Override
    public void create() {
        nSHeight = Gdx.graphics.getHeight();
        nSWidth = Gdx.graphics.getWidth();
        sbBatch = new SpriteBatch();
        font = new BitmapFont();
        font.setScale(nSWidth * 3 / 1794, nSHeight * 3 / 1080);
        stage = new Stage(){//http://gamedev.stackexchange.com/questions/28442/handle-input-processor-in-device-back-button-and-button-as-actor
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    screenControl.setnScreen(1);
                }
                return super.keyDown(keyCode);
            }
        };
        tBack = new Texture(Gdx.files.internal("MenuBack.png"));
        tRiffy = new Texture(Gdx.files.internal("Riffy1.png"));
        tBadLuck = new Texture(Gdx.files.internal("BadLuck1.png"));

        skRiffy = new Skin(); //setting up the button
        taRiffy = new TextureAtlas(Gdx.files.internal("MenuButton.pack"));
        skRiffy.addRegions(taRiffy);
        tbsRiffy = new TextButton.TextButtonStyle();
        tbsRiffy.font = font;
        tbsRiffy.up = skRiffy.getDrawable("MenuButtonUp");
        tbsRiffy.down = skRiffy.getDrawable("MenuButtonDown");
        tbsRiffy.checked = skRiffy.getDrawable("MenuButtonUp");
        tbRiffy = new TextButton("Riffy", tbsRiffy);
        tbRiffy.setSize(nSWidth * 500 / 1794, nSHeight * 100 / 1080);
        tbRiffy.setPosition(nSWidth / 2 - tbRiffy.getWidth() / 2 + nSWidth * 400 / 1794, nSHeight / 2 - tbRiffy.getHeight() - nSHeight * 300 / 1080);
        tbRiffy.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.mainCharacter.setCharacter("Riffy");
                screenControl.setnScreen(3);
                return true;
            }
        });
        stage.addActor(tbRiffy);


        tbBadLuck = new TextButton("Bad Luck", tbsRiffy);
        tbBadLuck.setSize(nSWidth * 500 / 1794, nSHeight * 100 / 1080);
        tbBadLuck.setPosition(nSWidth / 2 - tbRiffy.getWidth() / 2 - nSWidth * 400 / 1794, nSHeight / 2 - tbRiffy.getHeight() - nSHeight * 300 / 1080);
        tbBadLuck.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.mainCharacter.setCharacter("BadLuck");
                screenControl.setnScreen(3);
                return true;
            }
        });
        stage.addActor(tbBadLuck);

    }


    @Override
    public void render() {
        Gdx.input.setInputProcessor(stage);
        sbBatch.begin();
        sbBatch.draw(tBack, 0, 0, nSWidth, nSHeight);
        sbBatch.draw(tRiffy, nSWidth / 2 - nSWidth * 300 / 1794 / 2 + nSWidth * 400 / 1794, nSHeight / 2 - nSHeight * 250 / 1080, nSWidth * 300 / 1794, nSHeight * 400 / 1080);
        sbBatch.draw(tBadLuck, nSWidth / 2 - nSWidth * 300 / 1794 / 2 - nSWidth * 400 / 1794, nSHeight / 2 - nSHeight * 250 / 1080, nSWidth * 300 / 1794, nSHeight * 400 / 1080);
        sbBatch.end();
        stage.draw();
    }


}


