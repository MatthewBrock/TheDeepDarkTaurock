package taurockdeepdark;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * Created by Matthew Brock on 30/10/2014.
 */
//http://stackoverflow.com/questions/21488311/libgdx-how-to-create-a-button
public class Controls implements ApplicationListener {
    ScreenControl screenControl;
    Stage stage;
    Texture tX, tBlueGem, tRedGem,tTransition;
    Animation aRedGem, aBlueGem;
    BitmapFont font;
    MainCharacter mainCharacter;
    TextButton tbFireButton, tbShieldButton, tbSwordButton;
    TextButton.TextButtonStyle tbsFireButton, tbsShieldButton, tbsSwordButton;
    Skin skFireButton, skShieldButton, skSwordButton;
    TextureAtlas taFireButton, taShieldButton, taSwordButton;
    int nSHeight, nSWidth, nCharacterRot, nCharacterRotDeg,nTransitionTimer;
    boolean bTransition;
    SpriteBatch sbBatch;
    float stateTime = 0f;
    Touchpad touchpad;
    Touchpad.TouchpadStyle touchpadStyle;
    Skin touchpadSkin;
    Drawable touchBackground;
    Drawable touchKnob;
    Sound FireballSound;
    Sound SwordSound;

    public void setScreenControl(ScreenControl screenControl_) {
        screenControl = screenControl_;
    }

    public void setMainCharacter(MainCharacter mainCharacter_) {
        mainCharacter = mainCharacter_;
    }

    public Animation build(Texture tTexture, int nRows, int nCols) {
        TextureRegion[] trTextureRegion;
        Animation aAnimation;
        int nCount1 = 0;
        TextureRegion[][] tmp = TextureRegion.split(tTexture, tTexture.getWidth() / nCols, tTexture.getHeight() / nRows);//Making an array that holds the region of each image and the image in that region
        trTextureRegion = new TextureRegion[nCols * nRows];//Making a 1d array with a length that is the same as the number of regions
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                trTextureRegion[nCount1++] = tmp[i][j];//Filling the 1d array with the regions from the 2d array
            }
        }
        aAnimation = new Animation(0.10f, trTextureRegion);//Making animation with the array that is the texture region and setting the frame rate
        return aAnimation;
    }


    @Override
    public void create() {
        nSHeight = Gdx.graphics.getHeight();
        nSWidth = Gdx.graphics.getWidth();
        stage = new Stage(){// This lets us use the android back button
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    screenControl.setnScreen(1);
                }
                return super.keyDown(keyCode);
            }
        };
        font = new BitmapFont();
        sbBatch = new SpriteBatch();//use to draw multiple sprites at once apparently better
        tX = new Texture(Gdx.files.internal("X.png"));
        tRedGem = new Texture(Gdx.files.internal("RedGem.png"));
        tBlueGem = new Texture(Gdx.files.internal("BlueGem.png"));
        tTransition = new Texture(Gdx.files.internal("Loading.jpeg"));
        FireballSound = Gdx.audio.newSound(Gdx.files.internal("FireBallSound.mp3"));
        SwordSound = Gdx.audio.newSound(Gdx.files.internal("SwordSound.mp3"));
        aRedGem = build(tRedGem, 1, 6);//Making gem animations
        aBlueGem = build(tBlueGem, 1, 6);
