package si.um.feri.ecsgame.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class FollowComponent implements Component {
    public Entity target; // entiteta, ki ji sledimo (projektil)
    public float offsetX = 0f, offsetY = 0f;
}
