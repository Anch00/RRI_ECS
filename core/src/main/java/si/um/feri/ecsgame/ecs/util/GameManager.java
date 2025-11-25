package si.um.feri.ecsgame.ecs.util;

import com.badlogic.gdx.math.RandomXS128;

public class GameManager {
    private static final GameManager I = new GameManager();
    public static GameManager i() { return I; }

    public final RandomXS128 rng = new RandomXS128();
    public int score = 0;
    public boolean gameOver = false;

    private GameManager() {}
    public void reset() {
        score = 0;
        gameOver = false;
    }
}
