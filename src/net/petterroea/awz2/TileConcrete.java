package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileConcrete extends Tile {

	public TileConcrete(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	public BufferedImage getTile(Map map)
	{
		return MediaManager.tiles.getBufferedImage(2, 0);
	}

}
