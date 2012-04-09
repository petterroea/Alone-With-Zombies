package net.petterroea.awz2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class JoinGameScreen extends Screen {
	public JoinGameScreen(Game thegame) {
		super(thegame);
		// TODO Auto-generated constructor stub
	}
	String port = "1994";
	String name = "gjest";
	String ip = "10.0.0.1";
	int editfield = 0;
	@Override
	public void tick(int delta, Graphics g)
	{
		FontShader highlight = new FontShader(){
			@Override
			public int doPixel(int x, int y, int src, int screenx, int screeny)
			{
				int r = 180;
				int g = 0;
				int b = 0;
				Color col = new Color(r, g, b);
				//Override this
				return col.getRGB();
			}
		};
		Rectangle mouse = new Rectangle(Input.mousex, Input.mousey, 1, 1);
		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		g.drawImage(MediaManager.title.getBufferedImage(), (game.WIDTH / 2) - (MediaManager.title.getBufferedImage().getWidth() / 2), 0, null);
		MediaManager.font.draw(g, (game.WIDTH / 2) - ((MediaManager.font.getCharWidth()*9) / 2), 5, "Join game", null);
		//Dont fuck up the game
		String fucko = "Dont use the same name as another";
		MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*fucko.length())/2), 100, fucko, null);
		//...It fucks up the game
		fucko = "It fucks up the game";
		MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*fucko.length())/2), 150, fucko, null);
		//Name
		String namestr = "Name: " + name;
		if(editfield==1)
		{
			namestr = namestr + "_";
		}
		MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*namestr.length())/2), 250, namestr, null);
		//Name
		String ipstr = "IP: " + ip;
		if(editfield==2)
		{
			ipstr = ipstr + "_";
		}
		MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*ipstr.length())/2), 300, ipstr, null);
		//Name
		String portstr = "Port: " + port;
		if(editfield==0)
		{
			portstr = portstr + "_";
		}
		MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*portstr.length())/2), 350, portstr, null);
		MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*27)/2), 400, "Press enter to change value", null);
		//Back
		Rectangle col = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*4)/2), 550, (MediaManager.font.getCharWidth()*4), MediaManager.font.getCharHeight());
		if(mouse.intersects(col))
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*4)/2), 550, "Back", highlight);
		}
		else
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*4)/2), 550, "Back", null);
		} 
		//Select map
		col = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*7)/2), 500, (MediaManager.font.getCharWidth()*7), MediaManager.font.getCharHeight());
		if(mouse.intersects(col))
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*7)/2), 500, "Connect", highlight);
		}
		else
		{
			MediaManager.font.draw(g, (game.WIDTH/2) - ((MediaManager.font.getCharWidth()*7)/2), 500, "Connect", null);
		} 

	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Rectangle mouse = new Rectangle(Input.mousex, Input.mousey, 1, 1);
		Rectangle stringCol = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*4)/2), 550, (MediaManager.font.getCharWidth()*4), MediaManager.font.getCharHeight());
		if(mouse.intersects(stringCol))
		{
			game.screen = new MPlayLobbyScreen(game);
		}
		stringCol = new Rectangle((game.WIDTH/2) - ((MediaManager.font.getCharWidth()*7)/2), 500, (MediaManager.font.getCharWidth()*7), MediaManager.font.getCharHeight());
		if(mouse.intersects(stringCol))
		{
			ClientSideConnection con = null;
			try {
				con = new ClientSideConnection(ip, Integer.parseInt(port));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			game.screen = new MpGameScreen(game, con, name);
		}
	}
	@Override
	public void keyDown(int code) {
		if(code == KeyEvent.VK_ENTER)
		{
			if(editfield==0)
			{
				editfield = 1;
			}
			else if(editfield==1)
			{
				editfield = 2;
			}
			else
			{
				editfield=0;
			}
		}
		else
		{
			if(editfield==0 && port.length() < 5) //We are editing a port
			{
				if(code == KeyEvent.VK_0)
				{
					port = port + "0";
				}
				else if(code == KeyEvent.VK_1)
				{
					port = port + "1";
				}
				else if(code == KeyEvent.VK_2)
				{
					port = port + "2";
				}
				else if(code == KeyEvent.VK_3)
				{
					port = port + "3";
				}
				else if(code == KeyEvent.VK_4)
				{
					port = port + "4";
				}
				else if(code == KeyEvent.VK_5)
				{
					port = port + "5";
				}
				else if(code == KeyEvent.VK_6)
				{
					port = port + "6";
				}
				else if(code == KeyEvent.VK_7)
				{
					port = port + "7";
				}
				else if(code == KeyEvent.VK_8)
				{
					port = port + "8";
				}
				else if(code == KeyEvent.VK_9)
				{
					port = port + "9";
				}
				else if(port.length() > 0)
				{
					if(code == KeyEvent.VK_BACK_SPACE)
					{
						port = port.substring(0, port.length() - 1);
					}
				}
			}
			else if(editfield==0 && port.length() > 0)
			{
				if(code == KeyEvent.VK_BACK_SPACE)
				{
					port = port.substring(0, port.length() - 1);
				}
			}
			else if(editfield==1)
			{
				if(name.length() > 0)
				{
					if(code == KeyEvent.VK_BACK_SPACE)
					{
						name = name.substring(0, name.length() - 1);
					}
				}
				if(name.length() < 15)
				{
					switch(code)
					{
					case KeyEvent.VK_A:
						name = name + "a";
					break;
					case KeyEvent.VK_B:
						name = name + "b";
					break;
					case KeyEvent.VK_C:
						name = name + "c";
					break;
					case KeyEvent.VK_D:
						name = name + "d";
					break;
					case KeyEvent.VK_E:
						name = name + "e";
					break;
					case KeyEvent.VK_F:
						name = name + "f";
					break;
					case KeyEvent.VK_G:
						name = name + "g";
					break;
					case KeyEvent.VK_H:
						name = name + "h";
					break;
					case KeyEvent.VK_I:
						name = name + "i";
					break;
					case KeyEvent.VK_J:
						name = name + "j";
					break;
					case KeyEvent.VK_K:
						name = name + "k";
					break;
					case KeyEvent.VK_L:
						name = name + "l";
					break;
					case KeyEvent.VK_M:
						name = name + "m";
					break;
					case KeyEvent.VK_N:
						name = name + "n";
					break;
					case KeyEvent.VK_O:
						name = name + "o";
					break;
					case KeyEvent.VK_P:
						name = name + "p";
					break;
					case KeyEvent.VK_Q:
						name = name + "q";
					break;
					case KeyEvent.VK_R:
						name = name + "r";
					break;
					case KeyEvent.VK_S:
						name = name + "s";
					break;
					case KeyEvent.VK_T:
						name = name + "t";
					break;
					case KeyEvent.VK_U:
						name = name + "u";
					break;
					case KeyEvent.VK_V:
						name = name + "v";
					break;
					case KeyEvent.VK_W:
						name = name + "w";
					break;
					case KeyEvent.VK_X:
						name = name + "x";
					break;
					case KeyEvent.VK_Y:
						name = name + "y";
					break;
					case KeyEvent.VK_Z:
						name = name + "z";
					break;
					case KeyEvent.VK_SPACE:
						name = name + " ";
					break;
						default:
							//Invalid key
					}
				}
			}
			else if(editfield==2)
			{
				if(ip.length() > 0)
				{
					if(code == KeyEvent.VK_BACK_SPACE)
					{
						ip = ip.substring(0, ip.length() - 1);
					}
				}
				if(ip.length() < 15)
				{
					switch(code)
					{
					case KeyEvent.VK_0:
						ip = ip + "0";
					break;
					case KeyEvent.VK_1:
						ip = ip + "1";
					break;
					case KeyEvent.VK_2:
						ip = ip + "2";
					break;
					case KeyEvent.VK_3:
						ip = ip + "3";
					break;
					case KeyEvent.VK_4:
						ip = ip + "4";
					break;
					case KeyEvent.VK_5:
						ip = ip + "5";
					break;
					case KeyEvent.VK_6:
						ip = ip + "6";
					break;
					case KeyEvent.VK_7:
						ip = ip + "7";
					break;
					case KeyEvent.VK_8:
						ip = ip + "8";
					break;
					case KeyEvent.VK_9:
						ip = ip + "9";
					break;
					case KeyEvent.VK_PERIOD:
						ip = ip + ".";
					break;
					}
				}
			}
		}
		
	}
}
