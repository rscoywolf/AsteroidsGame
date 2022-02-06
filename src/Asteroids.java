import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.applet.*;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Asteroids extends Applet implements KeyListener, ActionListener {
	Ship ship;
	AlienShip eShip;
	Asteroid a;
	Timer time;
	Graphics offG;
	Image offScreen;
	AudioClip background, laser, thrust, explode, levelup, startup, die, alarm;
	public static ArrayList<Asteroid> asteroids;
	public static ArrayList<Laser> lasers;
	public static ArrayList<Laser> eLasers;
	public static ArrayList<Laser> rLasers;
	public static ArrayList<Integer> alevels;
	public static boolean mirror;
	int spawnDelay = 0;
	int score = 0;
	int eslevel;
	int level = 1;
	int upgrade = 0;
	int displaytime = 0;
	int levelupscore = 6000;
	boolean start;
	boolean win;
	boolean started = false;

	Random r;

	public void init() {
		start = false;
		r = new Random();
		setSize(800, 600);
		offScreen = this.createImage(this.getWidth(), this.getHeight());
		offG = offScreen.getGraphics();
		time = new Timer(20, this);
		this.addKeyListener(this);

		laser = getAudioClip(getCodeBase(), "laser80.wav");
		thrust = getAudioClip(getCodeBase(), "thruster.wav");
		explode = getAudioClip(getCodeBase(), "explode0.wav");
		levelup = getAudioClip(getCodeBase(), "level.wav");
		startup = getAudioClip(getCodeBase(), "start.wav");
		die = getAudioClip(getCodeBase(), "bgkill.wav");
		background = getAudioClip(getCodeBase(), "back.wav");
		alarm = getAudioClip(getCodeBase(), "alarm.wav");

		eShip = new AlienShip(400, 400);
		ship = new Ship(100, 100);
		lasers = new ArrayList<Laser>();
		asteroids = new ArrayList<Asteroid>();
		eLasers = new ArrayList<Laser>();
		rLasers = new ArrayList<Laser>();
		// setBackground(Color.black);

	}

	public void initsound() {

	}

	public void paint(Graphics g) {
		offG.setColor(Color.black);
		offG.fillRect(0, 0, 800, 600);
		if (start == true) {
			ship.draw(offG);
			offG.setColor(Color.yellow);
			offG.drawString("Score:" + score, 300, 70);
			offG.setColor(Color.GREEN);
			offG.drawString("Level:" + level, 300, 80);
			offG.setColor(Color.BLUE);
			offG.drawString("Shields:" + ship.lives + "%", 300, 90);
			offG.setColor(Color.orange);
			offG.drawString("Ship lvl. " + ship.shiplevel, 300, 100);
			if (displaytime > 0) {
				if (upgrade == 0) {
					offG.setColor(Color.orange);
					offG.drawString("Upgraded Shields", 10, 10);
				} else if (upgrade == 1) {
					offG.setColor(Color.orange);
					offG.drawString("Upgraded laser speed", 10, 10);
				} else if (upgrade == 2) {
					offG.setColor(Color.orange);
					offG.drawString("Upgraded lasers range", 10, 10);
				}
			}

			g.setColor(Color.black);
			

			for (Asteroid asteroid : asteroids) {
				asteroid.draw(offG);
			}
			for (Laser laser : lasers) {
				laser.draw(offG);

			}
			for (Laser laser : eLasers) {
				laser.draw(offG);
			}

			for (Laser laser : rLasers) {
				laser.draw(offG);
			}

			if (eShip != null && eShip.alive) {
				eShip.draw(offG);
			}
		} else {
			offG.setColor(Color.GREEN);
			String msg = "Press s to start!";
			offG.drawString(msg, 325, 300);
		}

		g.drawImage(offScreen, 0, 0, this.getWidth(), this.getHeight(), null);
		repaint();
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void start() {
		time.start();
	}

	public void stop() {
		time.stop();
	}

	public void winreset() {
		lasers.removeAll(lasers);
		asteroids.removeAll(asteroids);
		spawnDelay = 0;
		win = false;

	}

	public void reset() {
		lasers.removeAll(lasers);
		eLasers.removeAll(lasers);
		rLasers.removeAll(lasers);
		asteroids.removeAll(asteroids);
		ship.reset(100, 100);
		spawnDelay = 0;
		win = false;
		ship.lives = ship.defaultlives;
		ship.shiplevel = 1;
		Laser.lspeed = 10;
		Laser.maxLifeTime = 60;
		ship.ShootDelay = 8;
	}

	void loop() {
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			ship.left = true;
		} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.right = true;
		} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			ship.up = true;
			if (ship.alive = true) {
				thrust.play();
			}

		} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			ship.down = true;
		} else if (arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
			ship.shift = true;
		} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			ship.enter = true;
		} else if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			ship.backspace = true;
		} else if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			ship.space = true;
			if (ship.alive = true) {
				laser.play();
			}
		} else if (arg0.getKeyCode() == KeyEvent.VK_S) {
			start = true;
			startup.play();
		}

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			ship.left = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.right = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			ship.up = false;
			thrust.stop();
		} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			ship.down = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_SHIFT) {
			ship.shift = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			ship.enter = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			ship.backspace = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			ship.space = false;
		} else if (arg0.getKeyCode() == KeyEvent.VK_O) {
			background.stop();
		} else if (arg0.getKeyCode() == KeyEvent.VK_P) {
			background.loop();
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		displaytime--;
		if (score >= levelupscore) {
			levelup.play();
			ship.shiplevel++;
			levelupscore = levelupscore + 500;
			;
			score = score - levelupscore;
			int powerup = r.nextInt(3);
			switch (powerup) {
			case 0:
				upgrade = 0;
				ship.lives = ship.lives + 10;
				break;
			case 1:
				upgrade = 1;
				ship.ShootDelay = (int) (ship.ShootDelay - 0.5);
				Laser.lspeed = Laser.lspeed + 1;
				break;
			case 2:
				upgrade = 2;
				Laser.maxLifeTime = Laser.maxLifeTime + 5;
				break;
			}
			displaytime = 150;
		}
		if (score < 0) {
			score = 0;
		}
		if (ship.lives <= 0) {
			background.stop();
			die.play();
			ship.alive = false;
			level = 1;
			score = 0;
			start = false;
			reset();
		}
		if (win == true) {
			level++;
			winreset();
		}
		if (start == true) {
			ship.update();
			if (eShip != null) {
				eShip.update();

				if (eShip.alive && eShip.eshootdelay == eShip.shotTime++) {
					laser.play();
					eShip.shoot(ship);
					eShip.shotTime = 0;
				}
			}

			if (spawnDelay == 100) {
//				if (alevels.contains(level)) {
//					alarm.play();
					eShip = new AlienShip(300, 300);
//				}
				Random r = new Random();
				int mirrorc = r.nextInt(3);
				switch (mirrorc) {
				case 1:
					for (int i = 0; i < level; i++) {
						a = new MirrorAsteroid(r.nextInt(800), r.nextInt(600),
								r.nextInt(11) - 5, r.nextInt(11) - 5, 1);
						spawnDelay++;
						mirror = true;
					}
					break;
				case 2:
					for (int i = 0; i < level; i++) {
						a = new Asteroid(r.nextInt(800), r.nextInt(600),
								r.nextInt(11) - 5, r.nextInt(11) - 5, 1);
						spawnDelay++;
						mirror = false;
					}
					break;
				case 3:
					for (int i = 0; i < level; i++) {
						a = new Asteroid(r.nextInt(800), r.nextInt(600),
								r.nextInt(11) - 5, r.nextInt(11) - 5, 1);
						spawnDelay++;
					}
					break;
				}

			} else {
				spawnDelay++;
			}
			for (int i = 0; i < asteroids.size(); i++) {
				if (asteroids.get(i).colide(ship)) {
					ship.lives--;
				}
				for (int j = 0; j < lasers.size(); j++) {
					if (asteroids.get(i).colide(lasers.get(j))
							&& asteroids.get(i).alive && lasers.get(j).alive) {
						explode.play();
						asteroids.get(i).die(lasers.get(j));
						asteroids.get(i).alive = false;
						lasers.get(j).alive = false;

					}
				}

				for (int j = 0; j < eLasers.size(); j++) {
					if (asteroids.get(i).colide(eLasers.get(j))
							&& asteroids.get(i).alive && eLasers.get(j).alive) {
						explode.play();
						asteroids.get(i).die(eLasers.get(j));
						asteroids.get(i).alive = false;
						eLasers.get(j).alive = false;
					}
				}

				asteroids.get(i).update();
			}
			for (Laser laser : eLasers) {
				if (ship.colide(laser)) {
					ship.lives -= 5;
					laser.alive = false;

				}
			}
			for (Laser laser : rLasers) {
				if (ship.colide(laser)) {
					ship.lives -= 10;
					laser.alive = false;

				}
			}
			for (Laser laser : lasers) {
				if (eShip != null && eShip.colide(laser)) {
					eShip.alive = false;
					laser.alive = false;
					score += 1000;
				}
			}
			for (Laser laser : rLasers) {
				if (eShip != null && eShip.colide(laser)) {
					eShip.alive = false;
					laser.alive = false;
					score += 2000;
				}
			}

			for (int i = 0; i < asteroids.size(); i++) {
				if (!asteroids.get(i).alive) {
					score += asteroids.get(i).score;
					asteroids.remove(asteroids.get(i));
				}
				if (asteroids.isEmpty()) {
					win = true;

				} else {
					win = false;
				}
			}

			for (int i = 0; i < lasers.size(); i++) {
				lasers.get(i).update();

			}
			for (int i = 0; i < eLasers.size(); i++) {
				eLasers.get(i).update();
			}

			for (int i = 0; i < rLasers.size(); i++) {
				rLasers.get(i).update();
			}

			for (int i = 0; i < lasers.size(); i++) {
				if (!lasers.get(i).alive) {
					lasers.remove(i);
				}
			}
			for (int i = 0; i < lasers.size(); i++) {
				if (!lasers.get(i).alive) {
					lasers.remove(i);
				}
			}
			for (int i = 0; i < eLasers.size(); i++) {
				if (!eLasers.get(i).alive) {
					eLasers.remove(i);
				}
			}
		} else {
			reset();

		}
	}

}
