package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileWood extends Tile{

	public TileWood(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public BufferedImage getTile(Map map)
	{
		return MediaManager.tiles.getBufferedImage(0, 2);
	}
	@Override
	public boolean collidesWith(Entity ent)
	{
		return false;
	}
}
