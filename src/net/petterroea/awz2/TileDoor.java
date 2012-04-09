package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileDoor extends Tile{
	int lives = 4;
	int maxlives = 4;
	long lastBreak = System.currentTimeMillis();
	long lastHeal = System.currentTimeMillis();
	public TileDoor(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public BufferedImage getTile(Map map)
	{
		return MediaManager.tiles.getBufferedImage(3 + lives, 0);
	}
	public void damageMe(Map map)
	{
		if(map instanceof MpMap)
		{
			MpMap themap = (MpMap) map;
			if(themap.server())
			{
				if(System.currentTimeMillis() - lastBreak > 4000 && lives > 0)
				{
					lives = lives - 1;
					lastBreak = System.currentTimeMillis();
					themap.handleDamage(gridx, gridy, lives);
				}
			}
		}
		else
		{
			if(System.currentTimeMillis() - lastBreak > 4000 && lives > 0)
			{
				lives = lives - 1;
				lastBreak = System.currentTimeMillis();
			}
		}
	}
	@Override
	public boolean collidesWith(Entity ent)
	{
		if(ent instanceof EntityPlayer || ent instanceof EntityBullet)
		{
			return true;
		}
		if(lives == 0)
		{
			return false;
		}
		return true;
	}
	public void healMe(EntityPlayer player, Map map) {
		if(System.currentTimeMillis() - lastHeal > 3000 && lives < maxlives)
		{
			lives = lives + 1;
			lastHeal = System.currentTimeMillis();
			player.points = player.points + 20;
			if(map instanceof MpMap)
			{
				MpMap themap = (MpMap) map;
				themap.handleHeal(gridx, gridy, lives);
			}
		}
		
	}
}
