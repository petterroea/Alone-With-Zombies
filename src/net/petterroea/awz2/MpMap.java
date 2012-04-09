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

import java.util.Random;

import net.petterroea.awz2.Packet.Packettype;

/**
 * This class is used to contain a array of Tiles
 * @author petterroea
 *
 */
public class MpMap extends Map{
	@Override
	public void fire() {
		if(weapons[currentWeapon].fire(this, playerx + 16, playery + 16, playerRot, (EntityPlayer)entities.get(playerindex)))
		{
			if(master.server())
			{
				synchronized(master.conn.sync)
				{
					master.conn.out.add(new Packet(meName + " " + playerRot + " " + weapons[currentWeapon].getName(), Packettype.FIRE));
				}
			}
			else
			{
				synchronized(master.client.synch){
					master.client.out.add(new Packet(meName + " " + playerRot + " " + weapons[currentWeapon].getName(), Packettype.FIRE));
				}
			}
		}
		
	}
	@Override
	public boolean isMplayer()
	{
		return true;
	}
	@Override
	public void fuckThisShitImDead(EntityZombie zomb, String nameOfKiller)
	{
		if(master.server())
		{
			if(nameOfKiller.equals(meName))
			{
				for(int i = 0; i < entities.size(); i++)
				{
					if(entities.get(i) instanceof EntityPlayer)
					{
						EntityPlayer player = (EntityPlayer) entities.get(i);
						if(player.name.equals(meName))
						{
							player.points = player.points + 100;
							entities.set(i, player);
							break;
						}
					}
				}
			}
			synchronized(master.conn.sync)
			{
				master.conn.out.add(new Packet(zomb.ident + " " + nameOfKiller, Packettype.DEADZOMBIE));
			}
		}
		else
		{
			//I dont care. The host is the master
		}
	}
	@Override
	public void iMoved(EntityPlayer player)
	{
		if(master.server())
		{
			master.conn.out.add(new Packet(player.name + " " + player.xspeed + " " + player.yspeed + " " + player.x + " " + player.y, Packettype.MOVEMENT));
		}
		else
		{
			synchronized(master.client.synch)
			{
				master.client.out.add(new Packet(player.name + " " + player.xspeed + " " + player.yspeed + " " + player.x + " " + player.y, Packettype.MOVEMENT));
			}
		}
	}
	public void checkPlayerIndex()
	{
		if(entities.size() == 0)
		{
			return;
		}
		if(entities.size() < playerindex || !(entities.get(playerindex) instanceof EntityPlayer))
		{
			for(int i = 0; i < entities.size(); i++)
			{
				if(entities.get(i) instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer)entities.get(i);
					if(player.name.equals(meName))
					{
						playerindex = i;
						break;
					}
				}
			}
		}
		else
		{
			EntityPlayer player = (EntityPlayer)entities.get(playerindex);
			if(!player.name.equals(meName))
			{
				for(int i = 0; i < entities.size(); i++)
				{
					if(entities.get(i) instanceof EntityPlayer)
					{
						player = (EntityPlayer)entities.get(i);
						if(player.name.equals(meName))
						{
							playerindex = i;
							break;
						}
					}
				}
			}
		}
	}
	MpGameScreen master;
	public MpMap(Map map, MpGameScreen master) {
		super(map.name, map.src, map.tilew, map.tileh);
		this.master = master;
		for(int i = 0; i < entities.size(); i++)
		{
			if(entities.get(i) instanceof EntityPlayer)
			{
				spawnx = (int)entities.get(i).x;
				spawny = (int)entities.get(i).y;
			}
		}
		entities.clear();
		// TODO Auto-generated constructor stub
	}
	public void checkPackets()
	{
		if(master.server())
		{
			//Check for serverside packets
			synchronized(master.conn.sync)
			{
				for(int i = 0; i < master.conn.in.size(); i++)
				{
					if(master.conn.in.get(i).type == Packettype.HANDSHAKE)
					{
						System.out.println(master.conn.in.get(i).senderId);
						master.conn.clients.set(master.conn.in.get(i).senderId, handShake(master.conn.clients.get(master.conn.in.get(i).senderId), master.conn.in.get(i).getEntirePacket()[0]));
					}
					else if(master.conn.in.get(i).type == Packettype.MOVEMENT)
					{
						System.out.println("Searching for " + master.conn.in.get(i).getEntirePacket()[0] + ".");
						for(int a = 0; a < entities.size(); a++)
						{
							if(entities.get(a) instanceof EntityPlayer)
							{
								EntityPlayer player = (EntityPlayer) entities.get(a);
								System.out.println("Checking if " + player.name + " is " + master.conn.in.get(i).getEntirePacket()[0].substring(0, master.conn.in.get(i).getEntirePacket()[0].length()));
								if(player.name.equals(master.conn.in.get(i).getEntirePacket()[0].substring(0, master.conn.in.get(i).getEntirePacket()[0].length())))
								{
									player.xspeed = Double.valueOf(master.conn.in.get(i).getEntirePacket()[1]);
									player.yspeed = Double.valueOf(master.conn.in.get(i).getEntirePacket()[2]);
									player.x = Double.valueOf(master.conn.in.get(i).getEntirePacket()[3]);
									player.y = Double.valueOf(master.conn.in.get(i).getEntirePacket()[4]);
									master.conn.out.add(new Packet(master.conn.in.get(i).getEntirePacket()[0] + " " + master.conn.in.get(i).getEntirePacket()[1] + " " + master.conn.in.get(i).getEntirePacket()[2] + " " + master.conn.in.get(i).getEntirePacket()[3] + " " + master.conn.in.get(i).getEntirePacket()[4], Packettype.MOVEMENT));
									entities.set(a, player);
									//System.out.println("ACTUALLY MOVED THE PLAYER");
									break;
								}
							}
						}
						
					}
					else if(master.conn.in.get(i).type == Packettype.FIRE)
					{
						EntityPlayer player = getPlayerFromName(master.conn.in.get(i).getEntirePacket()[0]);
						System.out.println("Getting weapon -" + master.conn.in.get(i).getEntirePacket()[2].substring(0, master.conn.in.get(i).getEntirePacket()[2].length()-1) + "-");
						Weapon.getWeapon(master.conn.in.get(i).getEntirePacket()[2].substring(0, master.conn.in.get(i).getEntirePacket()[2].length())).fire(this, (int)player.x, (int)player.y, Double.valueOf(master.conn.in.get(i).getEntirePacket()[1]), player);
					}
					else if(master.conn.in.get(i).type == Packettype.HEAL)
					{
						int gx = Integer.parseInt(master.conn.in.get(i).getEntirePacket()[0]);
						int gy = Integer.parseInt(master.conn.in.get(i).getEntirePacket()[1]);
						TileDoor door = new TileDoor(gx, gy);
						door.lives = Integer.parseInt(master.conn.in.get(i).getEntirePacket()[2]);
						tiles[gx][gy] = door;
					}
				}
				master.conn.in.clear();
			}
		}
		else
		{
			synchronized(master.client.synch)
			{
				for(int i = 0; i < master.client.in.size(); i++)
				{
					if(master.client.in.get(i).type == Packettype.TIME)
					{
						String toLong = master.client.in.get(i).getEntirePacket()[1].substring(0, master.client.in.get(i).getEntirePacket()[1].length()-1);
						System.out.println("Time should be " + toLong + ".");
						
						gamestart = Long.parseLong(toLong);
					}
					else if(master.client.in.get(i).type == Packettype.PLAYER)
					{
						boolean allreadyExists = false;
						int indexItIsAt = 0;
						for(int a = 0; a < entities.size(); a++)
						{
							if(entities.get(a) instanceof EntityPlayer)
							{
								EntityPlayer player = (EntityPlayer)entities.get(a);
								if(player.name.equals(master.client.in.get(i).getEntirePacket()[4]))
								{
									//Do nothing
								}
								else
								{
									allreadyExists = true;
									indexItIsAt = a;
								}
							}
						}
						if(!allreadyExists)
						{
							//Process the player
							EntityPlayer player = new EntityPlayer((int)Double.parseDouble(master.client.in.get(i).getEntirePacket()[1]), (int)Double.parseDouble(master.client.in.get(i).getEntirePacket()[2]));
							player.name = master.client.in.get(i).getEntirePacket()[4];
							entities.add(player);
						}
						else
						{
							EntityPlayer player = new EntityPlayer((int)Double.parseDouble(master.client.in.get(i).getEntirePacket()[1]), (int)Double.parseDouble(master.client.in.get(i).getEntirePacket()[2]));
							player.name = master.client.in.get(i).getEntirePacket()[4];
							player.xspeed = Double.valueOf(master.client.in.get(i).getEntirePacket()[5]);
							player.yspeed = Double.valueOf(master.client.in.get(i).getEntirePacket()[6]);
							entities.set(indexItIsAt, player);
						}
					}
					else if(master.client.in.get(i).type == Packettype.ZOMBIE)
					{
						//Process the player
						EntityZombie player = new EntityZombie(master.client.in.get(i).getEntirePacket()[1], (int)Double.parseDouble(master.client.in.get(i).getEntirePacket()[2]), (int)Double.parseDouble(master.client.in.get(i).getEntirePacket()[3]), (int)Double.parseDouble(master.client.in.get(i).getEntirePacket()[4]));
						entities.add(player);
					}
					else if(master.client.in.get(i).type == Packettype.MOVEMENT && !(meName.equals(master.client.in.get(i).getEntirePacket()[1])))
					{
						boolean playerExists = false;
						for(int a = 0; a < entities.size(); a++)
						{
							if(entities.get(a) instanceof EntityPlayer )
							{
								System.out.println("Searching for " + master.client.in.get(i).getEntirePacket()[1] + ".");
								EntityPlayer player = (EntityPlayer) entities.get(a);
								if(player.name.equals(master.client.in.get(i).getEntirePacket()[1]))
								{
									playerExists = true;
									player.xspeed = Double.valueOf(master.client.in.get(i).getEntirePacket()[2]);
									player.yspeed = Double.valueOf(master.client.in.get(i).getEntirePacket()[3]);
									player.x = Double.valueOf(master.client.in.get(i).getEntirePacket()[4]);
									player.y = Double.valueOf(master.client.in.get(i).getEntirePacket()[5]);
									entities.set(a, player);
								}
							}
						}
						if(!playerExists)
						{
							EntityPlayer player = new EntityPlayer(Double.valueOf(master.client.in.get(i).getEntirePacket()[4]), Double.valueOf(master.client.in.get(i).getEntirePacket()[5]));
							player.xspeed = Double.valueOf(master.client.in.get(i).getEntirePacket()[2]);
							player.yspeed = Double.valueOf(master.client.in.get(i).getEntirePacket()[3]);
							player.name = master.client.in.get(i).getEntirePacket()[1];
							entities.add(player);
						}
					}
					else if(master.client.in.get(i).type == Packettype.DEADZOMBIE)
					{
						System.out.println("Processing a dead zombie. Wait, what?");
						for(int a = 0; a < entities.size(); a++)
						{
							if(entities.get(a) instanceof EntityZombie)
							{
								System.out.println("A entity here is a zombie");
								EntityZombie zomb = (EntityZombie) entities.get(a);
								System.out.println("Checking if -" + zomb.ident + "- is -" + master.client.in.get(i).getEntirePacket()[1] + "-");
								if(zomb.ident.equals(master.client.in.get(i).getEntirePacket()[1]))
								{
									System.out.println("Yup. The zombie exists");
									addBlood(zomb.x, zomb.y);
									entities.remove(a);
									System.out.println("Checking if " + meName + " is " + master.client.in.get(i).getEntirePacket()[2] + ".");
									if(master.client.in.get(i).getEntirePacket()[2].substring(0, master.client.in.get(i).getEntirePacket()[2].length()-1).equals(meName))
									{
										System.out.println("I killed it^^");
										for(int p = 0; p < entities.size(); p++)
										{
											if(entities.get(p) instanceof EntityPlayer)
											{
												EntityPlayer player = (EntityPlayer) entities.get(p);
												if(player.name.equals(master.client.in.get(i).getEntirePacket()[2].substring(0, master.client.in.get(i).getEntirePacket()[2].length()-1)))
												{
													player.points = player.points + 100;
												}
												entities.set(p, player);
												break;
											}
										}
										break;
									}
								}
								
							}
						}
					}
					else if(master.client.in.get(i).type == Packettype.TARGET)
					{
						System.out.println("Target set");
						for(int a = 0; a < entities.size(); a++)
						{
							if(entities.get(a) instanceof EntityZombie)
							{
								EntityZombie zomb = (EntityZombie)entities.get(a);
								System.out.println("We have a zombie with ident " + zomb.ident + ".");
								System.out.println("We want " + master.client.in.get(i).getEntirePacket()[2].substring(0, master.client.in.get(i).getEntirePacket()[2].length()-1) + ".");
								if(zomb.ident.equals(master.client.in.get(i).getEntirePacket()[2].substring(0, master.client.in.get(i).getEntirePacket()[2].length()-1)))
								{
									System.out.println("And we found the right zombie. The name of the target is " + master.client.in.get(i).getEntirePacket()[1] + ".");
									zomb.target = master.client.in.get(i).getEntirePacket()[1];
									entities.set(a, zomb);
									break;
								}
							}
						}
					}
					else if(master.client.in.get(i).type == Packettype.FIRE)
					{
						if(!meName.equals(master.client.in.get(i).getEntirePacket()[1]))
						{
							EntityPlayer player = getPlayerFromName(master.client.in.get(i).getEntirePacket()[1]);
							System.out.println("Getting weapon -" + master.client.in.get(i).getEntirePacket()[3].substring(0, master.client.in.get(i).getEntirePacket()[3].length()-1) + "-");
							Weapon.getWeapon(master.client.in.get(i).getEntirePacket()[3].substring(0, master.client.in.get(i).getEntirePacket()[3].length()-1)).fire(this, (int)player.x, (int)player.y, Double.valueOf(master.client.in.get(i).getEntirePacket()[2]), player);
						}
					}
					else if(master.client.in.get(i).type == Packettype.HEAL)
					{
						int gx = Integer.parseInt(master.client.in.get(i).getEntirePacket()[1]);
						int gy = Integer.parseInt(master.client.in.get(i).getEntirePacket()[2]);
						TileDoor door = new TileDoor(gx, gy);
						door.lives = Integer.parseInt(master.client.in.get(i).getEntirePacket()[3].substring(0, master.client.in.get(i).getEntirePacket()[3].length()-1));
						tiles[gx][gy] = door;
					}
					else if(master.client.in.get(i).type == Packettype.DAMAGE)
					{
						int gx = Integer.parseInt(master.client.in.get(i).getEntirePacket()[1]);
						int gy = Integer.parseInt(master.client.in.get(i).getEntirePacket()[2]);
						TileDoor door = new TileDoor(gx, gy);
						door.lives = Integer.parseInt(master.client.in.get(i).getEntirePacket()[3].substring(0, master.client.in.get(i).getEntirePacket()[3].length()-1));
						tiles[gx][gy] = door;
					}
				}
				master.client.in.clear();
			}
		}
	}
	public int connectPlayer(String name2) {
		if(master.server())
		{
			EntityPlayer ent = new EntityPlayer(spawnx, spawny);
			ent.name = name2.substring(0, name2.length());
			addEntity(ent);
		}
		return (entities.size() -1);
	}
	public ServerClient handShake(ServerClient obj, String name2)
	{
		obj.handShake = true;
		obj.name = name;
		connectPlayer(name2);
		obj.out.add(new Packet(String.valueOf(entities.size()-1) + " " + name, Packettype.HANDSHAKEBACK));
		obj.out.add(new Packet(String.valueOf(gamestart), Packettype.TIME));
		return obj;
	}
	public void addBlood(double x, double y)
	{
		Random rand = new Random();
		for(int i = 0; i < 50; i++)
		{
			int xpos = rand.nextInt(32) + (int)x;
			int ypos = rand.nextInt(32) + (int)y;
			double xm = (x+16) - xpos;
			double ym = (y+16) - ypos;
			float radians = (float) -Math.atan2(xm, -ym);
			entities.add(new EntityBlood(xpos, ypos, (Math.sin(radians)/(30+rand.nextInt(10)))*2, (Math.cos(radians)/(30+rand.nextInt(10))) * 2, (long)(1000 + rand.nextInt(500))));
		}
	}
	@Override
	public int addEntity(Entity ent) {
		if(master.server())
		{
			//Broadcast that we have a entity
			if(ent instanceof EntityPlayer)
			{
				synchronized(master.conn.sync)
				{
					EntityPlayer blood = (EntityPlayer)ent;
					master.conn.out.add(new Packet(String.valueOf(blood.x) + " " + String.valueOf(blood.y) + " " + String.valueOf(blood.degrot) + " " + blood.name + " " + String.valueOf(blood.xspeed) + " " + String.valueOf(blood.yspeed),  Packettype.PLAYER));
				}
			}
			else if(ent instanceof EntityZombie)
			{
				synchronized(master.conn.sync)
				{
					EntityZombie blood = (EntityZombie)ent;
					master.conn.out.add(new Packet(blood.ident + " " + String.valueOf(blood.x) + " " + String.valueOf(blood.y) + " " + String.valueOf(blood.health), Packettype.ZOMBIE));
				}
			}
			else
			{
				//Dont do a shit. Literally, thats gross. You can do a human. But doing a shit... Thats... Nasty... What kind of sick person does that?
			}
			entities.add(ent);
			return entities.size() - 1;
		}
		else
		{
			//Dont do a shit. Literally, thats gross. You can do a human. But doing a shit... Thats... Nasty... What kind of sick person does that?
			//entities.add(ent);
			//return entities.size() - 1;
		}
		return -1;
	}
	public boolean server() {
		// TODO Auto-generated method stub
		return master.server();
	}
	public void handleHeal(int gridx, int gridy, int lives) {
		// TODO Auto-generated method stub
		if(master.server())
		{
			synchronized(master.conn.sync)
			{
				master.conn.out.add(new Packet(gridx + " " + gridy + " " + lives, Packettype.HEAL));
			}
		}
		else
		{
			synchronized(master.client.synch)
			{
				master.client.out.add(new Packet(gridx + " " + gridy + " " + lives, Packettype.HEAL));
			}
		}
	}
	public void handleDamage(int gridx, int gridy, int lives) {
		// TODO Auto-generated method stub
		if(master.server())
		{
			synchronized(master.conn.sync)
			{
				master.conn.out.add(new Packet(gridx + " " + gridy + " " + lives, Packettype.DAMAGE));
			}
		}
	}
	
	
}
