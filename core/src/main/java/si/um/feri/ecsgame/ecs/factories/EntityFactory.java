package si.um.feri.ecsgame.ecs.factories;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import si.um.feri.ecsgame.assets.AssetPaths;
import si.um.feri.ecsgame.assets.GameAssets;
import si.um.feri.ecsgame.ecs.components.*;
import si.um.feri.ecsgame.ecs.util.Constants;

public class EntityFactory {
    private final Engine engine;
    private final GameAssets assets;

    private final ParticleEffectPool fireballTrailPool;

    public EntityFactory(Engine engine, GameAssets assets) {
        this.engine = engine;
        this.assets = assets;
        ParticleEffect fireballTrail = assets.get(AssetPaths.PFX_FIREBALL_TRAIL, ParticleEffect.class);
        this.fireballTrailPool = new ParticleEffectPool(fireballTrail, 4, 32);
    }

    private TextureRegion region(String path) {
        Texture tex = assets.get(path, Texture.class);
        return new TextureRegion(tex);
    }

    public Entity createBackground() {
        Entity e = new Entity();
        TextureComponent t = new TextureComponent();
        t.region = region(AssetPaths.IMG_BACKGROUND);
        t.width = Constants.WORLD_WIDTH;
        t.height = Constants.WORLD_HEIGHT;
        e.add(t);
        e.add(new BackgroundComponent());
        ZOrderComponent z = new ZOrderComponent();
        z.z = -1000;
        e.add(z);
        engine.addEntity(e);
        return e;
    }

    public Entity createPlayer(float x, float y) {
        Entity e = new Entity();

        PositionComponent p = new PositionComponent();
        p.x = x; p.y = y;

        VelocityComponent v = new VelocityComponent();

        TextureComponent t = new TextureComponent();
        t.region = region(AssetPaths.IMG_WIZARD);
        t.width = t.region.getRegionWidth() * Constants.WIZARD_SCALE;
        t.height = t.region.getRegionHeight() * Constants.WIZARD_SCALE;

        BoundsComponent b = new BoundsComponent();
        b.rect.set(x, y, t.width, t.height);

        HealthComponent h = new HealthComponent();
        h.hp = Constants.START_HEALTH;

        ManaComponent m = new ManaComponent();
        m.max = Constants.MAX_MANA;
        m.mana = 0;

        ShieldComponent s = new ShieldComponent();
        s.active = false;

        ScoreComponent sc = new ScoreComponent();

        e.add(p); e.add(v); e.add(t); e.add(b);
        e.add(h); e.add(m); e.add(s); e.add(sc);
        e.add(new PlayerComponent());

        ZOrderComponent z = new ZOrderComponent();
        z.z = 10;
        e.add(z);

        engine.addEntity(e);
        return e;
    }

    public Entity createCrystal(float x, float y) {
        Entity e = new Entity();

        PositionComponent p = new PositionComponent();
        p.x = x; p.y = y;

        VelocityComponent v = new VelocityComponent();
        v.vy = -Constants.CRYSTAL_SPEED;

        TextureComponent t = new TextureComponent();
        t.region = region(AssetPaths.IMG_CRYSTAL);
        t.width = t.region.getRegionWidth();
        t.height = t.region.getRegionHeight();

        BoundsComponent b = new BoundsComponent();
        b.rect.set(x, y, t.width, t.height);

        ItemComponent item = new ItemComponent();
        item.type = 0; // crystal

        e.add(p); e.add(v); e.add(t); e.add(b);
        e.add(item);

        ZOrderComponent z = new ZOrderComponent();
        z.z = 5;
        e.add(z);

        engine.addEntity(e);
        return e;
    }

