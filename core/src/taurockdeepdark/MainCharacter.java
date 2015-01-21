package taurockdeepdark;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;


/**
 * Created by Matthew Brock on 27/10/2014.
 */

//https://github.com/libgdx/libgdx/wiki/2D-Animation
public class MainCharacter implements ApplicationListener {
    Loader loader;
    Map[] armMaps;
    Controls controls;
    OrthographicCamera camera;
    float fCharacterVelocityX = 0, fCharacterVelocityY = 0, fCharacterX, fCharacterY, fCharacterWidth, fCharacterHeight;
    int nSHeight, nSWidth, nCharacterRotation = 1, nCharacterRotationDeg = 0, nLayerCount, nCurrentMap = 3, nVelocityX, nVelocityY, nShieldTimer, nSwordTimer, nHp = 5, nMp = 5, nMpTimer, nSongTimer, nTimerX, timerSpawn = 0;
    Animation[] araWalking;
    ArrayList<FireBall> arlFireBalls;
    ArrayList<Enemy> arlEnemy;
    Texture tTemp, tFireBall, tShield, tSword, tEnemy;
    Sprite spSword;
    SpriteBatch sbSpriteBatch;
    Music SpookySong;
    float stateTime;
    float fOldX, fOldY, tileWidth, tileHeight;
    boolean bCollidedX, bCollidedY, bShieldR, bShieldT, bSword, bBGsong = true;
    String sName;

    public void reset() {
        nHp = 5;
        nMp = 5;
        nCurrentMap = 0;
        fCharacterX = 85 * tileWidth - (tileWidth / 2);
        fCharacterY = 15 * tileHeight - (tileHeight / 2);
    }

    public void setloader(Loader loader_) {
        loader = loader_;
    }

    public float getCharacterY() {
        return fCharacterY;
    }

    public float getCharacterX() {
        return fCharacterX;
    }

    public boolean getSword() {
        return bSword;
    }

    public boolean getShield() {
        return bShieldR;
    }

    public void setControls(Controls controls_) {
        controls = controls_;
    }

    public void setMaps(Map[] armMaps_) {
        armMaps = armMaps_;
    }

    public void setCamera(OrthographicCamera camera_) {
        camera = camera_;
    }

    public void makeFireBall() {//This makes a new fireball
        arlFireBalls.add(new FireBall(tFireBall, fCharacterX + (fCharacterWidth / 8), fCharacterY + (fCharacterHeight / 8), nCharacterRotationDeg, camera));
    }

    public void makeEnemy() {//This makes a new enemy
        arlEnemy.add(new Enemy(tEnemy, nCharacterRotationDeg, camera, armMaps));
    }

    public ArrayList<FireBall> getFireballs() {
        return arlFireBalls;
    }

    public ArrayList<Enemy> getEnemy() {
        return arlEnemy;
    }

    public void setCharacter(String sName_) {//takes the name of the chosen character then builds the animation for that character
        sName = sName_;
        for (int i = 0; i < 8; i++) {
            int k = 1;
            tTemp = new Texture(Gdx.files.internal(sName_ + i + ".png"));
            if (i > 3) {
                k = 3;
            }
            araWalking[i] = build(tTemp, 1, k);//Populating an array of animations using my method BuildAnimation
        }
    }