//        skUpButton = new Skin();
//        taUpButton = new TextureAtlas(Gdx.files.internal("UpButton.pack"));//Importing the .pack into a texture atlas that holds multiple images and can be referenced within a TextButtonStyle
//        skUpButton.addRegions(taUpButton);//Applying a texture atlas into a skin
//        tbsUpButton = new TextButton.TextButtonStyle();//TextButtonStyle Holds all the images that will be applied to the TextButton
//        tbsUpButton.font = font;
//        tbsUpButton.up = skUpButton.getDrawable("ArrowUp");//Setting positions and the image to use when the button is in those positions
//        tbsUpButton.down = skUpButton.getDrawable("PressedArrowUp");
//        tbsUpButton.checked = skUpButton.getDrawable("ArrowUp");
//        tbUpButton = new TextButton("", tbsUpButton);//Applying the TextButtonStyle to the TextButton giving it all of its positions and images as well as any text but I didn't use
//        tbUpButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
//        tbUpButton.setPosition(nSWidth * 200 / 1794, nSHeight * 400 / 1080);
//        tbUpButton.addListener(new InputListener() {//http://gamedev.stackexchange.com/questions/60123/registering-inputlistener-in-libgdx
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                mainCharacter.setCharacterRotation(4, 180);
//                mainCharacter.setCharacterVelocity(0, 1);
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                mainCharacter.setCharacterRotation(0, 180);
//                mainCharacter.setCharacterVelocity(0, 0);
//            }
//        });
//        stage.addActor(tbUpButton);
//        skDownButton = new Skin();
//        taDownButton = new TextureAtlas(Gdx.files.internal("DownButton.pack"));
//        skDownButton.addRegions(taDownButton);
//        tbsDownButton = new TextButton.TextButtonStyle();
//        tbsDownButton.font = font;
//        tbsDownButton.up = skDownButton.getDrawable("ArrowDown");
//        tbsDownButton.down = skDownButton.getDrawable("PressedArrowDown");
//        tbsDownButton.checked = skDownButton.getDrawable("ArrowDown");
//        tbDownButton = new TextButton("", tbsDownButton);
//        tbDownButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
//        tbDownButton.setPosition(nSWidth * 200 / 1794, 0);
//        tbDownButton.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                mainCharacter.setCharacterRotation(5, 0);
//                mainCharacter.setCharacterVelocity(0, -1);
//                return true;
//            }
//
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                mainCharacter.setCharacterRotation(1, 0);
//                mainCharacter.setCharacterVelocity(0, 0);
//            }
//        });
//        stage.addActor(tbDownButton);
//
//        skLeftButton = new Skin();
//        taLeftButton = new TextureAtlas(Gdx.files.internal("LeftButton.pack"));
//        skLeftButton.addRegions(taLeftButton);
//        tbsLeftButton = new TextButton.TextButtonStyle();
//        tbsLeftButton.font = font;
//        tbsLeftButton.up = skLeftButton.getDrawable("ArrowLeft");
//        tbsLeftButton.down = skLeftButton.getDrawable("PressedArrowLeft");
//        tbsLeftButton.checked = skLeftButton.getDrawable("ArrowLeft");
//        tbLeftButton = new TextButton("", tbsLeftButton);
//        tbLeftButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
//        tbLeftButton.setPosition(0, nSHeight * 200 / 1080);
//        tbLeftButton.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                mainCharacter.setCharacterRotation(6, 270);
//                mainCharacter.setCharacterVelocity(-1, 0);
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                mainCharacter.setCharacterRotation(2, 270);
//                mainCharacter.setCharacterVelocity(0, 0);
//            }
//        });
//        stage.addActor(tbLeftButton);
//
//        skRightButton = new Skin();
//        taRightButton = new TextureAtlas(Gdx.files.internal("RightButton.pack"));
//        skRightButton.addRegions(taRightButton);
//        tbsRightButton = new TextButton.TextButtonStyle();
//        tbsRightButton.font = font;
//        tbsRightButton.up = skRightButton.getDrawable("ArrowRight");
//        tbsRightButton.down = skRightButton.getDrawable("PressedArrowRight");
//        tbsRightButton.checked = skRightButton.getDrawable("ArrowRight");
//        tbRightButton = new TextButton("", tbsRightButton);
//        tbRightButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
//        tbRightButton.setPosition(nSWidth * 400 / 1794, nSHeight * 200 / 1080);
//        tbRightButton.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                mainCharacter.setCharacterRotation(7, 90);
//                mainCharacter.setCharacterVelocity(1, 0);
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                mainCharacter.setCharacterRotation(3, 90);
//                mainCharacter.setCharacterVelocity(0, 0);
//            }
//        });
//        stage.addActor(tbRightButton);

        touchpadSkin = new Skin();//making a touchpad which is kinda like an analog stick
        touchpadSkin.add("touchBackground", new Texture(Gdx.files.internal("touchpadback.png")));//setting the background on the touchpad
        touchpadSkin.add("touchKnob", new Texture(Gdx.files.internal("touchpadknob.png")));//setting the knob
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setSize(nSWidth * 350 / 1794, nSHeight * 350 / 1080);
        stage.addActor(touchpad);

        skFireButton = new Skin(); //setting up the button
        taFireButton = new TextureAtlas(Gdx.files.internal("FireButton.pack"));
        skFireButton.addRegions(taFireButton);
        tbsFireButton = new TextButton.TextButtonStyle();
        tbsFireButton.font = font;
        tbsFireButton.up = skFireButton.getDrawable("FireButton");
        tbsFireButton.down = skFireButton.getDrawable("pressedFireButton");
        tbsFireButton.checked = skFireButton.getDrawable("FireButton");
        tbFireButton = new TextButton("", tbsFireButton);
        tbFireButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
        tbFireButton.setPosition(nSWidth - (nSWidth * 400 / 1794), 0);
        tbFireButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mainCharacter.nMp > 0) {//if you still have a mana gem
                    mainCharacter.makeFireBall();//Make a new fireball
                    FireballSound.play();
                    mainCharacter.nMp -= 1;
                }
                return true;
            }
        });
        stage.addActor(tbFireButton);


        skShieldButton = new Skin(); //setting up the button
        taShieldButton = new TextureAtlas(Gdx.files.internal("ShieldButtons.pack"));
        skShieldButton.addRegions(taShieldButton);
        tbsShieldButton = new TextButton.TextButtonStyle();
        tbsShieldButton.font = font;
        tbsShieldButton.up = skShieldButton.getDrawable("shieldButtonUp");
        tbsShieldButton.down = skShieldButton.getDrawable("shieldButtonDown");
        tbsShieldButton.checked = skShieldButton.getDrawable("shieldButtonUp");
        tbShieldButton = new TextButton("", tbsShieldButton);
        tbShieldButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
        tbShieldButton.setPosition(nSWidth - (nSWidth * 300 / 1794), (nSHeight * 200 / 1080));
        tbShieldButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainCharacter.bShieldR = true;
                return true;
            }
        });
        stage.addActor(tbShieldButton);

        skSwordButton = new Skin(); //setting up the button
        taSwordButton = new TextureAtlas(Gdx.files.internal("SwordButton.pack"));
        skSwordButton.addRegions(taSwordButton);
        tbsSwordButton = new TextButton.TextButtonStyle();
        tbsSwordButton.font = font;
        tbsSwordButton.up = skSwordButton.getDrawable("SwordButtonUp");
        tbsSwordButton.down = skSwordButton.getDrawable("SwordButtonDown");
        tbsSwordButton.checked = skSwordButton.getDrawable("SwordButtonUp");
        tbSwordButton = new TextButton("", tbsSwordButton);
        tbSwordButton.setSize(nSWidth * 200 / 1794, nSHeight * 200 / 1080);
        tbSwordButton.setPosition(nSWidth - (nSWidth * 200 / 1794), 0);
        tbSwordButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mainCharacter.bSword = true;
                SwordSound.play();
                return true;
            }
        });
        stage.addActor(tbSwordButton);


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.input.setInputProcessor(stage);
        if (touchpad.getKnobPercentX() > .75) {//The touchpad give you the percentage away from the center in the x and y direction I use this to find which direction it is being pulled
            nCharacterRot = 7;
            nCharacterRotDeg = 90;
            mainCharacter.setCharacterVelocity(1, 0);
        } else if (touchpad.getKnobPercentX() < -.75) {
            nCharacterRot = 6;
            nCharacterRotDeg = 270;
            mainCharacter.setCharacterVelocity(-1, 0);
        } else if (touchpad.getKnobPercentY() > .75) {
            nCharacterRot = 4;
            nCharacterRotDeg = 180;
            mainCharacter.setCharacterVelocity(0, 1);
        } else if (touchpad.getKnobPercentY() < -.75) {
            nCharacterRot = 5;
            nCharacterRotDeg = 0;
            mainCharacter.setCharacterVelocity(0, -1);
        }
        if (touchpad.getKnobPercentY() > -.75 && touchpad.getKnobPercentY() < .75 && touchpad.getKnobPercentX() > -.75 && touchpad.getKnobPercentX() < .75) {// this part checks if the touchpad is in the center then sets the animation to the standing still
            mainCharacter.setCharacterVelocity(0, 0);
            if (nCharacterRot == 7) {
                nCharacterRot = 3;
            } else if (nCharacterRot == 6) {
                nCharacterRot = 2;
            } else if (nCharacterRot == 5) {
                nCharacterRot = 1;
            } else if (nCharacterRot == 4) {
                nCharacterRot = 0;
            }
        }

        mainCharacter.setCharacterRotation(nCharacterRot, nCharacterRotDeg);


        stateTime += Gdx.graphics.getDeltaTime();
        stage.draw();
        sbBatch.begin();

        if (mainCharacter.nHp > 0) {// Shows the gems based on how much hp and mp you have
            sbBatch.draw(aRedGem.getKeyFrame(stateTime, true), 0, nSHeight - nSHeight * 100 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }
        if (mainCharacter.nHp > 1) {
            sbBatch.draw(aRedGem.getKeyFrame(stateTime, true), nSWidth * 100 / 1794, nSHeight - nSHeight * 100 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }
        if (mainCharacter.nHp > 2) {
            sbBatch.draw(aRedGem.getKeyFrame(stateTime, true), 2 * nSWidth * 100 / 1794, nSHeight - nSHeight * 100 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }
        if (mainCharacter.nHp > 3) {
            sbBatch.draw(aRedGem.getKeyFrame(stateTime, true), 3 * nSWidth * 100 / 1794, nSHeight - nSHeight * 100 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }
        if (mainCharacter.nHp > 4) {
            sbBatch.draw(aRedGem.getKeyFrame(stateTime, true), 4 * nSWidth * 100 / 1794, nSHeight - nSHeight * 100 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }

        if (mainCharacter.nMp > 0) {
            sbBatch.draw(aBlueGem.getKeyFrame(stateTime + 2, true), 0, nSHeight - nSHeight * 200 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }
        if (mainCharacter.nMp > 1) {
            sbBatch.draw(aBlueGem.getKeyFrame(stateTime + 2, true), nSWidth * 100 / 1794, nSHeight - nSHeight * 200 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }
        if (mainCharacter.nMp > 2) {
            sbBatch.draw(aBlueGem.getKeyFrame(stateTime + 2, true), 2 * nSWidth * 100 / 1794, nSHeight - nSHeight * 200 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }
        if (mainCharacter.nMp > 3) {
            sbBatch.draw(aBlueGem.getKeyFrame(stateTime + 2, true), 3 * nSWidth * 100 / 1794, nSHeight - nSHeight * 200 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }
        if (mainCharacter.nMp > 4) {
            sbBatch.draw(aBlueGem.getKeyFrame(stateTime + 2, true), 4 * nSWidth * 100 / 1794, nSHeight - nSHeight * 200 / 1080, nSWidth * 75 / 1794, nSHeight * 75 / 1080);
        }

        if (mainCharacter.nShieldTimer > 100 && mainCharacter.nShieldTimer < 400) {
            sbBatch.draw(tX, nSWidth - (nSWidth * 300 / 1794), (nSHeight * 200 / 1080), nSWidth * 200 / 1794, nSHeight * 200 / 1080);
        }
        if(bTransition){
            sbBatch.draw(tTransition,0,0,nSWidth,nSHeight);
            nTransitionTimer++;
            if(nTransitionTimer==200){
                bTransition=false;
                nTransitionTimer=0;
            }
        }




        sbBatch.end();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

}
