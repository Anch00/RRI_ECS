package si.um.feri.ecsgame.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import si.um.feri.ecsgame.AncevskiGameECS;
import si.um.feri.ecsgame.assets.GameAssets;
import si.um.feri.ecsgame.ecs.components.GameStateComponent;
import si.um.feri.ecsgame.ecs.components.SpawnComponent;
import si.um.feri.ecsgame.ecs.factories.EntityFactory;
import si.um.feri.ecsgame.ecs.systems.*;
import si.um.feri.ecsgame.ecs.util.Constants;

public class GameScreen extends ScreenAdapter implements GameStateSystem.Resettable {
    private final AncevskiGameECS game;
    private final GameAssets assets;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private FitViewport viewport;
    private PooledEngine engine;
    private EntityFactory factory;

    public GameScreen(AncevskiGameECS game) {
        this.game = game;
        this.assets = game.assets;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);

        engine = new PooledEngine();
        factory = new EntityFactory(engine, assets);

        Entity state = new Entity();
        state.add(new GameStateComponent());
        engine.addEntity(state);

        engine.addSystem(new GameStateSystem(engine, this));
        engine.addSystem(new BackgroundSystem(batch));
        engine.addSystem(new InputSystem(factory));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new SpawnSystem(factory));
        engine.addSystem(new CollisionSystem(engine, assets));
        engine.addSystem(new ParticleSystem(batch));
        engine.addSystem(new CleanupSystem());
        engine.addSystem(new RenderingSystem(batch));
        engine.addSystem(new HudSystem(batch, assets));

        factory.createBackground();
        factory.createPlayer(Constants.WORLD_WIDTH / 2f - 32f, 20f);

        engine.addEntity(spawner(0, Constants.CRYSTAL_SPAWN_TIME));
        engine.addEntity(spawner(1, Constants.POTION_SPAWN_TIME));
        engine.addEntity(spawner(2, Constants.DARKORB_SPAWN_TIME));
        engine.addEntity(spawner(3, Constants.SHIELD_SPAWN_TIME));
    }

    private Entity spawner(int type, float interval) {
        Entity e = new Entity();
        SpawnComponent s = new SpawnComponent();
        s.type = type; s.interval = interval; s.timer = 0f;
        e.add(s);
        return e;
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void resetGame(Engine eng) {
        for (Entity e : eng.getEntities().toArray(Entity.class)) eng.removeEntity(e);

        Entity state = new Entity();
        state.add(new GameStateComponent());
        eng.addEntity(state);

        factory.createBackground();
        factory.createPlayer(Constants.WORLD_WIDTH / 2f - 32f, 20f);

        eng.addEntity(spawner(0, Constants.CRYSTAL_SPAWN_TIME));
        eng.addEntity(spawner(1, Constants.POTION_SPAWN_TIME));
        eng.addEntity(spawner(2, Constants.DARKORB_SPAWN_TIME));
        eng.addEntity(spawner(3, Constants.SHIELD_SPAWN_TIME));
    }
}
