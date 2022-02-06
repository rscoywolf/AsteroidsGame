import java.awt.Color;


public class AlienShip extends VectorSprite {
	public int eshootdelay = 30;
	public int shotTime =0;
	public AlienShip(int x, int y) {
		xpos = x;
		ypos = y;
		color = Color.green;
		speedx = 10;
		speedy = 0;
		alive = true;
		angle = 0.0;
		
//		for (int i = 0; i < 360; i ++) {
//			shape.addPoint((int) (15 *  Math.cos(Math.toRadians(i))),
//					(int) (15 *  Math.sin(Math.toRadians(i))));
//
//			drawshape.addPoint(
//					(int) (40 * Math.cos(Math.toRadians(i))),
//					(int) (40 * Math.sin(Math.toRadians(i))));
//		}
		shape.addPoint(-3 * 2, 0 * 2);
		shape.addPoint(3 * 2, 0* 2);
		shape.addPoint(7 * 2, 3* 2);
		shape.addPoint(10 * 2, 3* 2);
		shape.addPoint(12 * 2, 5* 2);
		shape.addPoint(10 * 2, 7* 2);
		shape.addPoint(-10 * 2, 7* 2);
		shape.addPoint(-12 * 2, 5* 2);
		shape.addPoint(-10 * 2, 3* 2);
		shape.addPoint(-7 * 2, 3* 2);
		
		
		drawshape.addPoint(-3 * 2, 0 * 2);
		drawshape.addPoint(3 * 2, 0* 2);
		drawshape.addPoint(7 * 2, 3* 2);
		drawshape.addPoint(10 * 2, 3* 2);
		drawshape.addPoint(12 * 2, 5* 2);
		drawshape.addPoint(10 * 2, 7* 2);
		drawshape.addPoint(-10 * 2, 7* 2);
		drawshape.addPoint(-12 * 2, 5* 2);
		drawshape.addPoint(-10 * 2, 3* 2);
		drawshape.addPoint(-7 * 2, 3* 2);
		
		

		
	
	}
	@Override
	public void wrapAround() {
		if (xpos <= 0) {
			speedx = 10;
			ypos +=25;
		} else if (xpos >= 800) {
			speedx = -10;
			ypos +=25;
		}
		
		if (ypos >= 600) {
			ypos = 0;
		}
	}
	public void shoot(VectorSprite target){
		double xDist = xpos - target.xpos;
		double yDist = ypos - target.ypos;
		double fireAngle = Math.atan2(yDist, xDist);
		Asteroids.eLasers.add(new Laser(xpos,ypos,fireAngle -Math.PI/2,Color.blue));
	}
	
}