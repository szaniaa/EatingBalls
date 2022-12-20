import processing.core.PApplet;
import processing.core.PVector;

public class EatingBalls extends PApplet {
    PVector camera = new PVector(0, 0);
    Game game;

    @Override
    public void settings() {
        size(800, 800);
    }

    @Override
    public void setup() {
        game = new Game(width, height);
    }

    @Override
    public void draw() {
        game.tick(mouseX, mouseY);
        background(100);

        drawGrid();
        drawScore();
        translate(width / 2f, height / 2f);
        if (game.player != null) {
            var p = game.player;
            camera = p.pos;
            p.draw(this, camera, 0xFFFFFFFF);
            if (game.playerDir != null) {
                push();
                translate(p.pos.x - camera.x, p.pos.y - camera.y);
                rotate(game.playerDir.heading() + PI / 2 + PI);
                translate(0, p.rad);
                var h = p.rad * 0.6f;
                var x = p.rad * 0.4f;
                pop();
            }
        }

        for (Circle food : game.food) {
            food.draw(this, camera, 0xFF00AA00);
        }

        for (Circle badFood : game.badFood) {
            badFood.draw(this, camera, 0xFFFF0000);
        }
    }

    void drawGrid() {
        for (float x = 0; x < width; x += width / 8.0f) {
            stroke(80);
            line(x, 0, x, height);
        }

        for (float y = 0; y < height; y += height / 8.0f) {
            stroke(80);
            line(0, y, width, y);
        }
    }

    public void drawScore() {
        fill(50);
        textAlign(CENTER, CENTER);
        textSize(512);
        text(nf(game.eatenCount), width / 2.0f, height / 2.0f);
    }
}