package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import si.um.feri.ecsgame.ecs.components.*;
import si.um.feri.ecsgame.ecs.factories.EntityFactory;
import si.um.feri.ecsgame.ecs.util.Constants;
import si.um.feri.ecsgame.ecs.util.Mappers;

public class InputSystem extends IteratingSystem {
    private final EntityFactory factory;

    public InputSystem(EntityFactory factory) {
        super(Family.all(PlayerComponent.class, VelocityComponent.class, PositionComponent.class,
            ManaComponent.class, BoundsComponent.class).get());
        this.factory = factory;
    }

    @Override
    public void update(float dt) {
        if (GameStateSystem.isPausedOrOver(getEngine())) return;
        super.update(dt);
    }

    @Override
    protected void processEntity(Entity entity, float dt) {
        VelocityComponent v = Mappers.velocity.get(entity);
        v.vx = 0f; v.vy = 0f;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) v.vx = -Constants.WIZARD_SPEED;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) v.vx = Constants.WIZARD_SPEED;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            ManaComponent m = Mappers.mana.get(entity);
            if (m.mana > 0) {
                PositionComponent p = Mappers.position.get(entity);
                BoundsComponent b = Mappers.bounds.get(entity);
                float fx = p.x + (b.rect.width / 2f) - 8f;
                float fy = p.y + b.rect.height;
                factory.createFireball(fx, fy);
                m.mana -= 1;
            }
        }
    }
}
