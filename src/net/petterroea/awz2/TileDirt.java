package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileDirt extends Tile{

	public TileDirt(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public BufferedImage getTile(Map map)
	{
		return MediaManager.tiles.getBufferedImage(1, 0);
	}
}
