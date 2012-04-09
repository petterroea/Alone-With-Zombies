package net.petterroea.awz2;

import java.awt.image.BufferedImage;

public class TileWall extends Tile {

	public TileWall(int gridx, int gridy) {
		super(gridx, gridy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean collidesWith(Entity ent)
	{
		return true;
	}
	@Override
	public BufferedImage getTile(Map map)
	{
		if((map.getTileAt(gridx - 1, gridy) instanceof TileWall || map.getTileAt(gridx + 1, gridy) instanceof TileWall) && !(map.getTileAt(gridx, gridy + 1) instanceof TileWall) && !(map.getTileAt(gridx, gridy - 1) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(2, 2); //Horisontal
		}
		if((map.getTileAt(gridx, gridy - 1) instanceof TileWall || map.getTileAt(gridx, gridy + 1) instanceof TileWall) && !(map.getTileAt(gridx + 1, gridy) instanceof TileWall) && !(map.getTileAt(gridx - 1, gridy) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(3, 2); //Vertikal
		}
		if(map.getTileAt(gridx, gridy + 1) instanceof TileWall && map.getTileAt(gridx, gridy - 1) instanceof TileWall && map.getTileAt(gridx + 1, gridy) instanceof TileWall && map.getTileAt(gridx - 1, gridy) instanceof TileWall)
		{
			return MediaManager.tiles.getBufferedImage(4, 3); //Kryss
		}
		//Kanter
		if((map.getTileAt(gridx, gridy + 1) instanceof TileWall) && !(map.getTileAt(gridx, gridy - 1) instanceof TileWall) && (map.getTileAt(gridx + 1, gridy) instanceof TileWall) && !(map.getTileAt(gridx - 1, gridy) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(3, 3); //Høyre-ned
		}
		if(!(map.getTileAt(gridx, gridy + 1) instanceof TileWall) && (map.getTileAt(gridx, gridy - 1) instanceof TileWall) && (map.getTileAt(gridx + 1, gridy) instanceof TileWall) && !(map.getTileAt(gridx - 1, gridy) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(3, 1); //Høyre-opp
		}
		if(!(map.getTileAt(gridx, gridy + 1) instanceof TileWall) && (map.getTileAt(gridx, gridy - 1) instanceof TileWall) && !(map.getTileAt(gridx + 1, gridy) instanceof TileWall) && (map.getTileAt(gridx - 1, gridy) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(2, 1); //Venstre-opp
		}
		if((map.getTileAt(gridx, gridy + 1) instanceof TileWall) && !(map.getTileAt(gridx, gridy - 1) instanceof TileWall) && !(map.getTileAt(gridx + 1, gridy) instanceof TileWall) && (map.getTileAt(gridx - 1, gridy) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(2, 3); //Venstre-ned
		}
		//Sånne rare greier med tre hull(WTF!?!)
		if(!(map.getTileAt(gridx, gridy + 1) instanceof TileWall) && (map.getTileAt(gridx, gridy - 1) instanceof TileWall) && (map.getTileAt(gridx + 1, gridy) instanceof TileWall) && (map.getTileAt(gridx - 1, gridy) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(1, 2); //Venstre-opp-høyre
		}
		if((map.getTileAt(gridx, gridy + 1) instanceof TileWall) && !(map.getTileAt(gridx, gridy - 1) instanceof TileWall) && (map.getTileAt(gridx + 1, gridy) instanceof TileWall) && (map.getTileAt(gridx - 1, gridy) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(1, 3); //Venstre-ned-høyre
		}
		if((map.getTileAt(gridx, gridy + 1) instanceof TileWall) && (map.getTileAt(gridx, gridy - 1) instanceof TileWall) && !(map.getTileAt(gridx + 1, gridy) instanceof TileWall) && (map.getTileAt(gridx - 1, gridy) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(0, 1); //Opp-venstre-ned
		}
		if((map.getTileAt(gridx, gridy + 1) instanceof TileWall) && (map.getTileAt(gridx, gridy - 1) instanceof TileWall) && (map.getTileAt(gridx + 1, gridy) instanceof TileWall) && !(map.getTileAt(gridx - 1, gridy) instanceof TileWall))
		{
			return MediaManager.tiles.getBufferedImage(1, 1); //Opp-høyre-ned
		}
		return MediaManager.tiles.getBufferedImage(4, 2);
	}
}
