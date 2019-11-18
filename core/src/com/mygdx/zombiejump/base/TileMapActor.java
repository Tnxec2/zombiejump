package com.mygdx.zombiejump.base;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * TileMapActor
 * 
 * Loads a Tiled map file (*.tmx), extends Actor to automatically render.
 */
public class TileMapActor extends Actor {

    // window dimensions
    private int windowWidht;
    private int windowHeight;

    private TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;

    /*
    * Initialize Tilemap created with the Tiled Map Editor
    */
    public TileMapActor(String fileName, Stage stage, int windowWidht, int windowHeight) {
        this.windowWidht = windowWidht;
        this.windowHeight = windowHeight;
        // set up tile map, renderer and camera
        tiledMap = new TmxMapLoader().load(fileName);

        /*
        *   list all props in tiledMap
        */
        /*
        MapProperties  props = tiledMap.getProperties();
        for (Iterator<String> it = props.getKeys(); it.hasNext();) {
            String s = it.next();
            System.out.println(s); 
        }
        */
        
        int tileWidth = (int) tiledMap.getProperties().get("tilewidth");
        int tileHeight = (int) tiledMap.getProperties().get("tileheight");
        int numTilesHorizontal = (int) tiledMap.getProperties().get("width");
        int numTilesVertical = (int) tiledMap.getProperties().get("height");

        int mapWidth = tileWidth * numTilesHorizontal;
        int mapHeight = tileHeight * numTilesVertical;

        
        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true);
        
        tiledCamera = new OrthographicCamera();
        tiledCamera.setToOrtho(false, windowWidht, windowHeight);
        tiledCamera.update();
        
        // by adding object to Stage, can be drawn automatically
        stage.addActor(this);

        // in theory, a solid boundary should be placed around the edge of the screen, 
        //  but just in case, this map can be used to set boundaries
        BaseActor.setWorldBounds(mapWidth, mapHeight);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        
        // adjust tilemap camera to stay in sync with main camera
        Camera mainCamera = getStage().getCamera();
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;
        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);

        // need the folowing code to force batch order,
        // otherweise it is batched and rendered last
        batch.end();
        tiledMapRenderer.render();
        batch.begin();
    }

    /**
     *  Search the map layers for Rectangle Objects that contain a property (key) 
     *  called "name" with associated value propertyName.
     * 
     *  Typically used to store non-actor information such as SpawnPoint 
     *  locations or dimensions of Solid objects.
     * 
     *  Retrieve data as object, then cast to desired type: for example, 
     *  float w = (float)obj.getProperties().get("width").
     */
    public ArrayList<MapObject> getRectangleList(String propertyName) {
        ArrayList<MapObject> list = new ArrayList<>();

        for ( MapLayer layer: tiledMap.getLayers()) {
            for (MapObject mapObject : layer.getObjects() ) {
                if ( ! ( mapObject instanceof RectangleMapObject) )
                    continue;
                
                MapProperties props = mapObject.getProperties();

                if ( props.containsKey("name") && props.get("name").equals(propertyName)  ) 
                    list.add(mapObject);
            }
        }

        return list;
    }
    

    /**
     *  Search the map layers for Tile Objects (tile-like elements of object layers)
     *  that contain a property (key) called "name" with associated value propertyName.
     *  Typically used to store actor information and will be used to create instances. 
     */
    public ArrayList<MapObject> getTileList(String propertyName)
    {
        ArrayList<MapObject> list = new ArrayList<MapObject>();

        for ( MapLayer layer : tiledMap.getLayers() )
        {
            for ( MapObject obj : layer.getObjects() )
            {
                if ( !(obj instanceof TiledMapTileMapObject) )
                    continue;

                MapProperties props = obj.getProperties();

                // Default MapProperties are stored within associated Tile object
                // Instance-specific overrides are stored in MapObject

                TiledMapTileMapObject tmtmo = (TiledMapTileMapObject)obj;
                TiledMapTile t = tmtmo.getTile();
                MapProperties defaultProps = t.getProperties();

                if ( defaultProps.containsKey("name") && defaultProps.get("name").equals(propertyName) )
                    list.add(obj);

                // get list of default property keys
                Iterator<String> propertyKeys = defaultProps.getKeys();

                // iterate over keys; copy default values into props if needed
                while ( propertyKeys.hasNext() )
                {
                    String key = propertyKeys.next();

                    // check if value already exists; if not, create property with default value
                    if ( props.containsKey(key) )
                        continue;
                    else
                    {
                        Object value = defaultProps.get(key);
                        props.put( key, value );
                    }
                }
            }
        }
        return list;
    }

    public int getWindowWidht() {
        return windowWidht;
    }

    public void setWindowWidht(int windowWidht) {
        this.windowWidht = windowWidht;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    

}