package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import si.um.feri.ecsgame.ecs.components.GameStateComponent;
import si.um.feri.ecsgame.ecs.components.HealthComponent;
import si.um.feri.ecsgame.ecs.components.PlayerComponent;

public class GameStateSystem extends IteratingSystem {
    private final Engine engine;
    private final Resettable resetCallback;

    public interface Resettable {
        void resetGame(Engine engine);
    }

    public GameStateSystem(Engine engine, Resettable resetCallback) {
        super(Family.all(GameStateComponent.class).get());
        this.engine = engine;
        this.resetCallback = resetCallback;
    }

    @Override
    protected void processEntity(Entity stateEntity, float dt) {
        GameStateComponent gs = stateEntity.getComponent(GameStateComponent.class);

        if (Gdx.input.isKeyJustPressed(Input.Keys.P) && !gs.gameOver) {
            gs.paused = !gs.paused;
        }

        if (gs.gameOver) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                resetCallback.resetGame(engine);
                gs.gameOver = false;
                gs.paused = false;
            }
            return;
        }

        for (Entity player : getEngine().getEntitiesFor(Family.all(PlayerComponent.class, HealthComponent.class).get())) {
            HealthComponent h = player.getComponent(HealthComponent.class);
            if (h.hp <= 0) {
                gs.gameOver = true;
                break;
            }
        }
    }

    public static boolean isPausedOrOver(Engine engine) {
        for (Entity e : engine.getEntitiesFor(Family.all(GameStateComponent.class).get())) {
            GameStateComponent gs = e.getComponent(GameStateComponent.class);
            if (gs != null && (gs.paused || gs.gameOver)) return true;
        }
        return false;
    }
}
