package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileLockedDoor extends Tile{
	boolean cleared = false;
	public TileLockedDoor(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String getUseText(EntityPlayer entityPlayer)
	{
		if(cleared)
		{
			return "";
		}
		if(entityPlayer.points > 1000)
		{
			return "Clear debris for 1000 points";
		}
		return "You cannot afford it(1000)";
	}
	@Override
	public BufferedImage getTile(Map map)
	{
		if(cleared)
		{
			return MediaManager.tiles.getBufferedImage(3, 0);
		}
		return MediaManager.tiles.getBufferedImage(8, 0);
	}
	@Override
	public boolean use(Map map, EntityPlayer ent)
	{
		if(ent.points > 1000 && !cleared)
		{
			ent.points = ent.points - 1000;
			cleared = true;
		}
		return true;
	}
	@Override
	public boolean collidesWith(Entity ent)
	{
		if(ent instanceof EntityPlayer)
		{
			return !cleared;
		}
		return false;
	}
}
