import processing.core.PVector;

import java.util.ArrayList;

public class Game {
    public static final int PLAYER_START_SIZE = 20;
    public static final float FOOD_RADIUS = 10;
    public int width;
    public int height;
    public Circle player;
    public PVector playerDir;
    public float playerSpeed = 5f;
    public int eatenCount = 0;
    public ArrayList<Circle> food = new ArrayList<>();
    public int maxFoodCount = 20;
    public ArrayList<Circle> badFood = new ArrayList<>();

    public Game(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void tick(float mouseX, float mouseY) {
        if (player == null && mouseX != 0 && mouseY != 0) {
            this.player = new Circle(mouseX, mouseY, PLAYER_START_SIZE);
        } else if (player != null) {
            PVector mouseWorldPos = new PVector(mouseX - width / 2f, mouseY - height / 2f);
            mouseWorldPos.add(player.pos);
            player.moveTo(mouseWorldPos, playerSpeed);
            playerDir = PVector.sub(player.pos, mouseWorldPos);
            playerDir.normalize();

            int addCount = maxFoodCount - food.size();
            for (int i = 0; i < addCount; i += 1) {
                // food.add(Circle.withRandomPosition(width, height, FOOD_RADIUS));
                var d = 500f;
                var c = new Circle(player.pos.x + Circle.randomInRange(-d, d), player.pos.y + Circle.randomInRange(-d, d), FOOD_RADIUS);
                food.add(c);
            }

            for (Circle c : food) {
                if (player.collides(c)) {
                    c.randomizePos(width, height);
                    onFoodConsumed();
                }
            }

            for (var circle : badFood) {
                if (player.collides(circle)) {
                    resetGame();
                    break;
                }
            }
        }
    }

    public boolean shouldSpawnBadFood() {
        return eatenCount % 10 == 0;
    }

    public void onFoodConsumed() {
        player.rad += 2;
        eatenCount += 1;
        if (shouldSpawnBadFood()) {
            badFood.add(Circle.withRandomPosition(width, height, FOOD_RADIUS));
        }
    }

    void resetGame() {
        player.rad = PLAYER_START_SIZE;
        eatenCount = 0;
        badFood.clear();
    }
}