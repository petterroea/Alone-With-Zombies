package net.petterroea.awz2;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class EntityPlayer extends Entity {
	/**
	 * Width of the entity
	 */
	public int width = 32;
	/**
	 * Height of the entity
	 */
	public int height = 32;
	Weapon[] weapons;
	int health = 100;
	int healthPerFrame = 25;
	int maxHealth = 100;
	public double rot = 0;
	public double gunRot = 0;
	long lastHeal = System.currentTimeMillis();
	public int degrot = 0;
	public int points = 0;
	double lastxspeed = 0;
	double lastyspeed = 0;
	String name = "UNNAMED";
	public EntityPlayer(int x, int y) {
		super(x, y);
		this.width = 32;
		this.height = 32;
		// TODO Auto-generated constructor stub
		this.entitysprite = MediaManager.player;
		weapons = new Weapon[2];
		weapons[0] = Weapon.getWeapon("gun");
	}
	public EntityPlayer(double x, double y) {
		super((int)x, (int)y);
		this.width = 32;
		this.height = 32;
		// TODO Auto-generated constructor stub
		this.entitysprite = MediaManager.player;
		weapons = new Weapon[2];
		weapons[0] = Weapon.getWeapon("gun");
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "player";
	}
	public void update(int delta, Map map, int pos) {
		if(pos == map.playerindex)
		{
			if(health < 100 && System.currentTimeMillis() - lastHeal > 2000)
			{
				health++;
				lastHeal = System.currentTimeMillis();
			}
			if(Input.keys[Options.USE])
			{
				Rectangle damageMe = new Rectangle((int)x-16, (int)y-16, width+32, height+32);
				for(int i = 0; i < map.doors.size(); i++)
				{
					Rectangle theDoor = new Rectangle(map.doors.get(i).x, map.doors.get(i).y, map.tilew, map.tileh);
					if(damageMe.intersects(theDoor))
					{
						//Try to heal it
						TileDoor door = (TileDoor)map.tiles[map.doors.get(i).x/map.tilew][map.doors.get(i).y/map.tileh];
						door.healMe(this, map);
					}
				}
				for(int i = 0; i < map.vendors.size(); i++)
				{
					Rectangle theVendor = new Rectangle(map.vendors.get(i).x, map.vendors.get(i).y, map.tilew, map.tileh);
					if(damageMe.intersects(theVendor))
					{
						TileVendor vendor = (TileVendor)map.tiles[map.vendors.get(i).x/map.tilew][map.vendors.get(i).y/map.tileh];
						vendor.use(map, this);
					}
				}
				for(int i = 0; i < map.locked.size(); i++)
				{
					Rectangle theVendor = new Rectangle(map.locked.get(i).x, map.locked.get(i).y, map.tilew, map.tileh);
					if(damageMe.intersects(theVendor))
					{
						TileLockedDoor vendor = (TileLockedDoor)map.tiles[map.locked.get(i).x/map.tilew][map.locked.get(i).y/map.tileh];
						vendor.use(map, this);
					}
				}
			}
			else
			{
				Rectangle damageMe = new Rectangle((int)x-16, (int)y-16, width+32, height+32);
				for(int i = 0; i < map.doors.size(); i++)
				{
					Rectangle theDoor = new Rectangle(map.doors.get(i).x, map.doors.get(i).y, map.tilew, map.tileh);
					if(damageMe.intersects(theDoor))
					{
						TileDoor door = (TileDoor)map.tiles[map.doors.get(i).x/map.tilew][map.doors.get(i).y/map.tileh];
						if(door.lives < door.maxlives)
						{
							map.msg = "Hold space to heal door";
						}
						else
						{
							map.msg = "The door is fully healed";
						}
					}
				}
				for(int i = 0; i < map.vendors.size(); i++)
				{
					Rectangle theVendor = new Rectangle(map.vendors.get(i).x, map.vendors.get(i).y, map.tilew, map.tileh);
					if(damageMe.intersects(theVendor))
					{
						TileVendor vendor = (TileVendor)map.tiles[map.vendors.get(i).x/map.tilew][map.vendors.get(i).y/map.tileh];
						map.msg = vendor.getUseText(this);
					}
				}
				for(int i = 0; i < map.locked.size(); i++)
				{
					Rectangle theVendor = new Rectangle(map.locked.get(i).x, map.locked.get(i).y, map.tilew, map.tileh);
					if(damageMe.intersects(theVendor))
					{
						TileLockedDoor vendor = (TileLockedDoor)map.tiles[map.locked.get(i).x/map.tilew][map.locked.get(i).y/map.tileh];
						map.msg = vendor.getUseText(this);
					}
				}
			}
		}
		
		boolean change = false;
		double xm = (x + map.xoff + (width / 2)) - Input.mousex;
		double ym = (y + map.yoff + (height / 2)) - Input.mousey;
		float radiansToMouse = (float) Math.atan2(xm, ym);
		rot = -radiansToMouse;
		gunRot = Math.atan2(-xm, -ym);
		float degreesToMouse = ((57.2957795f * radiansToMouse) * -1) - 90;
		degrot = (int)degreesToMouse;
		super.width = 32;
		super.height = 32;
		if(pos == map.playerindex)//TODO: REmove acceleration
		{
			boolean xb = false;
			boolean yb = false;
			if(Input.keys[Options.UP])
			{
				yspeed = -0.15;		
				yb = true;
			}
			if(Input.keys[Options.DOWN])
			{
				yspeed = 0.15;
				yb = true;
			}
			if(Input.keys[Options.LEFT])
			{
				xspeed = -0.15;
				xb = true;
			}
			if(Input.keys[Options.RIGHT])
			{
				xspeed = 0.15;
				xb = true;
			}
			if(!xb)
			{
				xspeed = 0;
			}
			if(!yb)
			{
				yspeed = 0;
			}
			if(xspeed > 0.15)
			{
				xspeed = 0.15;
				change = true;
			}
			if(yspeed > 0.15)
			{
				yspeed = 0.15;
				change = true;
			}
			if(xspeed < -0.15)
			{
				xspeed = -0.15;
				change = true;
			}
			if(yspeed < -0.15)
			{
				yspeed = -0.15;
				change = true;
			}
		}
		//System.out.println("Xspeed: " + xspeed + ", Yspeed: " + yspeed);
		super.update(delta, map, pos);
		if(y < -map.yoff + 100)
		{
			map.yoff = map.yoff + 2;
		}
		if(y > -map.yoff - 250 + Game.HEIGHT)
		{
			map.yoff = map.yoff - 2;
		}
		if(x < -map.xoff + 100)
		{
			map.xoff = map.xoff + 2;
		}
		if(x > -map.xoff - 200 + Game.WIDTH)
		{
			map.xoff = map.xoff - 2;
		}
		if(map.playerindex == pos)
		{
			map.playerx = (int)x;
			map.playery = (int)y;
			map.playerhealth = health;
			map.playerRot = gunRot;
			map.playerPoints = points;
			if(xspeed != lastxspeed || yspeed != lastyspeed || change)
			{
				map.iMoved(this);
			}
			lastxspeed = xspeed;
			lastyspeed = yspeed;
		}
		
	}
	@Override
	public void draw(Graphics g, int xoff, int yoff, Map map, int meIndex) {
		if(isVisible(xoff, yoff))
		{
			g.drawImage(MediaManager.rotate(MediaManager.player.getBufferedImage(), rot), (int)x + xoff - 16, (int)y + yoff - 16, null);
			if(map instanceof MpMap)
			{
				//We are multiplayer
				MediaManager.font.draw(g, (int)x+xoff+16 - ((MediaManager.font.getCharWidth()*name.length()) / 2), (int)y+32+yoff, name, null);
			}
		}
		if(map.playerindex == meIndex)
		{
			GameScreen.fovx = (int)x + xoff - (MediaManager.fov.getBufferedImage().getWidth() / 2) + 16;
			GameScreen.fovy = (int)y + yoff - (MediaManager.fov.getBufferedImage().getHeight() / 2) + 16;
			MpGameScreen.fovx = (int)x + xoff - (MediaManager.fov.getBufferedImage().getWidth() / 2) + 16;
			MpGameScreen.fovy = (int)y + yoff - (MediaManager.fov.getBufferedImage().getHeight() / 2) + 16;
		//MediaManager.font.draw(g, (int)(x + xoff) - 32, (int)(y + 64 + yoff), (int)x + " " + (int)y, null);
		}
	}
	public void damage(int amount) {
		health = health - amount;
		if(health < 0)
		{
			health = 0;
		}
		
	}

}
