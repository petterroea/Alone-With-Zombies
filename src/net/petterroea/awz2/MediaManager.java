package net.petterroea.awz2;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class MediaManager {
	public static Sprite logo;
	public static Font font;
	public static Sprite button;
	public static Sprite tiles;
	public static LinkedList<Map> maps;
	public static Sprite title;
	public static Sprite player;
	public static Sprite fov;
	public static Sprite zombie;
	public static Sprite health;
	public static Sprite blood;
	public static Sprite topShade;
	public static Sprite bottomShade;
	public static Sprite bullet;
	public static Sprite flame;
	public static Sprite ray;
	public static Sprite bloodParticle;
	public static void load()
	{
		try {
			bloodParticle = new Sprite("blood_particle.png", true);
			ray = new Sprite("ray.png", true);
			flame = new Sprite("flame.png", true);
			bullet = new Sprite("bullet.png", true);
			topShade = new Sprite("top.png", true);
			bottomShade = new Sprite("bottom.png", true);
			blood = new Sprite("blood.png", 300, 300, true);
			health = new Sprite("health.png", 2, 50, true);
			zombie = new Sprite("zombie.png", true);
			fov = new Sprite("fov.png", true);
			player = new Sprite("player.png", true);
			title = new Sprite("title.png", true);
			resetMaps();
			tiles = new Sprite("tiles.png", 32, 32, true);
			logo = new Sprite("logo.png", true);
			font = new Font(new Sprite("font.png", 16, 24, true));
			button = new Sprite("button.png", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void resetMaps() throws IOException
	{
		maps = new LinkedList<Map>();
		maps.add(new Map("underrachhish", ImageIO.read(MediaManager.class.getResourceAsStream("underrachhish.png")), 32, 32));
		maps.add(new Map("Corridor", ImageIO.read(MediaManager.class.getResourceAsStream("corridor.png")), 32, 32));
		maps.add(new Map("Dorfenvanrichoff(Port)", ImageIO.read(MediaManager.class.getResourceAsStream("dorfen_new.png")), 32, 32));
		maps.add(new Map("House", ImageIO.read(MediaManager.class.getResourceAsStream("house.png")), 32, 32));
	}
	public static BufferedImage rotate(BufferedImage read, double amount) {
		AffineTransform tx = new AffineTransform();
		tx.rotate(amount, read.getWidth()/2, read.getHeight()/2);

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		return op.filter(read, null);
	}
}
