import processing.core.PApplet;
import processing.core.PVector;

public class Circle {
    public PVector pos;
    public float rad;

    public Circle(float x, float y, float rad) {
        this.pos = new PVector(x, y);
        this.rad = rad;
    }

    public static Circle withRandomPosition(float maxX, float maxY, float rad) {
        return new Circle((float) (Math.random() * maxX), (float) (Math.random() * maxY), rad);
    }

    public static float randomInRange(float min, float max) {
        return min + (float) Math.random() * (max - min);
    }

    public void randomizePos(float maxX, float maxY) {
        this.pos.x = (float) Math.random() * maxX;
        this.pos.y = (float) Math.random() * maxY;
    }

    public void draw(PApplet app, PVector camera, int colour) {
        app.push();
        app.fill(colour);
        app.translate(pos.x - camera.x, pos.y - camera.y);
        app.circle(0, 0, rad * 2);
        app.pop();
    }

    public void moveTo(PVector target, float amount) {
        var diff = PVector.sub(target, this.pos);
        if (diff.mag() < amount) {
            amount = diff.mag();
        }

        var dir = diff.normalize(null);
        var movementVector = PVector.mult(dir, amount);
        this.pos = PVector.add(this.pos, movementVector);
    }

    boolean collides(Circle other) {
        return rad + other.rad >= pos.dist(other.pos);
    }
}