    public Entity createPotion(float x, float y) {
        Entity e = new Entity();

        PositionComponent p = new PositionComponent();
        p.x = x; p.y = y;

        VelocityComponent v = new VelocityComponent();
        v.vy = -Constants.POTION_SPEED;

        TextureComponent t = new TextureComponent();
        t.region = region(AssetPaths.IMG_POTION);
        t.width = t.region.getRegionWidth();
        t.height = t.region.getRegionHeight();

        BoundsComponent b = new BoundsComponent();
        b.rect.set(x, y, t.width, t.height);

        ItemComponent item = new ItemComponent();
        item.type = 1; // potion

        e.add(p); e.add(v); e.add(t); e.add(b);
        e.add(item);

        ZOrderComponent z = new ZOrderComponent();
        z.z = 5;
        e.add(z);

        engine.addEntity(e);
        return e;
    }

    public Entity createShield(float x, float y) {
        Entity e = new Entity();

        PositionComponent p = new PositionComponent();
        p.x = x; p.y = y;

        VelocityComponent v = new VelocityComponent();
        v.vy = -Constants.POTION_SPEED;

        TextureComponent t = new TextureComponent();
        t.region = region(AssetPaths.IMG_SHIELD);
        t.width = t.region.getRegionWidth();
        t.height = t.region.getRegionHeight();

        BoundsComponent b = new BoundsComponent();
        b.rect.set(x, y, t.width, t.height);

        ItemComponent item = new ItemComponent();
        item.type = 2; // shield

        e.add(p); e.add(v); e.add(t); e.add(b);
        e.add(item);

        ZOrderComponent z = new ZOrderComponent();
        z.z = 5;
        e.add(z);

        engine.addEntity(e);
        return e;
    }

    public Entity createDarkOrb(float x, float y) {
        Entity e = new Entity();

        PositionComponent p = new PositionComponent();
        p.x = x; p.y = y;

        VelocityComponent v = new VelocityComponent();
        v.vy = -Constants.DARKORB_SPEED;

        TextureComponent t = new TextureComponent();
        t.region = region(AssetPaths.IMG_DARK_ORB);
        t.width = t.region.getRegionWidth() * Constants.DARKORB_SCALE;
        t.height = t.region.getRegionHeight() * Constants.DARKORB_SCALE;

        BoundsComponent b = new BoundsComponent();
        b.rect.set(x, y, t.width, t.height);

        e.add(p); e.add(v); e.add(t); e.add(b);
        e.add(new EnemyComponent());

        ZOrderComponent z = new ZOrderComponent();
        z.z = 6;
        e.add(z);

        engine.addEntity(e);
        return e;
    }

    public Entity createFireball(float x, float y) {
        // projektil
        Entity proj = new Entity();

        PositionComponent p = new PositionComponent();
        p.x = x; p.y = y;

        VelocityComponent v = new VelocityComponent();
        v.vy = Constants.FIREBALL_SPEED;

        TextureComponent t = new TextureComponent();
        t.region = region(AssetPaths.IMG_FIREBALL);
        t.width = t.region.getRegionWidth() * Constants.FIREBALL_SCALE;
        t.height = t.region.getRegionHeight() * Constants.FIREBALL_SCALE;

        BoundsComponent b = new BoundsComponent();
        b.rect.set(x, y, t.width, t.height);

        proj.add(p); proj.add(v); proj.add(t); proj.add(b);
        proj.add(new ProjectileComponent());

        ZOrderComponent z = new ZOrderComponent();
        z.z = 7;
        proj.add(z);

        engine.addEntity(proj);

        // trail, sledi projektilu (FollowComponent)
        Entity trail = new Entity();

        ParticleComponent pc = new ParticleComponent();
        pc.pool = fireballTrailPool;
        pc.attachedToEntity = false; // sledi prek FollowComponent
        trail.add(pc);

        FollowComponent follow = new FollowComponent();
        follow.target = proj;
        follow.offsetX = t.width / 2f;
        follow.offsetY = t.height / 2f;
        trail.add(follow);

        PositionComponent tp = new PositionComponent();
        tp.x = x; tp.y = y;
        trail.add(tp);

        ZOrderComponent tz = new ZOrderComponent();
        tz.z = 8;
        trail.add(tz);

        engine.addEntity(trail);

        // shoot sound
        Sound shoot = assets.get(AssetPaths.SND_SHOOT, Sound.class);
        shoot.play();

        return proj;
    }
}
