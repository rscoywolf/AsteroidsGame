import java.awt.Color;
import java.awt.Polygon;

public class Laser extends VectorSprite {
	int lifeTime;
	static int maxLifeTime;
	static int lspeed;

	public Laser(double xpos, double ypos, double angle,Color c) {
		this.xpos = xpos;
		this.ypos = ypos;
		this.angle = angle;
		lifeTime = 0;
		lspeed = 10;
		alive = true;
		maxLifeTime = 60;
		shape = new Polygon();
		drawshape = new Polygon();
		color = c;
		
		
		
		for (int i = 0; i < 30; i += (30 / 10)) {
			shape.addPoint(0, -1*i);
			drawshape.addPoint(0, -1*i);
		}
		this.speedx = lspeed * Math.cos(angle - Math.PI / 2);
		this.speedy = lspeed * Math.sin(angle - Math.PI / 2);
	}

	public void update() {
		super.update();
		lifeTime++;
		if (lifeTime >= maxLifeTime) {
			alive = false;
		}
	}
}
