import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public abstract class VectorSprite {
  protected Polygon shape;
  protected Polygon drawshape;
  protected double xpos, ypos;
  protected Color color;
  protected double angle;
  protected double speedx, speedy;
  boolean alive;

  public VectorSprite() {
    shape = new Polygon();
    drawshape = new Polygon();
    xpos = 0;
    ypos = 0;
    color = Color.red;
    speedx = 0;
    speedy = 0;
    angle = 0.0;
    alive = true;
  }

  public VectorSprite(int x, int y) {
    shape = new Polygon();
    drawshape = new Polygon();
    Polygon shape = new Polygon();
    xpos = x;
    ypos = y;
    color = Color.red;
    speedx = 0;
    speedy = 0;
    angle = Math.toRadians(0);

    shape.addPoint(100, 15);
    shape.addPoint(25, 5);
    shape.addPoint(5, 5);
  }

  public boolean colide(VectorSprite s) {
    for (int i = 0; i < s.drawshape.npoints; i++) {
      if (
        this.drawshape.contains(s.drawshape.xpoints[i], s.drawshape.ypoints[i])
      ) {
        return true;
      }
    }
    return false;
  }

  public void draw(Graphics g) {
    if (this.alive) {
      g.setColor(this.getColor());
      g.drawPolygon(drawshape);
    }
  }

  public void wrapAround() {
    if (xpos <= 0) {
      xpos = 800;
    } else if (xpos >= 800) {
      xpos = 0;
    }
    if (ypos <= 0) {
      ypos = 600;
    } else if (ypos >= 600) {
      ypos = 0;
    }
  }

  public void update() {
    int x, y;
    wrapAround();
    xpos += speedx;

    ypos += speedy;
    for (int i = 0; shape.npoints != 0 && i < shape.npoints; i++) {
      x =
        (int) Math.round(
          (
            Math.cos(angle) *
            shape.xpoints[i] -
            Math.sin(angle) *
            shape.ypoints[i]
          )
        );
      y =
        (int) Math.round(
          (
            Math.sin(angle) *
            shape.xpoints[i] +
            Math.cos(angle) *
            shape.ypoints[i]
          )
        );
      drawshape.xpoints[i] = x;
      drawshape.ypoints[i] = y;
    }
    drawshape.invalidate();
    drawshape.translate((int) Math.round(xpos), (int) Math.round(ypos));
  }

  // ///////////////////////////GETTERS &&
  // SETTERS//////////////////////////////////////////////

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public double getAngle() {
    return angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }
}
