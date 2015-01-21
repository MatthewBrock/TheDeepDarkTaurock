package taurockdeepdark;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


/**
 * Created by Matthew Brock on 07/01/2015.
 */
public class Main extends Game {//http://stackoverflow.com/questions/24551605/libgdx-input-processor-not-working-with-group
    Loader loader;
    GameScreen gameScreen;
    MainMenu mainMenu;
    Credits credits;
    ScreenControl screenControl;
    CharacterSelect characterSelect;
    Music BGsong;

    int nScreen;

    @Override
    public void create() {
        BGsong = Gdx.audio.newMusic(Gdx.files.internal("DungeonCraw.mp3"));
        Gdx.input.setCatchBackKey(true);// lets you use the back button without it just taking you out of the game
        loader = new Loader();
        credits = new Credits();
        screenControl = new ScreenControl();
        gameScreen = new GameScreen();
        mainMenu = new MainMenu();
        characterSelect = new CharacterSelect();
        screenControl.create();
        credits.create();
        credits.setScreenControl(screenControl);
        gameScreen.setLoader(loader);
        gameScreen.create();
        mainMenu.create();
        characterSelect.create();
        characterSelect.setScreenControl(screenControl);// screen control is just used to set the screen that is being rendered
        mainMenu.setScreenControl(screenControl);
        mainMenu.setLoader(loader);
        gameScreen.controls.setScreenControl(screenControl);
        characterSelect.setGameScreen(gameScreen);
        BGsong.setVolume(1f);
        BGsong.setLooping(true);
        BGsong.play();
    }

    @Override
    public void render() {//controls which screen is rendered
        if (!gameScreen.mainCharacter.bBGsong) {//used for an easter egg that switches the song
            BGsong.stop();
        }
        nScreen = screenControl.nScreen;
        if (nScreen == 1) {
            mainMenu.render();
        } else if (nScreen == 2) {
            characterSelect.render();
        } else if (nScreen == 3) {
            gameScreen.render();
        } else if (nScreen == 4) {
            credits.render();
        }
    }
}