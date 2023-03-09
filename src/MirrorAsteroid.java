import java.awt.Color;

public class MirrorAsteroid extends Asteroid {

  public MirrorAsteroid(
    double x,
    double y,
    double xspeed,
    double yspeed,
    int sizemod
  ) {
    super(x, y, xspeed, yspeed, sizemod);
    color = Color.white;
  }

  @Override
  public void die(Laser rlaser) {
    super.die(rlaser);
    Asteroids.rLasers.add(
      new Laser(xpos, ypos, rlaser.angle - Math.toRadians(180), Color.white)
    );
  }
}
