package net.petterroea.awz2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import net.petterroea.awz2.Packet.Packettype;

public class MpGameScreen extends Screen {
	MpMap map;
	static int fovx = 0;
	static int fovy = 0;
	ServerSideConnection conn = null;
	ClientSideConnection client = null;
	public MpGameScreen(ServerSideConnection conn, String name, Game thegame, Map inmap) {
		super(thegame);
		this.map = new MpMap(inmap, this);
		this.conn = conn;
		conn.start();
		if(server())
		{
			int index = this.map.connectPlayer(name);
			this.map.playerindex = index;
			System.out.println("Added at index " + index);
			this.map.meName = name;
		}
		// TODO Auto-generated constructor stub
	}
	public MpGameScreen(Game thegame, ClientSideConnection client, String name) {
		super(thegame);
		this.client = client;
		client.startListening();
		client.out.add(new Packet(name, Packettype.HANDSHAKE));
		boolean gotMap = false;
		int meIndex = -1;	
		Map theMap = null;		
		try {
			MediaManager.resetMaps();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!gotMap)
		{
			synchronized(client.synch)
			{
				for(int i = 0; i < client.in.size(); i++)
				{
					if(client.in.get(i).type == Packettype.HANDSHAKEBACK)
					{
						String mapname = client.in.get(i).getEntirePacket()[2].substring(0, client.in.get(i).getEntirePacket()[2].length()-1);
						System.out.println("Searching for map " + mapname + ".");
						gotMap = true;
						for(int a = 0; a < MediaManager.maps.size(); a++)
						{
							if(MediaManager.maps.get(a).name.equals(mapname))
							{
								theMap = MediaManager.maps.get(a);
								meIndex = Integer.parseInt(client.in.get(i).getEntirePacket()[1]);
							}
						}
						client.in.remove(i);
						System.out.println("Got the map");
						break;
						
					}
				}
			}
		}
		this.map = new MpMap(theMap, this);
		this.map.meName = name;
		System.out.println("Done");
		if(server())
		{
			int index = this.map.connectPlayer(name);
			this.map.playerindex = index;
			System.out.println("Added index " + index);
		}
		// TODO Auto-generated constructor stub
	}
	public boolean server()
	{
		return !(conn == null && !(client == null));
	}
	public void tick(int delta, Graphics g)
	{
		map.checkPlayerIndex();
		map.checkPackets();
		if(!server())
		{
			if(!client.connected)
			{
				game.screen = new MainMenuScreen(game);
			}
		}
		else
		{
			
		}
		map.updateEntities(delta);
		map.draw(g);
		//Damage blood
		if(map.playerhealth < 80)
		{
			g.drawImage(MediaManager.blood.getBufferedImage(3 - (map.playerhealth/20), 0), fovx, fovy, null);
		}
		//Fov
		g.setColor(Color.black);
		g.fillRect(fovx, 0, MediaManager.fov.getBufferedImage().getWidth(), fovy);
		g.fillRect(fovx, fovy + MediaManager.fov.getBufferedImage().getHeight(), MediaManager.fov.getBufferedImage().getWidth(), game.HEIGHT);
		g.fillRect(0, 0, fovx, game.HEIGHT);
		g.fillRect(fovx + MediaManager.fov.getBufferedImage().getWidth(), 0, game.WIDTH - (fovx + MediaManager.fov.getBufferedImage().getWidth()), game.HEIGHT);
		g.drawImage(MediaManager.fov.getBufferedImage(), fovx, fovy, null);
		map.drawGui(g);
		if(map.playerhealth == 0)
		{
			game.screen = new ScreenGameOver(game, System.currentTimeMillis() - map.gamestart);
		}
	}
	@Override
	public void keyDown(int code) {
		if(code == KeyEvent.VK_ESCAPE)
		{
			if(server())
			{
				try {
					conn.stop();conn.close();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			try {
				MediaManager.resetMaps();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			game.screen = new MainMenuScreen(game);
		}
	}

}
