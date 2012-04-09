package net.petterroea.awz2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class CreditsScreen extends Screen {
	long sinceStart = System.currentTimeMillis();
	public CreditsScreen(Game thegame) {
		super(thegame);
		// TODO Auto-generated constructor stub
	}
	public void tick(int delta, Graphics g)
	{
		FontShader shader = new FontShader(){
			public int doPixel(int x, int y, int src, int screenx, int screeny)
			{
				int r = 255;
				int g = 255;
				int b = 255;
				Color col = new Color(r, g, b);
				//Override this
				return col.getRGB();
			}
		};
		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, game.HEIGHT);
		
		//Tegn dritt her
		String[] lines = {"Alone with zombies 2", "", "Code written by:", "Liam S. Crouch", "(aka petterroea)", "", "Made for The gathering 2012s", "gamedev compo", " ", "Greetings to:", "All the other gamedev participants", " ", "Thank you for playing!"};
		int pixelsoff = ((int)(System.currentTimeMillis() - sinceStart)/40) + 900;
		for(int i = 0; i < lines.length; i++)
		{
			MediaManager.font.draw(g, (Game.WIDTH/2)-((MediaManager.font.getCharWidth()*lines[i].length())/2), ((((i*(MediaManager.font.getCharHeight()+5))+5)-pixelsoff)%-900) + 600, lines[i], shader);
		}
		//System.out.println(game.WIDTH + " " + game.HEIGHT);
		g.setColor(Color.black);
		g.fillRect(0, 0, game.WIDTH, 100);
		g.drawImage(MediaManager.topShade.getBufferedImage(), 0, 100, null);
		g.drawImage(MediaManager.bottomShade.getBufferedImage(), 0, game.HEIGHT - MediaManager.bottomShade.getBufferedImage().getHeight(), null);
		MediaManager.font.draw(g, (Game.WIDTH/2)-(((MediaManager.font.getCharWidth()+1)*22)/2), 50, "Press any key to close", shader);
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		try {
			MediaManager.resetMaps();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		game.screen = new MainMenuScreen(game);
	}

}
