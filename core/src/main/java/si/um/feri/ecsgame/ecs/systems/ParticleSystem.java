package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import si.um.feri.ecsgame.ecs.components.FollowComponent;
import si.um.feri.ecsgame.ecs.components.ParticleComponent;
import si.um.feri.ecsgame.ecs.components.PositionComponent;
import si.um.feri.ecsgame.ecs.util.Mappers;

public class ParticleSystem extends IteratingSystem {
    private final SpriteBatch batch;

    public ParticleSystem(SpriteBatch batch) {
        super(Family.all(ParticleComponent.class, PositionComponent.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity e, float dt) {
        ParticleComponent pc = Mappers.particle.get(e);
        PositionComponent p = Mappers.position.get(e);
        FollowComponent follow = e.getComponent(FollowComponent.class);

        // sledenje targetu
        if (follow != null && follow.target != null) {
            PositionComponent tp = Mappers.position.get(follow.target);
            if (tp != null) {
                p.x = tp.x + follow.offsetX;
                p.y = tp.y + follow.offsetY;
            } else {
                // target ne obstaja več -> takoj počisti in odstrani trail
                if (pc.effect != null) { pc.effect.free(); pc.effect = null; }
                getEngine().removeEntity(e);
                return;
            }
        }

        if (pc.effect == null && pc.pool != null) {
            pc.effect = pc.pool.obtain();
            pc.effect.setPosition(p.x, p.y);
            pc.effect.start();
        }

        if (pc.effect != null) {
            pc.effect.setPosition(p.x, p.y);
            pc.effect.update(dt);

            batch.begin();
            pc.effect.draw(batch);
            batch.end();

            // če je efekt končan in nimamo targeta (ali naj trail živi samo, dokler target živi), počisti
            if (pc.effect.isComplete() && (follow == null || follow.target == null)) {
                pc.effect.free();
                pc.effect = null;
                getEngine().removeEntity(e);
            }
        }
    }
}
