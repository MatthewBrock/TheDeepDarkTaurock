package taurockdeepdark;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Matthew Brock on 08/11/2014.
 */
public class BuildAnimation implements ApplicationListener {
    static TextureRegion[] trTextureRegion;
    static Animation aAnimation;
    static int nCount1 = 0;


    public static Animation build(Texture tTexture, int nRows, int nCols) {

        TextureRegion[][] tmp = TextureRegion.split(tTexture, tTexture.getWidth() / nCols, tTexture.getHeight() / nRows);
        trTextureRegion = new TextureRegion[nCols * nRows];
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                trTextureRegion[nCount1++] = tmp[i][j];
            }
        }
        nCount1 = 0;
        aAnimation = new Animation(0.17f, trTextureRegion);
        return aAnimation;
    }

    @Override
    public void create() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
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
