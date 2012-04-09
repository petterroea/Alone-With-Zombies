package net.petterroea.awz2;

import java.awt.Graphics;
import java.awt.Rectangle;

public class EntityBlood extends Entity{
	public EntityBlood(int x, int y, double xspeed, double yspeed, long lifetime) {
		super(x, y);
		this.xspeed = xspeed;
		this.yspeed = yspeed;
		started = System.currentTimeMillis();
		this.lifetime = lifetime;
		width = MediaManager.bloodParticle.getBufferedImage().getWidth();
		height = MediaManager.bloodParticle.getBufferedImage().getHeight();
		// TODO Auto-generated constructor stub
	}
	long started = System.currentTimeMillis();
	long lifetime = 0;
	@Override
	public void draw(Graphics g, int xoff, int yoff, Map map, int pos) {
		if(isVisible(xoff, yoff))
		{
			g.drawImage(MediaManager.bloodParticle.getBufferedImage(), (int)x + xoff, (int)y + yoff, null);
		}
		//MediaManager.font.draw(g, (int)(x + xoff), (int)(y + 64 + yoff), targetx + " " + targety, null);
		
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "blood";
	}
	@Override
	public void update(int delta, Map map, int pos) {
		if(System.currentTimeMillis() - started > lifetime)
		{
			removeMe = true;
		}
		double oldx = x;
		double oldy = y;
		if(xspeed > 0)
		{
			xspeed = xspeed - (0.00003*delta);
		}
		else if(xspeed < 0)
		{
			xspeed = xspeed + (0.00003*delta);
		}
		if(yspeed > 0)
		{
			yspeed = yspeed - (0.00003*delta);
		}
		else if(yspeed < 0)
		{
			yspeed = yspeed + (0.00003*delta);
		}
		x = x + (xspeed * delta);
		Rectangle me = new Rectangle((int)x, (int)y, width, height);
		//Sjekk x
		for(int i = 0; i < map.entities.size(); i++)
		{
			if(i != pos && me.intersects(map.entities.get(i).collisionbox) && (map.entities.get(i).canCollideWith(this) || canCollideWith(map.entities.get(i))))
			{
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
						y = oldy;
						break top;
					}
				}
			}
		}
		collisionbox = new Rectangle((int)x, (int)y, width, height); //Oppdater kollisjonsboksen
		
	}
}