    @Override
    public void create() {
        nSHeight = Gdx.graphics.getHeight(); //use to make scaling
        nSWidth = Gdx.graphics.getWidth();
        nVelocityX = nSWidth * 10 / 1794;
        nVelocityY = nSHeight * 10 / 1080;
        camera.setToOrtho(false, nSWidth, nSHeight);
        camera.update();
        fCharacterWidth = nSWidth * 110 / 1794;
        fCharacterHeight = nSHeight * 120 / 1080;
        SpookySong = Gdx.audio.newMusic(Gdx.files.internal("SpookySong.mp3"));
        SpookySong.setVolume(1f);
        SpookySong.setLooping(true);
        tFireBall = new Texture(Gdx.files.internal("FireBall.png"));
        tShield = new Texture(Gdx.files.internal("Shield.png"));
        tSword = new Texture(Gdx.files.internal("FireSword.png"));
        tEnemy = new Texture(Gdx.files.internal("Ghost1.png"));
        spSword = new Sprite(tSword);
        spSword.setSize(nSWidth * 40 / 1794, nSHeight * 175 / 1080);
        spSword.setOrigin(spSword.getWidth() / 2, 0);
        araWalking = new Animation[8];//array of animations
        arlFireBalls = new ArrayList<FireBall>();
        arlEnemy = new ArrayList<Enemy>();
        sbSpriteBatch = new SpriteBatch();//use to draw multiple sprites at once apparently better
        stateTime = 0f;
        tileWidth = armMaps[nCurrentMap].nMapScale * (armMaps[nCurrentMap].arclCollisionLayer[0].getTileWidth());//Grabbing the tile width for the tiledMap
        tileHeight = armMaps[nCurrentMap].nMapScale * (armMaps[nCurrentMap].arclCollisionLayer[0].getTileHeight());
        fCharacterX = 87 * tileWidth - (tileWidth / 2);
        fCharacterY = 13 * tileHeight - (tileHeight / 2);
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
    public void resize(int width, int height) {

    }

    public void setCharacterRotation(int nRotation, int nRotationDeg) {
        nCharacterRotation = nRotation;
        nCharacterRotationDeg = nRotationDeg;
    }

    public void setCharacterVelocity(int nVelocityX_, int nVelocityY_) {
        fCharacterVelocityX = nVelocityX_ * nVelocityX;
        fCharacterVelocityY = nVelocityY_ * nVelocityY;
    }

    public boolean getTileID(float fX, float fY, float nWidth, String sID) {// this is slightly complicated but its basically grabbing the tile that the character is standing on and getting the ID
        boolean bCollided = false;
        for (nLayerCount = 0; nLayerCount < armMaps[nCurrentMap].tiledMap.getLayers().getCount(); nLayerCount++) {
            try {
                bCollided = armMaps[nCurrentMap].arclCollisionLayer[nLayerCount].getCell((int) ((fX + nWidth / 4) / tileWidth), (int) (fY / tileHeight))
                        .getTile().getProperties().containsKey(sID);

                bCollided |= armMaps[nCurrentMap].arclCollisionLayer[nLayerCount].getCell((int) ((fX + 3 * nWidth / 4) / tileWidth), (int) (fY / tileHeight))
                        .getTile().getProperties().containsKey(sID);

                bCollided |= armMaps[nCurrentMap].arclCollisionLayer[nLayerCount].getCell((int) ((fX + nWidth / 2) / tileWidth), (int) (fY / tileHeight))
                        .getTile().getProperties().containsKey(sID);
            } catch (NullPointerException e) {
            }
        }
        return bCollided;
    }

    @Override
    public void render() {
        camera.position.set(fCharacterX, fCharacterY, 0);
        sbSpriteBatch.setProjectionMatrix(camera.combined);
        if (nMp < 5) {// used to regenerate mp at a set rate
            nMpTimer++;
            if (nMpTimer == 75) {
                nMp += 1;
                nMpTimer = 0;
            }
        }
        timerSpawn++; // the spawn rate of the enemies
        if (timerSpawn == 75) {
            makeEnemy();
            timerSpawn = 0;
        }
        camera.update();


        //System.out.println(((fCharacterX+tileWidth / 2)/tileWidth) + " is x" ); // used to check the tile of the character
        //System.out.println(((fCharacterY+tileHeight / 2)/tileHeight) + " is Y" );

        fOldX = fCharacterX;//This is used for resetting the players position if they hit a wall
        fOldY = fCharacterY;

        fCharacterX += fCharacterVelocityX;//Move character
        bCollidedX = getTileID(fCharacterX, fCharacterY, fCharacterWidth, "Block");//Did it touched a tile with the block ID

        if (bCollidedX) {//If it touched a tile with the block ID reset the position
            fCharacterX = fOldX;
        }

        fCharacterY += fCharacterVelocityY;//This is the same as the previous bit but for the Y direction
        bCollidedY = getTileID(fCharacterX, fCharacterY, fCharacterWidth, "Block");

        if (bCollidedY) {
            fCharacterY = fOldY;
        }


        if (getTileID(fCharacterX, fCharacterY, fCharacterWidth, "MoveUp")) {//This checks if the character is standing on a door
            if (nCurrentMap < armMaps.length - 1) {
                controls.bTransition = true;
                fCharacterX = 85 * tileWidth - (tileWidth / 2);
                fCharacterY = 15 * tileHeight - (tileHeight / 2);
                nCurrentMap++;//If the character was standing on a door change the map
            }
        }


        if (getTileID(fCharacterX, fCharacterY, fCharacterWidth, "Egg")) {
            nSongTimer++;
            if (nSongTimer == 300) {
                bBGsong = false;
                SpookySong.play();
            }
        } else {
            nSongTimer = 0;
        }
        if (getTileID(fCharacterX, fCharacterY, fCharacterWidth, "Egg2")) {
            nTimerX++;
            if (nTimerX == 600) {
                nCurrentMap += 1;
                fCharacterX = 85 * tileWidth - (tileWidth / 2);
                fCharacterY = 15 * tileHeight - (tileHeight / 2);
            }
        } else {
            nTimerX = 0;
        }


        if (getTileID(fCharacterX, fCharacterY, fCharacterWidth, "MoveDown")) {//Same as the previous bit but for the door the decreases the map index
            if (nCurrentMap > 0) {
                controls.bTransition = true;
                fCharacterX = 15 * tileWidth - (tileWidth / 2);
                fCharacterY = 83 * tileHeight - (tileHeight / 2);
                nCurrentMap--;

            }
        }


        stateTime += Gdx.graphics.getDeltaTime();//Getting a time to select a frame from the animation
        sbSpriteBatch.begin();
        if (bShieldR) {
            if (nShieldTimer <= 100) {
                sbSpriteBatch.draw(tShield, fCharacterX - fCharacterWidth / 8, fCharacterY - fCharacterHeight / 8, nSWidth * 135 / 1794, nSHeight * 150 / 1080);
                bShieldT = true;
            } else {
                bShieldT = false;
            }
            nShieldTimer++;
            if (nShieldTimer == 400) {
                bShieldR = false;
            }
        } else {
            nShieldTimer = 0;
        }
        if (bSword) {
            nSwordTimer++;
            if (nSwordTimer == 10) {
                bSword = false;
                nSwordTimer = 0;
            }
        }


        spSword.setRotation(nCharacterRotationDeg + 180);

        if (nCharacterRotationDeg == 90) {
            sbSpriteBatch.draw(araWalking[nCharacterRotation].getKeyFrame(stateTime, true), fCharacterX, fCharacterY, fCharacterWidth, fCharacterHeight);//Drawing the animation from the array of animations based on the character rotation
            spSword.setPosition(fCharacterX, fCharacterY + fCharacterHeight / 4);
            if (bSword) {
                spSword.draw(sbSpriteBatch);
            }
        } else if (nCharacterRotationDeg == 0) {
            sbSpriteBatch.draw(araWalking[nCharacterRotation].getKeyFrame(stateTime, true), fCharacterX, fCharacterY, fCharacterWidth, fCharacterHeight);//Drawing the animation from the array of animations based on the character rotation
            spSword.setPosition(fCharacterX, fCharacterY + fCharacterHeight / 2);
            if (bSword) {
                spSword.draw(sbSpriteBatch);
            }
        } else if (nCharacterRotationDeg == 180) {
            spSword.setPosition(fCharacterX + fCharacterWidth / 2, fCharacterY + fCharacterHeight / 4);
            if (bSword) {
                spSword.draw(sbSpriteBatch);
            }
            sbSpriteBatch.draw(araWalking[nCharacterRotation].getKeyFrame(stateTime, true), fCharacterX, fCharacterY, fCharacterWidth, fCharacterHeight);//Drawing the animation from the array of animations based on the character rotation

        } else if (nCharacterRotationDeg == 270) {
            spSword.setPosition(fCharacterX + fCharacterWidth / 2, fCharacterY + fCharacterHeight / 4);
            if (bSword) {
                spSword.draw(sbSpriteBatch);
            }
            sbSpriteBatch.draw(araWalking[nCharacterRotation].getKeyFrame(stateTime, true), fCharacterX, fCharacterY, fCharacterWidth, fCharacterHeight);//Drawing the animation from the array of animations based on the character rotation

        }


        sbSpriteBatch.end();
        for (int i = 0; i < arlEnemy.size(); i++) { //This renders the enemies
            arlEnemy.get(i).setFx(getCharacterX());
            arlEnemy.get(i).setFy(getCharacterY());
            arlEnemy.get(i).render();

        }
        for (int i = 0; i < arlFireBalls.size(); i++) {//This renders all the fireballs
            arlFireBalls.get(i).render();
            if (arlFireBalls.get(i).bounds(tileWidth, tileHeight)) {
                arlFireBalls.remove(i);
            }
        }
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

