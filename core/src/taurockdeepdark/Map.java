package taurockdeepdark;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Created by Matthew Brock on 18/11/2014.
 */
//http://www.youtube.com/watch?v=qik60F5I6J4
public class Map implements ApplicationListener {
    TiledMap tiledMap;
    OrthographicCamera camera;
    OrthogonalTiledMapRenderer tiledMapRenderer;

    @Override
    public void create() {
        camera= new  OrthographicCamera();
        camera.setToOrtho(false,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        tiledMap = new TmxMapLoader().load("Map1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 2);//Set the render to the map and scale it


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

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
