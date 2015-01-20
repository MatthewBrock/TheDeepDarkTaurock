package taurockdeepdark;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;


/**
 * Created by Max on 2015-01-07.
 */
public class Enemy implements ApplicationListener {
    Map[] armMaps;
    MainCharacter mainCharacter;
    int nSHeight, nSWidth, nCharacterRotation = 1, nCharacterWidth, nCharacterHeight, nLayerCount, nCurrentMap = 0, nVelocityX, nVelocityY, nHealth = 4;
    OrthographicCamera camera;
    Texture tTemp;
    Animation[] araWalking;
    SpriteBatch sbSpriteBatch;
    float stateTime;
    float fGhostX, fGhostY, fX, fY;
    float fOldX, fOldY, tileWidth, tileHeight, fDx, fDy, fPyth, fFireX, fFireY, fPythFire;
    boolean bCollidedX, bCollidedY, bChase = false, bSword = false, bShield = false, bLive = true;
    int nGhostHealth = 10;


    public void setMaps(Map[] armMaps_) {
        armMaps = armMaps_;
    }

    public void setCamera(OrthographicCamera camera_) {
        camera = camera_;
    }

    public void setMainCharacter(MainCharacter mainCharacter_) {
        mainCharacter = mainCharacter_;
    }


    @Override
    public void create() {
        nSHeight = Gdx.graphics.getHeight(); //use to make scaling
        nSWidth = Gdx.graphics.getWidth();
        nVelocityX = nSWidth * 10 / 1794;
        nVelocityY = nSHeight * 10 / 1080;
        nCharacterWidth = nSWidth * 110 / 1794;
        nCharacterHeight = nSHeight * 120 / 1080;
        araWalking = new Animation[8];//array of animations
        sbSpriteBatch = new SpriteBatch();//use to draw multiple sprites at once apparently better
        for (int i = 0; i < 8; i++) {
            int k = 1;
            tTemp = new Texture(Gdx.files.internal("Ghost" + i + ".png"));
            if (i > 3) {
                k = 3;
            }
            araWalking[i] = build(tTemp, 1, k);//Populating an array of animations using my method BuildAnimation
        }
        stateTime = 0f;
        tileWidth = armMaps[nCurrentMap].nMapScale * (armMaps[nCurrentMap].arclCollisionLayer[0].getTileWidth());//Grabbing the tile width for the tiledMap
        tileHeight = armMaps[nCurrentMap].nMapScale * (armMaps[nCurrentMap].arclCollisionLayer[0].getTileHeight());
        fGhostX = 50 * tileWidth - (tileWidth / 2); // spawning location
        fGhostY = 13 * tileHeight - (tileHeight / 2);
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

    public boolean getTileID(float fX, float fY, int nWidth, String sID) {// this is slightly complicated but its basically grabbing the tile that the character is standing on and getting the ID
        boolean bCollided = false;
        boolean bFire = false;
        for (nLayerCount = 0; nLayerCount < armMaps[nCurrentMap].tiledMap.getLayers().getCount() - 1; nLayerCount++) {

            bCollided = armMaps[nCurrentMap].arclCollisionLayer[nLayerCount].getCell((int) ((fX + nWidth / 4) / tileWidth), (int) (fY / tileHeight))
                    .getTile().getProperties().containsKey(sID);

            bCollided |= armMaps[nCurrentMap].arclCollisionLayer[nLayerCount].getCell((int) ((fX + 3 * nWidth / 4) / tileWidth), (int) (fY / tileHeight))
                    .getTile().getProperties().containsKey(sID);

            bCollided |= armMaps[nCurrentMap].arclCollisionLayer[nLayerCount].getCell((int) ((fX + nWidth / 2) / tileWidth), (int) (fY / tileHeight))
                    .getTile().getProperties().containsKey(sID);
        }
        return bCollided;
    }

    public void setFy(float characterFy) {
        fY = characterFy;
    }

    public void setFx(float characterFx) {
        fX = characterFx;
    }

    public void setSword(boolean _bSword) {
        bSword = _bSword;
    }

    public void setShield(boolean _bShield) {
        bSword = _bShield;
    }


    @Override
    public void render() {
        sbSpriteBatch.setProjectionMatrix(camera.combined);
        camera.update();
        fOldX = fGhostX;//This is used for resetting the players position if they hit a wall
        fOldY = fGhostY;
        fDx = fGhostX - fX;           /// this determines whether the main character is close enough to chase or not
        fDy = fGhostY - fY;

        ArrayList<FireBall> fireBalls = mainCharacter.getFireballs();
        for (int i = 0; i < fireBalls.size(); i++) {
            fFireX = fireBalls.get(i).getfX();
            fFireY = fireBalls.get(i).getfY();
            fPythFire = (float) Math.abs(Math.sqrt(Math.pow(fGhostX - fFireX, 2) + Math.pow(fGhostY - fFireY, 2)));
            //System.out.println(fFireX + " is X");
            //System.out.println(fFireY + " is Y");
            if (fPythFire <= 50) {
                nGhostHealth = 0;
                System.out.println("dead");

            }
            //System.exit(0);
        }
        fPyth = (float) Math.abs(Math.sqrt(Math.pow(fDx, 2) + Math.pow(fDy, 2)));
        if (bSword && fPyth < 100) { //this checks the hit test of the sword
            nGhostHealth--;
            System.out.println("get Stabbed HEALTH IS: " + nGhostHealth);
            bSword = false;
        } else if (fPyth < 50 && !bShield && !bSword) { // hit test player
            //  nHealth--;
            System.out.println("player hit " + nHealth);
            //  fGhostX = fOldX;
            // fGhostY = fOldY;
            if (nHealth <= 0) {
                System.out.println("dead");
                //System.exit(0);
            }
        } else if (fPyth < 300) {//  sees if you are close enough to chase
            bChase = true;
        } else if (fPyth > 1000) {
            bChase = false;
        }

       /* if(fPyth<=100){ // hit test against main
            fGhostX = fOldX;//This is used for resetting the players position if they hit a wall
            fGhostY = fOldY;// only sort of works, might move this to MainCharacter.java

        }*/
        if (bChase) {
            fGhostX = (float) (0.98 * (fGhostX - fX) + fX - 1.5);//Move character
            System.out.println();
            bCollidedX = getTileID(fGhostX, fGhostY, nCharacterWidth, "Block");//Did it touched a tile with the block ID
            if (bCollidedX) {//If it touched a tile with the block ID reset the position
                fGhostX = fOldX;
            }
            fGhostY = (float) (0.98 * (fGhostY - fY) + fY - 1.5);//This is the same as the previous bit but for the Y direction
            bCollidedY = getTileID(fGhostX, fGhostY, nCharacterWidth, "Block");
            if (bCollidedY) {
                fGhostY = fOldY;
            }
        }
        if (fOldX < fGhostX/*&& fOldY-fGhostX<=10*/) {//facing right
            nCharacterRotation = 7;
        } else if (fOldX > fGhostX) {//facing left
            nCharacterRotation = 6;
        } else if (fOldY < fGhostY/*&& fOldY-fGhostX<=10*/) {//facing down
            nCharacterRotation = 4;
        } else if (fOldY > fGhostY) {//facing up
            nCharacterRotation = 5;
        } else {
            nCharacterRotation = 1;
        }


        stateTime += Gdx.graphics.getDeltaTime();//Getting a time to select a frame from the animation
        sbSpriteBatch.begin();
        sbSpriteBatch.draw(araWalking[nCharacterRotation].getKeyFrame(stateTime, true), fGhostX, fGhostY, nCharacterWidth, nCharacterHeight);//Drawing the animation from the array of animations based on the character rotation
        sbSpriteBatch.end();
        if (nGhostHealth == 0) {
            bLive = false;
        }
    }

    public boolean getLive() {
        return bLive;
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