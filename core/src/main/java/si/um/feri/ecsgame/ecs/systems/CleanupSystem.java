package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import si.um.feri.ecsgame.ecs.components.TTLComponent;
import si.um.feri.ecsgame.ecs.util.Mappers;

public class CleanupSystem extends IteratingSystem {
    public CleanupSystem() {
        super(Family.one(TTLComponent.class).get());
    }

    @Override
    protected void processEntity(Entity e, float dt) {
        TTLComponent ttl = Mappers.ttl.get(e);
        if (ttl != null) {
            ttl.timeLeft -= dt;
            if (ttl.timeLeft <= 0) getEngine().removeEntity(e);
        }
    }
}
