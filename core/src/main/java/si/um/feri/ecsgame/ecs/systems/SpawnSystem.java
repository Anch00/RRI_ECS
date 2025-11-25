package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import si.um.feri.ecsgame.ecs.components.SpawnComponent;
import si.um.feri.ecsgame.ecs.factories.EntityFactory;
import si.um.feri.ecsgame.ecs.util.Constants;

public class SpawnSystem extends IteratingSystem {
    private final EntityFactory factory;

    public SpawnSystem(EntityFactory factory) {
        super(Family.all(SpawnComponent.class).get());
        this.factory = factory;
    }

    @Override
    public void update(float dt) {
        if (GameStateSystem.isPausedOrOver(getEngine())) return;
        super.update(dt);
    }

    @Override
    protected void processEntity(Entity e, float dt) {
        SpawnComponent s = e.getComponent(SpawnComponent.class);
        s.timer += dt;
        if (s.timer >= s.interval) {
            s.timer -= s.interval;
            float x = MathUtils.random(0, Constants.WORLD_WIDTH - 64f);
            float y = Constants.WORLD_HEIGHT;
            switch (s.type) {
                case 0: factory.createCrystal(x, y); break;
                case 1: factory.createPotion(x, y); break;
                case 2: factory.createDarkOrb(x, y); break;
                case 3: factory.createShield(x, y); break;
            }
        }
    }
}
