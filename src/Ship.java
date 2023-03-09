import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Ship extends VectorSprite {
  public static final double ACCEL_AMOUNT = 1;
  public static final double ROTATE_AMOUNT = Math.toRadians(10);
  Polygon drawthrust, thrust;
  boolean accelerating, boosting;
  boolean left, right, up;
  boolean down, shift, enter;
  boolean space, backspace;

  int ShootDelay, count, shiplevel;
  int lives;
  int defaultlives;

  public Ship(int x, int y) {
    super(x, y);
    this.setColor(Color.lightGray);
    alive = true;
    ShootDelay = 8;
    shiplevel = 1;
    lives = 100;
    defaultlives = 100;
    count = 0;
    speedx = 0;
    speedy = 0;
    angle = 0.0;
    shape = new Polygon();
    thrust = new Polygon();
    drawthrust = new Polygon();
    accelerating = false;

    thrust.addPoint(15, 10);
    thrust.addPoint(25, 10);
    thrust.addPoint(16, 0);
    thrust.addPoint(25, -10);
    thrust.addPoint(15, -10);

    drawthrust.addPoint(10, 10);
    drawthrust.addPoint(25, 10);
    drawthrust.addPoint(16, 0);
    drawthrust.addPoint(25, -10);
    drawthrust.addPoint(15, -10);

    shape.addPoint(0, -15);
    shape.addPoint(10, 15);
    shape.addPoint(-10, 15);

    drawshape.addPoint(0, 30);
    drawshape.addPoint(10, 60);
    drawshape.addPoint(-10, 60);
  }

  public void draw(Graphics g) {
    /*
     * g.setColor(Color.white); g.drawString("X Speed:" + speedx, 500, 50);
     * g.drawString("Y Speed:" + speedy, 500, 60); g.setColor(Color.green);
     * g.drawString("X Location:" + xpos, 500, 80);
     * g.drawString("Y Location:" + ypos, 500, 90); g.setColor(Color.blue);
     * g.drawString("Angle:" + angle, 500, 110);
     */
    g.setColor(Color.black);
    if (alive == true) {
      super.draw(g);
      if (accelerating) {
        g.setColor(getThrustColor());
        g.drawPolygon(drawthrust);
      } else {
        g.setColor(Color.BLACK);
      }
    }
  }

  public Color getThrustColor() {
    if (boosting) {
      return Color.red;
    } else {
      return Color.blue;
    }
  }

  public void reset(int x, int y) {
    xpos = x;
    ypos = y;
    speedx = 0;
    speedy = 0;
    angle = 0;
    lives -= 1;
    alive = true;
  }

  public void update() {
    count++;
    keyUpdate();
    super.update();
    if (speedx > 60) {
      speedx = 60;
    } else if (speedx < -60) {
      speedx = -60;
    }
    if (speedy > 60) {
      speedy = 60;
    } else if (speedy < -60) {
      speedy = -60;
    }

    for (int i = 0; thrust.npoints != 0 && i < thrust.npoints; i++) {
      int x = (int) Math.round(
        (
          Math.cos(angle - 3 * Math.PI / 2) *
          thrust.xpoints[i] -
          Math.sin(angle - 3 * Math.PI / 2) *
          thrust.ypoints[i]
        )
      );
      int y = (int) Math.round(
        (
          Math.sin(angle - 3 * Math.PI / 2) *
          thrust.xpoints[i] +
          Math.cos(angle - 3 * Math.PI / 2) *
          thrust.ypoints[i]
        )
      );
      drawthrust.xpoints[i] = x;
      drawthrust.ypoints[i] = y;
    }
    drawthrust.translate((int) Math.round(xpos), (int) Math.round(ypos));
  }

  public void accel(int i) {
    if (i != 0) {
      this.speedx += i * ACCEL_AMOUNT * Math.cos(angle - Math.PI / 2);
      this.speedy += i * ACCEL_AMOUNT * Math.sin(angle - Math.PI / 2);
    } else {
      this.speedx = 0;
      this.speedy = 0;
    }
  }

  public void boost(int boostAmnt) {
    this.speedx = boostAmnt * ACCEL_AMOUNT * Math.cos(angle - Math.PI / 2);
    this.speedy = boostAmnt * ACCEL_AMOUNT * Math.sin(angle - Math.PI / 2);
  }

  public void rotate(int i) {
    if (angle > 6.4) {
      angle = 0;
    } else if (angle < -6.4) {
      angle = 0;
    }
    this.angle = this.angle - i * ROTATE_AMOUNT;
  }

  public void shoot() {
    if (this.alive == true) {
      if (count >= ShootDelay) {
        Asteroids.lasers.add(
          new Laser(this.xpos, this.ypos, this.angle, Color.RED)
        );
        // Asteroids.lasers.add(new Laser(this.xpos+5, this.ypos+7,
        // this.angle));
        count = 0;
      }
    }
  }

  public void keyUpdate() {
    if (this.alive == true) {
      if (left) {
        rotate(1);
      }
      if (right) {
        rotate(-1);
      }
      if (up) {
        accel(1);
        accelerating = true;
      }
      if (down) {
        accel(-1);
      }
      if (shift) {
        accel(0);
      }
      if (enter) {
        accel(1);
        boost(45);
        accelerating = true;
        boosting = true;
      }
      if (backspace) {
        boost(-10);
      }
      if (space) {
        shoot();
      }

      if (!up) {
        accelerating = false;
      }
      if (!enter) {
        boosting = false;
      }
    }
  }
}
