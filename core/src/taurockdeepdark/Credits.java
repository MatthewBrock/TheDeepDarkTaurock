package taurockdeepdark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Matthew Brock on 20/01/2015.
 */
public class Credits extends Game {
    Texture tCredits;
    SpriteBatch batch;
    int nSWidth,nSHeight;
    ScreenControl screenControl;
    Stage stage;


    @Override
    public void create() {
        stage = new Stage(){
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.BACK) {
                    screenControl.setnScreen(1
                    );
                }
                return super.keyDown(keyCode);
            }
        };
        nSHeight = Gdx.graphics.getHeight();
        nSWidth = Gdx.graphics.getWidth();
        batch = new SpriteBatch();
        tCredits = new Texture(Gdx.files.internal("Credits.png"));
    }

    @Override
    public void render(){
        Gdx.input.setInputProcessor(stage);
        batch.begin();
        batch.draw(tCredits,0,0,nSWidth,nSHeight);
        batch.end();
    }
    public void setScreenControl(ScreenControl screenControl_){
        screenControl = screenControl_;
    }
}
