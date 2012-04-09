package net.petterroea.awz2;

import java.awt.Graphics;
import java.awt.Rectangle;

public class EntityBullet extends Entity {
	double angle;
	EntityPlayer owner;
	double damage = 1.0;
	String special = "";
	public EntityBullet(int x, int y, double angle, EntityPlayer owner, double damage, String special) {
		super(x, y);
		this.special = special;
		this.damage = damage;
		this.owner = owner;
		// TODO Auto-generated constructor stub
		xspeed = Math.sin(angle)/2;
		yspeed = Math.cos(angle)/2;
		this.angle = angle;
		super.width = 6;
		super.height = 12;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "bullet";
	}
	@Override
	public void draw(Graphics g, int xoff, int yoff, Map map, int pos) {
		if(special.equalsIgnoreCase("flames"))
		{
			if(isVisible(xoff, yoff))
			{
				double degAngle = Math.toDegrees(angle)+180;
				g.drawImage(MediaManager.rotate(MediaManager.flame.getBufferedImage(), -Math.toRadians(degAngle)), (int)x + xoff - 10, (int)y + yoff - 10, null);
			}
		}
		else if(special.equalsIgnoreCase("ray"))
		{
			if(isVisible(xoff, yoff))
			{
				double degAngle = Math.toDegrees(angle)+180;
				g.drawImage(MediaManager.rotate(MediaManager.ray.getBufferedImage(), -Math.toRadians(degAngle)), (int)x + xoff - 10, (int)y + yoff - 10, null);
			}
		}
		else
		{
			if(isVisible(xoff, yoff))
			{
				double degAngle = Math.toDegrees(angle)+180;
				g.drawImage(MediaManager.rotate(MediaManager.bullet.getBufferedImage(), -Math.toRadians(degAngle)), (int)x + xoff - 10, (int)y + yoff - 10, null);
			}
		}
	}
	public void die(Map map)
	{
		removeMe = true;
	}
	@Override
	public void update(int delta, Map map, int pos) {
		double oldx = x;
		double oldy = y;
		x = x + (xspeed * delta);
		Rectangle me = new Rectangle((int)x, (int)y, width, height);
		//Sjekk x
		for(int i = 0; i < map.entities.size(); i++)
		{
			if(i != pos && me.intersects(map.entities.get(i).collisionbox) && (map.entities.get(i).canCollideWith(this) || canCollideWith(map.entities.get(i))))
			{
				if(map.entities.get(i) instanceof EntityZombie)
				{
					EntityZombie zomb = (EntityZombie)map.entities.get(i);
					zomb.damage(this, damage, map);
				}
				die(map);
				x = oldx;
				break;
			}
		}
		if(x != oldx)
		{
			top: for(int xp = 0; xp < map.w; xp++)
			{
				for(int yp = 0; yp < map.h; yp++)
				{
					Rectangle col = new Rectangle(xp * map.tilew, yp * map.tileh, map.tilew, map.tileh);
					if(me.intersects(col) && map.tiles[xp][yp].collidesWith(this))
					{
						die(map);
						x = oldx;
						break top;
					}
				}
			}
		}
		//Sjekk y
		y = y + (yspeed * delta);
		me = new Rectangle((int)x, (int)y, this.width, this.height);
		for(int i = 0; i < map.entities.size(); i++)
		{	
			if(i != pos && me.intersects(map.entities.get(i).collisionbox) && (map.entities.get(i).canCollideWith(this) || canCollideWith(map.entities.get(i))))
			{
				if(map.entities.get(i) instanceof EntityZombie)
				{
					EntityZombie zomb = (EntityZombie)map.entities.get(i);
					zomb.damage(this, damage, map);
				}
				die(map);
				y = oldy;
				break;
			}
		}
		if(y != oldy)
		{
			top: for(int xp = 0; xp < map.w; xp++)
			{
				for(int yp = 0; yp < map.h; yp++)
				{
					Rectangle col = new Rectangle(xp * map.tilew, yp * map.tileh, map.tilew, map.tileh);
					if(me.intersects(col) && map.tiles[xp][yp].collidesWith(this))
					{
						die(map);
						y = oldy;
						break top;
					}
				}
			}
		}
		collisionbox = new Rectangle((int)x, (int)y, width, height); //Oppdater kollisjonsboksen
		
	}

}
