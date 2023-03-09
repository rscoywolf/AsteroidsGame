import java.awt.Color;
import java.awt.Polygon;
import java.util.Random;

public class Asteroid extends VectorSprite {
  int sizemod;
  int score;

  public Asteroid(
    double x,
    double y,
    double xspeed,
    double yspeed,
    int sizemod
  ) {
    this.setColor(Color.blue);
    speedx = xspeed;
    speedy = yspeed;
    angle = 0.0;
    alive = true;
    xpos = x;
    ypos = y;
    shape = new Polygon();
    drawshape = new Polygon();
    this.sizemod = sizemod;
    this.score = 500 / sizemod;
    for (int i = 0; i < 360; i += (360 / 6)) {
      shape.addPoint(
        (int) (40 / sizemod * Math.cos(Math.toRadians(i))),
        (int) (40 / sizemod * Math.sin(Math.toRadians(i)))
      );
      drawshape.addPoint(
        (int) (40 / sizemod * Math.cos(Math.toRadians(i))),
        (int) (40 / sizemod * Math.sin(Math.toRadians(i)))
      );
    }

    /*
     * shape.addPoint(50 / 2, 0); shape.addPoint(80 / 2, -50 / 2);
     * shape.addPoint(20 / 2, -70 / 2); shape.addPoint(-50 / 2, -60 / 2);
     * shape.addPoint(-20 / 2, -20 / 2); drawshape.addPoint(100 / 2, 0);
     * drawshape.addPoint(80 / 2, -140 / 2); drawshape.addPoint(20 / 2, -70
     * / 2); drawshape.addPoint(-50 / 2, -60 / 2); drawshape.addPoint(-98 /
     * 2, -20 / 2);
     *
     * shape.addPoint(450,554); shape.addPoint(530,570); shape.addPoint(560,
     * 423); shape.addPoint(421, 400); shape.addPoint(400, 390);
     *
     * shape.addPoint( (int) Math.round(366), (int)Math.round(479));
     * shape.addPoint( (int) Math.round(345), (int)Math.round(350));
     * shape.addPoint( (int) Math.round(408), (int)Math.round(450));
     * shape.addPoint( (int) Math.round(490), (int)Math.round(480));
     */
    Asteroids.asteroids.add(this);
  }

  public void die(Laser rlaser) {
    if (this.sizemod < 3) {
      Random r = new Random();
      for (int j = 0; j < (r.nextInt(4) + 2); j++) {
        float speed = 2 / 10 + 10;
        if (Asteroids.mirror == true) {
          new MirrorAsteroid(
            this.xpos,
            this.ypos,
            r.nextFloat() * speed - speed / 2,
            r.nextFloat() * speed - speed / 2,
            this.sizemod + 1
          );
        } else {
          new Asteroid(
            this.xpos,
            this.ypos,
            r.nextFloat() * speed - speed / 2,
            r.nextFloat() * speed - speed / 2,
            this.sizemod + 1
          );
        }
      }
    }
  }
}
