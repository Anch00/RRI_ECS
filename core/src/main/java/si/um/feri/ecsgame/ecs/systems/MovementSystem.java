package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.systems.IteratingSystem;
import si.um.feri.ecsgame.ecs.components.BoundsComponent;
import si.um.feri.ecsgame.ecs.components.PositionComponent;
import si.um.feri.ecsgame.ecs.components.VelocityComponent;
import si.um.feri.ecsgame.ecs.util.Constants;
import si.um.feri.ecsgame.ecs.util.Mappers;
import com.badlogic.ashley.core.*;

public class MovementSystem extends IteratingSystem {
    public MovementSystem() {
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    public void update(float dt) {
        if (GameStateSystem.isPausedOrOver(getEngine())) return;
        super.update(dt);
    }

    @Override
    protected void processEntity(Entity e, float dt) {
        PositionComponent p = Mappers.position.get(e);
        VelocityComponent v = Mappers.velocity.get(e);
        p.x += v.vx * dt;
        p.y += v.vy * dt;

        BoundsComponent b = Mappers.bounds.get(e);
        if (b != null) b.rect.setPosition(p.x, p.y);

        if (Mappers.player.get(e) != null) {
            if (p.x < 0) p.x = 0;
            if (p.x > Constants.WORLD_WIDTH - (b != null ? b.rect.width : 0)) p.x = Constants.WORLD_WIDTH - (b != null ? b.rect.width : 0);
        }
    }
}
