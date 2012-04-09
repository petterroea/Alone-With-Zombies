/*
 * LevelReader.java
 * 
 * Part of the GameSDK starterkit made for the seminar at "The Gathering 2012 - At the end of the universe"
 * 
 * This source code is provided AS-IS and without any warranty. Use at own risk.
 * You can use and modify this source any way you want, but please send me an E-mail to petterroea@skymiastudios.com
 * if you make a game from it - I would like to keep a list of all games, as i am curious. You may also use this for games
 * that cost money. So it is basically public domain, except that i own the rights to it, but give you the rights to use
 * it for the purposes above. You cant sell this kit. You can sell a game based on it, but not the source code to this kit.
 * Also, this big comment must stay in all code files that originated from the starterkit, no matter how much they are
 * modified.
 * 
 * If you want support for this SDK, feel free to contact me. If you are at "The Gathering 2012", you can
 * look for me in the creative lounge(Where i sit). If not, feel free to send me an E-Mail to the above
 * adress. If you need normal java help, ask a mentor.
 * 
 */
package net.petterroea.awz2;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * This class is used to decide the tile depending on the color in input
 * @author petterroea
 *
 */
public class LevelReader {
	/**
	 * Modify this
	 * @param rgb the color
	 * @return The tile that is going to be used at the current position in the map
	 * @throws Exception  What happens if there is no tile with that color
	 */
	public static Tile getTile(int rgb, int gridx, int gridy, LinkedList<Entity> entities, int w, int h, Map map)
	{
		int val = rgb & 0xFFFFFF;
		if(val == 0x000000) //Hvis fargen er sort
		{
			return new TileWall(gridx, gridy);	
		}
		else if(val == 0xFFFFFF)
		{
			return new TileDirt(gridx, gridy);
		}
		else if(val == 0xFFD800)
		{
			return new TileConcrete(gridx, gridy);
		}
		else if(val == 0xFF0000)
		{
			map.spawnx = w*gridx;
			map.spawny = h*gridy;
			entities.add(new EntityPlayer(gridx*w, gridy*h));
			return new TileConcrete(gridx, gridy);
		}
		else if(val == 0x00FF00)
		{
			map.spawners.add(new ColPos(gridx*w, gridy*h));
			return new TileDirt(gridx, gridy);
		}
		else if(val == 0xA0A0A0)
		{
			map.doors.add(new ColPos(gridx*w, gridy*h));
			return new TileDoor(gridx, gridy);
		}
		else if(val == 0xFF00DC)
		{
			map.vendors.add(new ColPos(gridx*w, gridy*h));
			return new TileGunVendor(gridx, gridy);
		}
		else if(val == 0xB200FF)
		{
			map.vendors.add(new ColPos(gridx*w, gridy*h));
			return new TileMgVendor(gridx, gridy);
		}
		else if(val == 0x00FFFF)
		{
			map.vendors.add(new ColPos(gridx*w, gridy*h));
			return new TileSgVendor(gridx, gridy);
		}
		else if(val == 0xFF6A00)
		{
			map.vendors.add(new ColPos(gridx*w, gridy*h));
			return new TileFlameVendor(gridx, gridy);
		}
		else if(val == 0x0026FF)
		{
			map.vendors.add(new ColPos(gridx*w, gridy*h));
			return new TileRandomVendor(gridx, gridy);
		}
		else if(val == 0x0094FF)
		{
			map.locked.add(new ColPos(gridx*w, gridy*h));
			return new TileLockedDoor(gridx, gridy);
		}
		else if(val == 0x7F3300)
		{
			return new TileWood(gridx, gridy);
		}
		else
		{
			return new Tile(gridx, gridy);
		}
	}

}
