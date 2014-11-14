package taurockdeepdark;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;


/**
 * Created by Matthew Brock on 30/10/2014.
 */
//http://obviam.net/index.php/getting-started-in-android-game-development-with-libgdx-create-a-working-prototype-in-a-day-tutorial-part-1/
public class Main implements ApplicationListener {
   // MainCharacter mainCharacter;
    MakeButtons makeButtons;
    @Override
    public void create() {//did this change
        makeButtons = new MakeButtons();
       // mainCharacter = new MainCharacter();
       // mainCharacter.create();
        makeButtons.create();
        System.out.println("Width"+Gdx.graphics.getWidth());
        System.out.println("Height"+Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        //mainCharacter.render();
        makeButtons.render();
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
