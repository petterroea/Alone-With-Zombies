/*
 * Map.java
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
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Random;

import net.petterroea.awz2.Packet.Packettype;
/**
 * This class is used to contain a array of Tiles
 * @author petterroea
 *
 */
public class Map {
	String name = "unnamed";
	int playerhealth = 100;
	int playerPoints = 0;
	Weapon[] weapons;
	public String meName = "";
	int currentWeapon = 0;
	double playerRot = 0;
	int allZombies = 0;
	int playerindex = 0;
	long gamestart = 0;
	String msg = "";
	long lastTime = 0;
	static boolean pause = true;
	long pausedTime = 0;
	long pauseStart = 0;
	LinkedList<ColPos> vendors = new LinkedList<ColPos>();
	LinkedList<ColPos> locked = new LinkedList<ColPos>();
	/**
	 * The entities
	 */
	int spawnx, spawny;
	public LinkedList<Entity> entities;
	BufferedImage src;
	/**
	 * The array of tiles
	 */
	Tile[][] tiles;
	int zombies = 0;
	/**
	 * Width or Height of the tile array
	 */
	public int playerx, playery;
	LinkedList<ColPos> spawners = new LinkedList<ColPos>();
	LinkedList<ColPos> doors = new LinkedList<ColPos>();
	public int w, h;
	/**
	 * Controls the offset of the map drawn
	 */
	public int xoff, yoff;
	/**
	 * Width and height of a tile
	 */
	public int tilew, tileh;
	/**
	 * Constructs a map from a image containing level data
	 * @param src The image with the level data
	 * @param tilew Width of one tile
	 * @param tileh Height of one tile
	 */
	public Map(String name, BufferedImage src, int tilew, int tileh)
	{
		if(src == null)
		{
			System.out.println("SRC IS NULL");
		}
		if(name == "")
		{
			System.out.println("SRC IS NULL");
		}
		if(tilew != 32)
		{
			System.out.println("SRC IS NULL");
		}
		if(tileh != 32)
		{
			System.out.println("SRC IS NULL");
		}
		this.src = src;
		weapons = new Weapon[2];
		weapons[0] = Weapon.getWeapon("gun");
		entities = new LinkedList<Entity>();
		this.name = name;
		this.w = src.getWidth();
		this.h = src.getHeight();
		this.tileh = tileh;
		this.tilew = tilew;
		tiles = new Tile[w][h];
		for(int x = 0; x < w; x++)
		{
			for(int y = 0; y < h; y++)
			{
				tiles[x][y] = LevelReader.getTile(src.getRGB(x, y), x, y, entities, tilew, tileh, this);
				tiles[x][y].gridx = x;
				tiles[x][y].gridy = y;
			}
		}
		for(int i = 0; i < entities.size(); i++)
		{
			if(entities.get(i) instanceof EntityPlayer)
			{
				playerindex = i;
				xoff =  0-((int)entities.get(i).x - (Game.WIDTH / 2));
				yoff = 0-((int)entities.get(i).y - (Game.HEIGHT / 2));
				spawnx = (int)entities.get(i).x;
				spawnx = (int)entities.get(i).y;
			}
		}
	}
	/**
	 * Returns the tile at the specific coordinates
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return The tile at the x and y coordinate
	 */
	public Tile getTileAt(int x, int y)
	{
		return tiles[x][y];
	}
	long lastZombieSpawn = System.currentTimeMillis();
	/**
	 * Updates ALL the entities! (Position)
	 * @param delta Time since last update in millisessions
	 */
	boolean updatedNotPause = false;
	boolean updatedPause = false;
	public void updateEntities(int delta)
	{
		
		if(Input.leftMouseBtn)
		{
			fire();
		}
		msg = "";
		if(gamestart == 0)
		{
			gamestart = System.currentTimeMillis();
		}
		if(Input.keys[Options.RELOAD])
		{
			weapons[currentWeapon].reload();
		}
		if(Input.keys[Options.WEAPON_1])
		{
			currentWeapon = 0;
		}
		if(Input.keys[Options.WEAPON_2] && weapons[1] != null)
		{
			currentWeapon = 1;
		}
		weapons[currentWeapon].tick();
		if(System.currentTimeMillis() - lastZombieSpawn > 6000 && zombies < 20)
		{
			lastZombieSpawn = System.currentTimeMillis();
			Random rand = new Random();
			int index = rand.nextInt(spawners.size());
			addEntity(new EntityZombie(getZombieID(), spawners.get(index).x, spawners.get(index).y, 1 + (allZombies/5)));
			zombies++;
			allZombies++;
			//System.out.println("Spawned a zombie");
		}
		for(int i = 0; i < entities.size(); i++)
		{
			entities.get(i).update(delta, this, i);
		}
		int last = 0;
		boolean done = false;
		while(!done)
		{
			for(int i = last; i < entities.size(); i++)
			{
				if(entities.get(i).removeMe)
				{
					if(i==0)
					{
						last=0;
					}
					else
					{
						last=i-1;
					}
					entities.remove(i);
					break;
				}
				if(i == entities.size() -1)
				{
					done = true;
				}
			}
			if(entities.size() == 0)
			{
				done = true;
			}
		}
	}
	public void fire() {
		weapons[currentWeapon].fire(this, playerx + 16, playery + 16, playerRot, (EntityPlayer)entities.get(playerindex));
		
	}
	public void fuckThisShitImDead(EntityZombie zomb, String nameOfKiller)
	{
		//Do nothing. This is SP. Remember?
	}
	public String getZombieID()
	{
		return ""+Math.random();
	}
	public EntityPlayer getPlayerFromName(String name)
	{
		for(int i = 0; i < entities.size(); i++)
		{
			if(entities.get(i) instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entities.get(i);
				if(player.name.equals(name))
				{
					return (EntityPlayer) entities.get(i);
				}
			}
		}
		System.out.println("INVALID NAME: " + name);
		return null;
	}
	/**
	 * Draws the map to the screen
	 * @param g The graphics object
	 */
	public void draw(Graphics g)
	{
		//Modify this if you want to use a different drawing method
		for(int x = (-xoff/tilew); x < (-xoff/tilew)+(Game.WIDTH/tilew); x++)
		{
			for(int y = (-yoff/tileh); y < (-yoff/tileh)+(Game.HEIGHT/tileh); y++)
			{
				if(tiles[x][y] != null && tiles[x][y].isVisible(xoff, yoff, tilew, tileh))
				{
					g.drawImage(tiles[x][y].getTile(this), (x * tilew) + xoff, (y * tileh) + yoff, null);
				}
			}
		}
		for(int i = 0; i < entities.size(); i++)
		{
			entities.get(i).draw(g, xoff, yoff, this, i);
		}
	}
	public boolean isMplayer()
	{
		return false;
	}
	public void iMoved(EntityPlayer player)
	{
		//Do nothing
	}
	public String getWeaponString()
	{
		String temp = "";
		if(currentWeapon == 0)
		{
			if(weapons[1] != null)
			{
				temp = temp + "(" + weapons[0].name + ") " + weapons[1].name;
			}
			else
			{
				temp = temp + "(" + weapons[0].name + ") NOTHING";
			}
		}
		else if(currentWeapon == 1)
		{
			if(weapons[0] != null)
			{
				temp = temp + " " + weapons[0].name + " (" + weapons[1].name + ")";
			}
			else
			{
				temp = temp + " NOTHING " + "(" + weapons[1].name + ")";
			}
		}
		return temp;
	}
	public void drawGui(Graphics g)
	{
		MediaManager.font.draw(g, 0, (int)(Game.HEIGHT - MediaManager.font.getCharHeight()*2.5), "Weapons: " + getWeaponString(), null);
		long sinceStart = (System.currentTimeMillis() - gamestart);
		int min = (int)(sinceStart/60000);
		int sec = (int)((sinceStart-(min*60000))/1000);
		int ms = (int)(sinceStart-(min*60000)-(sec*1000));
		MediaManager.font.draw(g, 0, (int)(Game.HEIGHT - MediaManager.font.getCharHeight()*4), min + ":" + sec + ":" + ms + " ", null);
		MediaManager.font.draw(g, 170, (int)(Game.HEIGHT - MediaManager.font.getCharHeight()*4), msg, null);
		MediaManager.font.draw(g, 0, Game.HEIGHT-MediaManager.font.getCharHeight(), "AMMO: " + weapons[currentWeapon].clip + "/" + weapons[currentWeapon].ammo + " " + weapons[currentWeapon].getStatus() + " POINTS: " + playerPoints, null);
		MediaManager.font.draw(g, Game.WIDTH - MediaManager.font.getCharWidth()*3, Game.HEIGHT-MediaManager.font.getCharHeight(), "" + playerhealth, null);
	}
	public int addEntity(Entity ent) {
		entities.add(ent);
		return 0;
		
	}
}
