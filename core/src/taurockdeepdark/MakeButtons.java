package taurockdeepdark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by Matthew Brock on 30/10/2014.
 */
//http://stackoverflow.com/questions/21488311/libgdx-how-to-create-a-button
public class MakeButtons extends Game {
    Stage stage;
    BitmapFont font;

    private TextButton tbUpButton, tbDownButton, tbLeftButton, tbRightButton;
    private TextButton.TextButtonStyle tbsUpButton, tbsDownButton, tbsLeftButton, tbsRightButton;
    private Skin skUpButton, skDownButton, skLeftButton, skRightButton;
    private TextureAtlas taUpButton, taDownButton, taLeftButton, taRightButton;
    static int nSHeight, nSWidth;

    @Override
    public void create() {
        nSHeight = Gdx.graphics.getHeight();
        nSWidth = Gdx.graphics.getWidth();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skUpButton = new Skin();
        taUpButton = new TextureAtlas(Gdx.files.internal("UpButton.pack"));//Importing the .pack into a texture atlas that holds multiple images and can be referenced within a TextButtonStyle
        skUpButton.addRegions(taUpButton);//Applying a texture atlas into a skin
        tbsUpButton = new TextButton.TextButtonStyle();//TextButtonStyle Holds all the images that will be applied to the TextButton
        tbsUpButton.font = font;
        tbsUpButton.up = skUpButton.getDrawable("ArrowUp");//Setting positions and the image to use when the button is in those positions
        tbsUpButton.down = skUpButton.getDrawable("PressedArrowUp");
        tbsUpButton.checked = skUpButton.getDrawable("ArrowUp");
        tbUpButton = new TextButton("", tbsUpButton);//Applying the TextButtonStyle to the TextButton giving it all of its positions and images as well as any text but I didn't use
        tbUpButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
        tbUpButton.setPosition(nSWidth * 200 / 1794, nSHeight * 400 / 1080);
        tbUpButton.addListener(new InputListener() {//http://stackoverflow.com/questions/15731944/libgdx-why-is-my-button-not-responding-on-mouseclicks
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainCharacter.setCharacterRotation(4);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MainCharacter.setCharacterRotation(0);
            }
        });
        stage.addActor(tbUpButton);
        skDownButton = new Skin();
        taDownButton = new TextureAtlas(Gdx.files.internal("DownButton.pack"));
        skDownButton.addRegions(taDownButton);
        tbsDownButton = new TextButton.TextButtonStyle();
        tbsDownButton.font = font;
        tbsDownButton.up = skDownButton.getDrawable("ArrowDown");
        tbsDownButton.down = skDownButton.getDrawable("PressedArrowDown");
        tbsDownButton.checked = skDownButton.getDrawable("ArrowDown");
        tbDownButton = new TextButton("", tbsDownButton);
        tbDownButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
        tbDownButton.setPosition(nSWidth * 200 / 1794, 0);
        tbDownButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainCharacter.setCharacterRotation(5);
                return true;
            }



            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MainCharacter.setCharacterRotation(1);
            }
        });
        stage.addActor(tbDownButton);

        skLeftButton = new Skin();
        taLeftButton = new TextureAtlas(Gdx.files.internal("LeftButton.pack"));
        skLeftButton.addRegions(taLeftButton);
        tbsLeftButton = new TextButton.TextButtonStyle();
        tbsLeftButton.font = font;
        tbsLeftButton.up = skLeftButton.getDrawable("ArrowLeft");
        tbsLeftButton.down = skLeftButton.getDrawable("PressedArrowLeft");
        tbsLeftButton.checked = skLeftButton.getDrawable("ArrowLeft");
        tbLeftButton = new TextButton("", tbsLeftButton);
        tbLeftButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
        tbLeftButton.setPosition(0, nSHeight * 200 / 1080);
        tbLeftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainCharacter.setCharacterRotation(6);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MainCharacter.setCharacterRotation(2);
            }
        });
        stage.addActor(tbLeftButton);

        skRightButton = new Skin();
        taRightButton = new TextureAtlas(Gdx.files.internal("RightButton.pack"));
        skRightButton.addRegions(taRightButton);
        tbsRightButton = new TextButton.TextButtonStyle();
        tbsRightButton.font = font;
        tbsRightButton.up = skRightButton.getDrawable("ArrowRight");
        tbsRightButton.down = skRightButton.getDrawable("PressedArrowRight");
        tbsRightButton.checked = skRightButton.getDrawable("ArrowRight");
        tbRightButton = new TextButton("", tbsRightButton);
        tbRightButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
        tbRightButton.setPosition(nSWidth * 400 / 1794, nSHeight * 200 / 1080);
        tbRightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainCharacter.setCharacterRotation(7);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                MainCharacter.setCharacterRotation(3);
            }
        });
        stage.addActor(tbRightButton);


    }

    @Override
    public void render() {
        super.render();
        stage.draw();

    }

}
