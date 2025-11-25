package si.um.feri.ecsgame.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.audio.Sound;
import si.um.feri.ecsgame.assets.AssetPaths;
import si.um.feri.ecsgame.assets.GameAssets;
import si.um.feri.ecsgame.ecs.components.*;
import si.um.feri.ecsgame.ecs.util.Mappers;

public class CollisionSystem extends IteratingSystem {
    private final Engine engine;
    private final Sound sndCollect, sndPotion, sndPowerup, sndHit, sndGameOver;

    public CollisionSystem(Engine engine, GameAssets assets) {
        super(Family.all(BoundsComponent.class).get());
        this.engine = engine;
        sndCollect = assets.get(AssetPaths.SND_COLLECT, Sound.class);
        sndPotion = assets.get(AssetPaths.SND_POTION, Sound.class);
        sndPowerup = assets.get(AssetPaths.SND_POWERUP, Sound.class);
        sndHit = assets.get(AssetPaths.SND_HIT, Sound.class);
        sndGameOver = assets.get(AssetPaths.SND_GAMEOVER, Sound.class);
    }

    @Override
    public void update(float dt) {
        if (GameStateSystem.isPausedOrOver(getEngine())) return;
        super.update(dt);
    }

    @Override
    protected void processEntity(Entity e, float dt) {
        if (Mappers.player.get(e) != null) {
            BoundsComponent pb = Mappers.bounds.get(e);
            HealthComponent ph = Mappers.health.get(e);
            ManaComponent pm = Mappers.mana.get(e);
            ShieldComponent ps = Mappers.shield.get(e);
            ScoreComponent sc = Mappers.score.get(e);

            for (Entity itemEnt : getEngine().getEntitiesFor(Family.all(ItemComponent.class, BoundsComponent.class).get())) {
                BoundsComponent ib = Mappers.bounds.get(itemEnt);
                if (pb.rect.overlaps(ib.rect)) {
                    ItemComponent ic = Mappers.item.get(itemEnt);
                    if (ic.type == 0) { pm.mana = Math.min(pm.max, pm.mana + 1); if (sc != null) sc.score += 10; sndCollect.play(); }
                    else if (ic.type == 1) { pm.mana = Math.min(pm.max, pm.mana + 3); if (sc != null) sc.score += 30; sndPotion.play(); }
                    else if (ic.type == 2) { ps.active = true; if (sc != null) sc.score += 25; sndPowerup.play(); }
                    getEngine().removeEntity(itemEnt);
                }
            }

            for (Entity enemy : getEngine().getEntitiesFor(Family.all(EnemyComponent.class, BoundsComponent.class).get())) {
                if (Mappers.item.get(enemy) != null) continue;
                BoundsComponent eb = Mappers.bounds.get(enemy);
                if (pb.rect.overlaps(eb.rect)) {
                    getEngine().removeEntity(enemy);
                    if (ps.active) {
                        ps.active = false;
                    } else {
                        ph.hp = Math.max(0, ph.hp - 20);
                        sndHit.play();
                        if (ph.hp <= 0) {
                            sndGameOver.play();
                            // označi game over
                            for (Entity s : getEngine().getEntitiesFor(Family.all(GameStateComponent.class).get())) {
                                s.getComponent(GameStateComponent.class).gameOver = true;
                            }
                        }
                    }
                }
            }
        }

        if (Mappers.projectile.get(e) != null) {
            BoundsComponent pb = Mappers.bounds.get(e);
            for (Entity enemy : getEngine().getEntitiesFor(Family.all(EnemyComponent.class, BoundsComponent.class).get())) {
                if (Mappers.item.get(enemy) != null) continue;
                BoundsComponent eb = Mappers.bounds.get(enemy);
                if (pb.rect.overlaps(eb.rect)) {
                    for (Entity player : getEngine().getEntitiesFor(Family.all(PlayerComponent.class, ScoreComponent.class).get())) {
                        ScoreComponent sc = Mappers.score.get(player);
                        if (sc != null) { sc.hits += 1; sc.score += 20; }
                    }
                    // počisti trail, ki sledi temu projektilu
                    for (Entity trail : getEngine().getEntitiesFor(Family.all(ParticleComponent.class, FollowComponent.class).get())) {
                        FollowComponent f = trail.getComponent(FollowComponent.class);
                        if (f != null && f.target == e) {
                            ParticleComponent pc = Mappers.particle.get(trail);
                            if (pc != null && pc.effect != null) { pc.effect.free(); pc.effect = null; }
                            getEngine().removeEntity(trail);
                        }
                    }
                    getEngine().removeEntity(e);
                    getEngine().removeEntity(enemy);
                    break;
                }
            }
        }
    }
}
