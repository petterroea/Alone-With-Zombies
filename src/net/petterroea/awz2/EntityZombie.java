package net.petterroea.awz2;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import net.petterroea.awz2.Packet.Packettype;

public class EntityZombie extends Entity {
	long lastDamage = System.currentTimeMillis();
	String ident;
	String target = "";
	public EntityZombie(String ident, int x, int y, int health) {
		super(x, y);
		this.ident = ident;
		this.health = health;
		// TODO Auto-generated constructor stub
	}
	int targetx = 0;
	int targety = 0;
	double rot = 0;
	double health = 0;
	@Override
	public void draw(Graphics g, int xoff, int yoff, Map map, int pos) {
		if(isVisible(xoff, yoff))
		{
			g.drawImage(MediaManager.rotate(MediaManager.zombie.getBufferedImage(), rot), (int)x + xoff - 16, (int)y + yoff - 16, null);
		}
		//MediaManager.font.draw(g, (int)(x + xoff), (int)(y + 64 + yoff), targetx + " " + targety, null);
		
	}
	public void die(Map map)
	{
		removeMe = true;
		map.zombies = map.zombies - 1;
		Random rand = new Random();
		for(int i = 0; i < 50; i++)
		{
			int xpos = rand.nextInt(32) + (int)x;
			int ypos = rand.nextInt(32) + (int)y;
			double xm = (x+16) - xpos;
			double ym = (y+16) - ypos;
			float radians = (float) -Math.atan2(xm, -ym);
			map.entities.add(new EntityBlood(xpos, ypos, (Math.sin(radians)/(30+rand.nextInt(10)))*2, (Math.cos(radians)/(30+rand.nextInt(10))) * 2, (long)(1000 + rand.nextInt(500))));
		}
	}
	@Override
	public boolean canCollideWith(Entity entity) {
		if(entity instanceof EntityPlayer || entity instanceof EntityZombie || entity instanceof EntityBullet)
		{
			return true;
		}
		return false;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "zombie";
	}
	@Override
	public void update(int delta, Map map, int pos) {
		if(health <= 0)
		{
			die(map);
		}
		/*
		 * PATHFINDING SUCKS
		 * 
		 * So i wrote my own crappy shit :P
		 */
		int thisx = (int) (x/map.tilew);
		int thisy = (int) (y/map.tileh);
		if(map.tiles[thisx][thisy] instanceof TileDirt)
		{
			if(targetx == 0 || targety == 0)
			{
				int best = 0;
				int bestLen = 10000;
				for(int i = 0; i < map.doors.size(); i++)
				{
					int prelen = (((int)x - map.doors.get(i).x)*((int)x - map.doors.get(i).x)) + (((int)y - map.doors.get(i).y)*((int)y - map.doors.get(i).y));
					if(prelen < 0)
					{
						prelen = 0-prelen;
					}
					int len = (int)Math.sqrt(prelen);
					//System.out.println(len);
					//System.out.println("(" + (int)x  + " " + (int)y + " " + map.doors.get(i).x + " " + map.doors.get(i).y + ")");
					//System.out.println("Before sqrt: " + (((int)x - map.doors.get(i).x) + ((int)y - map.doors.get(i).y)));
					
					if(len < bestLen)
					{
						bestLen = len;
						best = i;
					}
				}
				//System.out.println("Best was " + best + ", with a length of " + bestLen);
				targetx = map.doors.get(best).x;
				targety = map.doors.get(best).y;
				//System.out.println("Best x: " + targetx + ", y: " + targety);
				//System.out.println("My coordinate: X:" + x + ", Y: " + y);
				//System.out.println("=======================================");
				//System.out.println("=======================================");
				//System.out.println("=======================================");
			}
			if(x < targetx)
			{
				xspeed = 0.04;
			}
			else if(x > targetx)
			{
				xspeed = -0.04;
			}
			else
			{
				xspeed = 0;
			}
			if(y < targety)
			{
				yspeed = 0.04;
			}
			else if(y > targety)
			{
				yspeed = -0.04;
			}
			else
			{
				yspeed = 0;
			}
			rot=calcRot(targetx, targety, x, y);
		}
		else
		{
			if(map instanceof MpMap)
			{
				MpMap themap = (MpMap) map;
				if(themap.server())
				{
					double shortest = 10000.0;
					int shortestindex = 0;
					for(int i = 0; i < themap.entities.size(); i++)
					{
						if(themap.entities.get(i) instanceof EntityPlayer)
						{
							EntityPlayer player = (EntityPlayer) themap.entities.get(i);
							double len = Math.sqrt(((player.x-x)*(player.x-x))+((player.y-y)*(player.y-y)));
							if(len < shortest)
							{
								shortest = len;
								shortestindex = i;
							}
						}
					}
					EntityPlayer player = (EntityPlayer)themap.entities.get(shortestindex);
					if(!target.equals(player.name))
					{
						target = player.name;
						synchronized(themap.master.conn.sync)
						{
							themap.master.conn.out.add(new Packet(target + " " + ident, Packettype.TARGET)); 
						}
					}
				}
				if(target.equals(""))
				{
					xspeed = 0;
					yspeed = 0;
				}
				else
				{
					//System.out.println("Target is " + target);
					EntityPlayer player = themap.getPlayerFromName(target);
					//System.out.println("Real target name is " + player.name);
					if(player == null)
					{
						xspeed = 0;
						yspeed = 0;
					}
					else
					{
						if(x < player.x)
						{
							xspeed = 0.04;
						}
						else if(x > player.x)
						{
							xspeed = -0.04;
						}
						if(y < player.y)
						{
							yspeed = 0.04;
						}
						else if(y > player.y)
						{
							yspeed = -0.04;
						}
						rot=calcRot(player.x, player.y, x, y);
					}
				}
			}
			else
			{
				if(x < map.playerx)
				{
					xspeed = 0.04;
				}
				else if(x > map.playerx)
				{
					xspeed = -0.04;
				}
				if(y < map.playery)
				{
					yspeed = 0.04;
				}
				else if(y > map.playery)
				{
					yspeed = -0.04;
				}
				rot=calcRot(map.playerx, map.playery, x, y);
			}
		}
		//Check doors(And break them^^)
		Rectangle damageMe = new Rectangle((int)x-16, (int)y-16, width+32, height+32);
		for(int i = 0; i < map.doors.size(); i++)
		{
			Rectangle theDoor = new Rectangle(map.doors.get(i).x, map.doors.get(i).y, map.tilew, map.tileh);
			if(damageMe.intersects(theDoor))
			{
				//Try to damage it
				TileDoor door = (TileDoor)map.tiles[map.doors.get(i).x/map.tilew][map.doors.get(i).y/map.tileh];
				door.damageMe(map);
			}
		}
		//I WANNA KEEL EVERY PLAYER IN DAH WORLD!
		for(int i = 0; i < map.entities.size(); i++)
		{
			if(map.entities.get(i) instanceof EntityPlayer)
			{
				EntityPlayer plyr = (EntityPlayer) map.entities.get(i);
				if(damageMe.intersects(plyr.collisionbox))
				{
					if(System.currentTimeMillis() - lastDamage > 2000)
					{
						lastDamage = System.currentTimeMillis();
						plyr.damage(15);
					}
				}
			}
		}
		super.update(delta, map, pos);	
	}
	private double calcRot(double targetx2, double targety2, double xd, double yd) {
		// TODO Auto-generated method stub
		return (double) -Math.atan2(targetx2 - xd, targety2 - yd);
	}
	public void damage(EntityBullet bullet, double damage, Map map) {
		if(map instanceof MpMap)
		{
			MpMap themap = (MpMap) map;
			if(themap.master.server())
			{
				health = health - damage;
				if(health <= 0)
				{
					if(!removeMe)
					{
						if(map instanceof MpMap)
						{
							//Do nothing. I have le custom code to deal with this
							map.fuckThisShitImDead(this, bullet.owner.name);
						}
						else
						{
							//We are dead. Give the responsible player points
							bullet.owner.points = bullet.owner.points + 100;
							//map.notifyCriticalChange(bullet.owner);
						}
					}
					die(map);
				}
			}
		}
		else
		{
			if(health <= 0)
			{
				if(!removeMe)
				{
					if(map instanceof MpMap)
					{
						//Do nothing. I have le custom code to deal with this
						map.fuckThisShitImDead(this, bullet.owner.name);
					}
					else
					{
						//We are dead. Give the responsible player points
						bullet.owner.points = bullet.owner.points + 100;
						//map.notifyCriticalChange(bullet.owner);
					}
				}
				die(map);
			}
			else
			{
				health = health - damage;
			}
		}
	}

}
