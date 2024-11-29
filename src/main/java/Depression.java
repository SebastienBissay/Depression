import processing.core.PApplet;
import processing.core.PVector;

import static parameters.Parameters.*;
import static save.SaveUtil.saveSketch;

public class Depression extends PApplet {
    public static void main(String[] args) {
        PApplet.main(Depression.class);
    }

    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
        randomSeed(SEED);
    }

    @Override
    public void setup() {
        background(BACKGROUND_COLOR.red(), BACKGROUND_COLOR.green(), BACKGROUND_COLOR.blue());
        stroke(STROKE_COLOR.red(), STROKE_COLOR.green(), STROKE_COLOR.blue(), STROKE_COLOR.alpha());
        fill(FILL_COLOR.red(), FILL_COLOR.green(), FILL_COLOR.blue(), FILL_COLOR.alpha());
        noLoop();
    }

    @Override
    public void draw() {
        for (int i = 0; i < NUMBER_OF_ITERATIONS; i++) {
            PVector p0 = new PVector(random(width), 0);
            PVector p1 = new PVector(random(p0.x),
                    height * (BEZIER_FIRST_CONTROL_POINT_OFFSET
                            + BEZIER_GAUSSIAN_FACTOR * (p0.x / width) * randomGaussian()));
            PVector p2 = new PVector(random(p1.x),
                    height * (BEZIER_SECOND_CONTROL_POINT_OFFSET
                            + BEZIER_GAUSSIAN_FACTOR * (p1.x / width) * randomGaussian()));
            PVector p3 = new PVector(random(p2.x), height);

            for (int k = 0; k < width; k++) {
                float t = 1 - exp(-abs(randomGaussian()));
                float x = pow(1 - t, 3) * p0.x
                        + 3 * sq(1 - t) * t * p1.x
                        + 3 * (1 - t) * sq(t) * p2.x
                        + pow(t, 3) * p3.x;
                float y = pow(1 - t, 3) * p0.y
                        + 3 * sq(1 - t) * t * p1.y
                        + 3 * (1 - t) * sq(t) * p2.y
                        + pow(t, 3) * p3.y;

                if (random(1) < CHANCE_OF_CIRCLE) {
                    int diameter = floor(random(1, (width - x) * CIRCLE_RADIUS_FACTOR));
                    circle(x, y, diameter);
                } else {
                    point(x, y);
                }
            }
        }
        saveSketch(this);
    }
}
