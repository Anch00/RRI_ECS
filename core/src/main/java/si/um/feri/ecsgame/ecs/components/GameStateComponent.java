package si.um.feri.ecsgame.ecs.components;

import com.badlogic.ashley.core.Component;

public class GameStateComponent implements Component {
    public boolean paused = false;
    public boolean gameOver = false;
}
