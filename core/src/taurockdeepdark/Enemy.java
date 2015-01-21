package taurockdeepdark;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;


/**
 * Created by Max on 2015-01-07.
 */
public class Enemy {
    Map[] armMaps;
    Sprite sGhost;
    MainCharacter mainChar = new MainCharacter();
    float fSHeight, fSWidth;
    OrthographicCamera camera;
    SpriteBatch sbSpriteBatch;
    Animation[] araWalking;
    ArrayList<FireBall> fireBalls;
    float fX, fY, tileWidth, tileHeight, fGhostX, fGhostY, fOldX, fOldY, fDx, fDy,
            fPyth, fFireX, fFireY, fPythFire, stateTime;
    int nCurrentMap = 0, nLayerCount, nCharacterWidth, nCharacterRotation, nCharacterHeight;
    boolean bChase = false, bCollidedX, bCollidedY;

    public void setMaps(Map[] armMaps_) {
        armMaps = armMaps_;
    }

    Enemy(Texture tEnemy, int nRotation, OrthographicCamera camera_, Map[] armMaps_) {
        camera = camera_;
        armMaps = armMaps_;
        tileWidth = armMaps[nCurrentMap].nMapScale * (armMaps[nCurrentMap].arclCollisionLayer[0].getTileWidth());
        tileHeight = armMaps[nCurrentMap].nMapScale * (armMaps[nCurrentMap].arclCollisionLayer[0].getTileHeight());
        fSHeight = Gdx.graphics.getHeight();
        fSWidth = Gdx.graphics.getWidth();
        sbSpriteBatch = new SpriteBatch();//This is bad use the one that already exists
        araWalking = new Animation[8];//array of animations
        sbSpriteBatch = new SpriteBatch();//use to draw multiple sprites at once apparently better
        stateTime = 0f;
        sGhost = new Sprite(tEnemy);
        sGhost.setSize(tileWidth, tileHeight);
        sGhost.setOrigin(sGhost.getWidth() / 2, sGhost.getHeight() / 2);
        //sGhost.setRotation(nRotation - 90);
        fGhostX = 67 * tileWidth - (tileWidth / 2);
        fGhostY = 13 * tileHeight - (tileHeight / 2);
        sGhost.setPosition(fGhostX, fGhostY);
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

    public void setFy(float characterFy) {//gets  the Y of the main character
        this.fY = characterFy;
    }

    public void setFx(float characterFx) {// get the X of the main character
        this.fX = characterFx;
    }
    public void render() {//Set matrix move then render
        sbSpriteBatch.setProjectionMatrix(camera.combined);
        sbSpriteBatch.begin();
        fOldX = fGhostX;//This is used for resetting the enemy position if they hit a wall
        fOldY = fGhostY;
        fDx = fGhostX - this.fX;           /// this determines whether the main character is close enough to chase or not
        fDy = fGhostY - this.fY;
        fPyth = (float) Math.abs(Math.sqrt(Math.pow(fDx, 2) + Math.pow(fDy, 2)));

        if (fPyth < 300) {//  sees if you are close enough to chase
            bChase = true;
        } else if (fPyth > 1000) {
            bChase = false;
        }
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

        sGhost.setPosition(fGhostX, fGhostY);//changes the position of the ghost
        sGhost.draw(sbSpriteBatch);
        sbSpriteBatch.end();


    }

    }